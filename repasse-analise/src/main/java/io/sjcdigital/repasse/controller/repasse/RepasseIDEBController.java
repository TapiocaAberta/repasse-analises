package io.sjcdigital.repasse.controller.repasse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.sjcdigital.repasse.controller.ideb.IdebCSVController;
import io.sjcdigital.repasse.model.base.DadosMunicipio;
import io.sjcdigital.repasse.model.base.Municipio;
import io.sjcdigital.repasse.model.repasse.RepasseIDEB;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseIDEBController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepasseIDEBController.class);
	
	private static final List<Integer> ANOS_REPASSE_COM_NOTAS_IDEB = Arrays.asList(2011, 2013, 2015, 2017, 2019);
	
	RepasseController repasseController;
	IdebCSVController idebCSVController;
	
	public RepasseIDEBController() {
		this.repasseController = new RepasseController();
		this.idebCSVController = new IdebCSVController();
	}
	
	public static void main(String[] args) {

		RepasseIDEBController controller = new RepasseIDEBController();
		List<RepasseIDEB> todos = controller.criaCriaRepasseIDEB();
		controller.criaCSV(todos, "/home/pesilva/workspace/code/pessoal/repasse-analises/data/ideb_repasse/");

	}
	
	public void criaCSV(List<RepasseIDEB> repasses, String filePath) {
		
		LOGGER.info("Salvando CSV em : "  + filePath);

		try (var writer = Files.newBufferedWriter(Paths.get(filePath + "ideb_repasse.csv"), StandardCharsets.UTF_8)) {

			StatefulBeanToCsv<RepasseIDEB> beanToCsv = new StatefulBeanToCsvBuilder<RepasseIDEB>(writer)
																										.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
																										.build();

			beanToCsv.write(repasses);

		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<RepasseIDEB> criaCriaRepasseIDEB() {
		
		LOGGER.info("Criando novo repasse com dados IDEB");
		
		List<RepasseIDEB> repassesIdeb = new ArrayList<>();
		
		idebCSVController.pegaIdebTodosPeriodosEscolasPublicas().forEach(ideb -> {
			
			System.out.println("Buscando: " + ideb.getMunicipio().getNome() + "-" + ideb.getMunicipio().getEstado().getUf() + " - PERIODO: " + ideb.getPerido());
			
			String uf = ideb.getMunicipio().getEstado().getUf();
			String munNome = ideb.getMunicipio().getNome();
			
			Optional<Municipio> municipio = repasseController.buscaMunicipio(uf, munNome);
			
			if(municipio.isPresent()) {
				
				int idMun = municipio.get().getId();
				
				List<DadosMunicipio> dadosMunicipio = repasseController.buscaDadosMunicipio(uf, munNome);
				
				ideb.getNotas().forEach(nota -> {
					
					if(ANOS_REPASSE_COM_NOTAS_IDEB.contains(nota.getAno())) {
						
						RepasseIDEB repasseIdeb = new RepasseIDEB();
						repasseIdeb.setUf(uf);
						repasseIdeb.setMunicipio(munNome);
						repasseIdeb.setRede(ideb.getRede().getRedeTexto());
						repasseIdeb.setPeriodo(ideb.getPerido().name());
						repasseIdeb.setAnoIdeb(nota.getAno());
						repasseIdeb.setIdebNota(nota.getNotaIdeb());
						
						/*
						 * Estamos sempre olhando um ano a frente do IDEB para o valor repassado. 
						 * Por exemplo: Se a nota é de 2011, o calculo deverá ser feito e repassado para 2012
						 */
						Integer anoRepasse = nota.getAno() + 1;
						Optional<Double> valor = repasseController.buscaValoresAgregadosEducacao(anoRepasse, idMun);
						
						if(valor.isPresent()) {
							repasseIdeb.setValorRepasseEducacao(valor.get());
							repasseIdeb.setAnoRepasse(anoRepasse);
						} else {
							LOGGER.info("Valor de repasse não encontrado para: " + municipio.get().getNome());
						}
						
						//Adiciona Dados do Ultimo IDH relativo ao ano da aplicação prova Ideb
						Optional<DadosMunicipio> dadoMunicipioIdh = dadosMunicipio.stream()
																				  .filter(anoUltimoIDH(nota.getAno()))
																				  .findFirst();
						
						if(dadoMunicipioIdh.isPresent()) {
							
							repasseIdeb.setPopulacao(dadoMunicipioIdh.get().getPopulacao());
							repasseIdeb.setAnoIdh(dadoMunicipioIdh.get().getAno());
							repasseIdeb.setIdhEducacao(dadoMunicipioIdh.get().getIdhEducacao());
							repasseIdeb.setIdhLongevidade(dadoMunicipioIdh.get().getIdhLongevidade());
							repasseIdeb.setIdhm(dadoMunicipioIdh.get().getIdhm());
							repasseIdeb.setIdhRenda(dadoMunicipioIdh.get().getIdhm());
							
						} else { 
							System.err.println("Dados não encontrado para: " + municipio.get().getNome() + " no ano de: " + anoUltimoIDH(anoRepasse));
							LOGGER.info("Dados não encontrado para: " + municipio.get().getNome() + " no ano de: " + anoUltimoIDH(anoRepasse));
						}
						
						repassesIdeb.add(repasseIdeb);
					}
				});
			
		} else {
			LOGGER.error("Cidade não Encontrada: " + munNome + "-" + uf);
		}});
		
		return repassesIdeb;
		
	}

	
	/**
	 * @param anoNota
	 * @return
	 */
	//TODO: Essa lógica deve ser mudada pra atender anos posteriores à 2020
	private Predicate<? super DadosMunicipio> anoUltimoIDH(Integer anoNota) {
		Integer ano = (anoNota >= 2010 && anoNota <= 2020) ? 2010 : 2020;
		return dm -> dm.getAno() == ano;
	}
	

}
