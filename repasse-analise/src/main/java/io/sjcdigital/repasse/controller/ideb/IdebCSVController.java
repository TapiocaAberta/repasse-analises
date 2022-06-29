package io.sjcdigital.repasse.controller.ideb;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import io.sjcdigital.repasse.model.entity.ideb.Periodo;
import io.sjcdigital.repasse.model.entity.ideb.Rede;
import io.sjcdigital.repasse.model.pojo.base.EstadoPojo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;
import io.sjcdigital.repasse.model.pojo.ideb.IdebAnosPojo;
import io.sjcdigital.repasse.model.pojo.ideb.IdebPojo;
import io.sjcdigital.repasse.model.pojo.ideb.NotaPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class IdebCSVController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdebCSVController.class);
	private static final String IDEB_CSV_PATH = "/home/pesilva/workspace/code/pessoal/repasse-analises/data/ideb/";
	
	/**
	 * 
	 * @return
	 */
	public List<IdebPojo> pegaIdebTodosPeriodos() {
		List<IdebPojo> todos = new ArrayList<>();
		List<Periodo> periodos = Arrays.asList(Periodo.values());
		periodos.forEach(p -> todos.addAll(this.converteCSV(p, recuperaArquivoPorPeriodo(p))));
		return todos;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<IdebPojo> pegaIdebTodosPeriodosEscolasPublicas() {
		return pegaIdebTodosPeriodos().stream().filter(IdebPojo::ehRedePublica).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param periodo
	 * @return
	 */
	private String recuperaArquivoPorPeriodo(final Periodo periodo) {
		
		switch (periodo) {
		case ENSINO_MEDIO:
			return IDEB_CSV_PATH + "ideb_ensino_medio_2017_2019__2.csv";
		case FUNDAMENTAL_ANOS_INICIAIS:
			return IDEB_CSV_PATH + "ideb_anos_inciais_2005_2019__2.csv";
		case FUNDAMENTAL_ANOS_FINAIS:
			return IDEB_CSV_PATH + "ideb_anos_finais_2005_2019__2.csv";
		default:
			LOGGER.error("Periodo " + periodo + " não aceito!");
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param periodo
	 * @param arquivo
	 * @return
	 */
	public List<IdebPojo> converteCSV(final Periodo periodo, final String arquivo) {
		
		List<IdebPojo> idebs = new ArrayList<>();
		
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(arquivo)).withSkipLines(1).build()) {
			
			reader.readAll()//.subList(0, 12)
				  .forEach(linha -> { idebs.add(criaIdeb(linha, periodo)); });
			
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} catch (CsvValidationException e) {
			LOGGER.error(e.getMessage());
		} catch (CsvException e) {
			LOGGER.error(e.getMessage());
		}
		
		return idebs;
	}
	
	/**
	 * 
	 * @param colunas
	 * @param periodo
	 * @return
	 */
	public IdebPojo criaIdeb(final String[] colunas, final Periodo periodo) {
		MunicipioPojo municipio = new MunicipioPojo(colunas[1], new EstadoPojo(colunas[0]));
		Rede rede = Rede.converteRedeStringParaRede(colunas[2]);
		return new IdebPojo(periodo, municipio, rede, criaNotasIdeb(colunas, periodo));
	}

	/**
	 * 
	 * @param colunas
	 * @param periodo
	 * @return
	 */
	private List<NotaPojo> criaNotasIdeb(final String[] colunas, final Periodo periodo) {
		List<NotaPojo> notas = new ArrayList<NotaPojo>();
		Integer[] anosDisp = IdebAnosPojo.pegaListaDeAnosPorPeriodo(periodo);
		
		//Atenção, no arquivo as notas devem começar a partir da coluna 3, para montar o sublist
		List<String> notasCSV = Arrays.asList(colunas).subList(3, (colunas.length));
		
		int index = 0;
		for (String nota : notasCSV) {
			notas.add(new NotaPojo(anosDisp[index], "-".equals(nota) ? "NA" : nota));
			index++;
		}
		
		return notas;
	}

}
