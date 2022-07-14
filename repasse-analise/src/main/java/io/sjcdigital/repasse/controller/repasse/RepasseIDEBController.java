package io.sjcdigital.repasse.controller.repasse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.sjcdigital.repasse.controller.censo.MatriculaController;
import io.sjcdigital.repasse.controller.ideb.IdebCSVController;
import io.sjcdigital.repasse.model.entity.base.FaixaPopulacao;
import io.sjcdigital.repasse.model.entity.base.Regiao;
import io.sjcdigital.repasse.model.pojo.base.DadosMunicipioPojo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;
import io.sjcdigital.repasse.model.pojo.censo.MatriculaPojo;
import io.sjcdigital.repasse.model.pojo.repasse.RepasseIdebPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseIDEBController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepasseIDEBController.class);
	
	private static final List<Integer> ANOS_REPASSE_COM_NOTAS_IDEB = Arrays.asList(2013, 2015, 2017, 2019);
	
	RepasseController repasseController;
	IdebCSVController idebCSVController;
	
	public RepasseIDEBController() {
		this.repasseController = new RepasseController();
		this.idebCSVController = new IdebCSVController();
	}
	
	public static void main(String[] args) {
	    
	    System.out.println("Starting ....");
		RepasseIDEBController controller = new RepasseIDEBController();
		List<RepasseIdebPojo> todos = controller.criaCriaRepasseIDEB();
		controller.criaCSV(todos, "/home/pesilva/workspace/code/pessoal/repasse-analises/data/ideb_repasse/");
		System.out.println("Finished ....");

	}
	
	public void criaCSV(List<RepasseIdebPojo> repasses, String filePath) {
		
		LOGGER.info("Salvando CSV em : "  + filePath);

		try (var writer = Files.newBufferedWriter(Paths.get(filePath + "ideb_repasse-fundeb.csv"), StandardCharsets.UTF_8)) {

			StatefulBeanToCsv<RepasseIdebPojo> beanToCsv = new StatefulBeanToCsvBuilder<RepasseIdebPojo>(writer)
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
	public List<RepasseIdebPojo> criaCriaRepasseIDEB() {
		
		LOGGER.info("Criando novo repasse com dados IDEB");
		
		List<RepasseIdebPojo> repassesIdeb = new ArrayList<>();
		Map<String, Collection<MatriculaPojo>> matriculas = new MatriculaController().recuperaMatriculas();
		
		idebCSVController.pegaIdebTodosPeriodosEscolasPublicas().forEach(ideb -> {
			
		    LOGGER.info("Buscando: " + ideb.getMunicipio().getNome() + "-" + ideb.getMunicipio().getEstado().getUf() + " - PERIODO: " + ideb.getPerido());
			
			String uf = ideb.getMunicipio().getEstado().getUf();
			String munNome = ideb.getMunicipio().getNome();
			
			Optional<MunicipioPojo> municipioRepasse = repasseController.buscaMunicipio(uf, munNome);
			
			if(municipioRepasse.isPresent()) {
				
				int idMun = municipioRepasse.get().getId();
				
				List<DadosMunicipioPojo> dadosMunicipio = repasseController.buscaDadosMunicipio(uf, munNome);
				
				ideb.getNotas().forEach(nota -> {
					
					if(ANOS_REPASSE_COM_NOTAS_IDEB.contains(nota.getAno())) {
						
						RepasseIdebPojo repasseIdeb = new RepasseIdebPojo();
						repasseIdeb.setUf(uf);
						repasseIdeb.setMunicipio(munNome);
						repasseIdeb.setRede(ideb.getRede().getRedeTexto());
						repasseIdeb.setPeriodo(ideb.getPerido().name());
						repasseIdeb.setAnoIdeb(nota.getAno());
						repasseIdeb.setIdebNota(nota.getNotaIdeb());
						
						/*
						 * Estamos sempre olhando um ano para trás da nota do IDEB para o valor repassado. 
						 * Por exemplo: Se a nota é de 2011, o calculo deverá ser feito e repassado para 2010
						 */
						Integer anoRepasse = nota.getAno() - 1;
						
						/*
						 * Os valores repassados para educação são com base nas matriculas dos anos anteriores. 
						 */
						Integer anoMatricula = anoRepasse - 1;
						
						//Optional<Double> valor = repasseController.buscaValoresAgregadosEducacao(anoRepasse, idMun);
						Optional<Double> valor = repasseController.buscaValoresAgregadosFundeb(anoRepasse, idMun);
						
						if(valor.isPresent()) {
							repasseIdeb.setValorRepasseEducacao(valor.get());
							repasseIdeb.setAnoRepasse(anoRepasse);
						} else {
						    System.err.println("Valor de repasse não encontrado para: " + municipioRepasse.get().getNome());
							LOGGER.info("Valor de repasse não encontrado para: " + municipioRepasse.get().getNome());
							repasseIdeb.setValorRepasseEducacao(0.0);
                            repasseIdeb.setAnoRepasse(anoRepasse);
						}
						
						Optional<MatriculaPojo> matricula = matriculas.get(anoMatricula.toString()).stream()
						        .filter(MatriculaController.filtraPorPeriodoUFECidade(ideb.getPerido(), ideb.getMunicipio()))
						        .findFirst();
						
						if(matricula.isPresent()) { 
						    
						    Integer habitantes = matricula.get().getMunicipio().getPopulacao();
						    
						    repasseIdeb.setMatricula(matricula.get().getQntMatriculados());
                            repasseIdeb.setPopulacao(habitantes);
						    repasseIdeb.setFaixa(FaixaPopulacao.porHabitantes(habitantes));
						    repasseIdeb.setAnoMatriculas(anoMatricula);
						    repasseIdeb.setRepassePorMatricula();
						    repasseIdeb.setCodigoMunicipio(matricula.get().getMunicipio().getCodMunicipio());
						    repasseIdeb.setRegiao(matricula.get().getMunicipio().getEstado().getRegiao().toString());
						    
						} else {
						    System.err.println("Matricula não encontrado para o Periodo " + ideb.getPerido() + " Cidade " + munNome + "-" + uf);
						    LOGGER.info("Matricula não encontrado para o Periodo " + ideb.getPerido() + " Cidade " + munNome + "-" + uf);
						    repasseIdeb.setPopulacao(0);
						    repasseIdeb.setMatricula(0);
						    repasseIdeb.setFaixa(FaixaPopulacao.porHabitantes(0));
						    repasseIdeb.setAnoMatriculas(anoMatricula);
						    repasseIdeb.setRepassePorMatricula();
						    repasseIdeb.setCodigoMunicipio("NA");
						    repasseIdeb.setRegiao(Regiao.NAO_ENCONTRADO.toString());
						}
						
						//Adiciona Dados do Ultimo IDH relativo ao ano da aplicação prova IdebPojo
						Optional<DadosMunicipioPojo> dadoMunicipioIdh = dadosMunicipio.stream()
																				  .filter(anoUltimoIDH(nota.getAno()))
																				  .findFirst();
						
						if(dadoMunicipioIdh.isPresent()) {
							repasseIdeb.setAnoIdh(dadoMunicipioIdh.get().getAno());
							repasseIdeb.setIdhEducacao(dadoMunicipioIdh.get().getIdhEducacao());
							repasseIdeb.setIdhLongevidade(dadoMunicipioIdh.get().getIdhLongevidade());
							repasseIdeb.setIdhm(dadoMunicipioIdh.get().getIdhm());
							repasseIdeb.setIdhRenda(dadoMunicipioIdh.get().getIdhm());
							
						} else { 
							System.err.println("Dados não encontrado para: " + municipioRepasse.get().getNome() + " no ano de: " + anoUltimoIDH(anoRepasse));
							LOGGER.info("Dados não encontrado para: " + municipioRepasse.get().getNome() + " no ano de: " + anoUltimoIDH(anoRepasse));
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
	private Predicate<? super DadosMunicipioPojo> anoUltimoIDH(Integer anoNota) {
		Integer ano = (anoNota >= 2010 && anoNota <= 2020) ? 2010 : 2020;
		return dm -> dm.getAno() == ano;
	}
	

}
