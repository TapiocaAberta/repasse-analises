package io.sjcdigital.repasse.controller.repasse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
	
	private static final List<Integer> ANOS_PRESENTES_REPASSE = Arrays.asList(2011, 2013, 2015, 2017, 2019);
	
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
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();

			beanToCsv.write(repasses);

		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public List<RepasseIDEB> criaCriaRepasseIDEB( ) {
		
		LOGGER.info("Criando novo repasse com dados IDEB");
		
		List<RepasseIDEB> repassesIdeb = new ArrayList<>();
		
		idebCSVController.pegaIdebTodosPeriodosEscolasPublicas().forEach(ideb -> {
			
			Optional<Municipio> municipio = repasseController.buscaMunicipio(ideb.getMunicipio().getEstado().getUf(), ideb.getMunicipio().getNome());
			
			if(municipio.isPresent()) {
				
				int idMun = municipio.get().getId();
				
				List<DadosMunicipio> dadosMunicipio = repasseController.buscaDadosMunicipio(ideb.getMunicipio().getEstado().getUf(), ideb.getMunicipio().getNome());
				
				ideb.getNotas().forEach(nota -> {
					
					if(ANOS_PRESENTES_REPASSE.contains(nota.getAno())) {
						
						RepasseIDEB repasseIdeb = new RepasseIDEB();
						repasseIdeb.setUf(ideb.getMunicipio().getEstado().getUf());
						repasseIdeb.setMunicipio(ideb.getMunicipio().getNome());
						repasseIdeb.setRede(ideb.getRede().getRedeTexto());
						repasseIdeb.setPeriodo(ideb.getPerido().name());
						repasseIdeb.setAno(nota.getAno());
						repasseIdeb.setIdebNota(nota.getNotaIdeb());
						
						Optional<DadosMunicipio> dadoMunicipio = dadosMunicipio.stream().filter(dm -> dm.getAno() == nota.getAno()).findFirst();
						Optional<Double> valor = repasseController.buscaValoresAgregadosEducacao(nota.getAno(), idMun);
						
						if(valor.isPresent()) {
							repasseIdeb.setValorRepasseEducacao(valor.get());
						}
						
						if(dadoMunicipio.isPresent()) {
							
							repasseIdeb.setIdhEducacao(dadoMunicipio.get().getIdhEducacao());
							repasseIdeb.setIdhLongevidade(dadoMunicipio.get().getIdhLongevidade());
							repasseIdeb.setIdhm(dadoMunicipio.get().getIdhm());
							repasseIdeb.setIdhRenda(dadoMunicipio.get().getIdhm());
							repasseIdeb.setPopulacao(dadoMunicipio.get().getPopulacao());
							
						}
						
						repassesIdeb.add(repasseIdeb);
					}
				});
			
		} else {
			System.out.println("Cidade não Encontrada: " + ideb.getMunicipio().getNome() + "-" + ideb.getMunicipio().getEstado().getUf());
			LOGGER.error("Cidade não Encontrada: " + ideb.getMunicipio().getNome() + "-" + ideb.getMunicipio().getEstado().getUf());
		}});
		
		return repassesIdeb;
		
	}
	
	
	
	

}
