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

import io.sjcdigital.repasse.model.base.Estado;
import io.sjcdigital.repasse.model.base.Municipio;
import io.sjcdigital.repasse.model.ideb.Ideb;
import io.sjcdigital.repasse.model.ideb.IdebAnos;
import io.sjcdigital.repasse.model.ideb.Nota;
import io.sjcdigital.repasse.model.ideb.Periodo;
import io.sjcdigital.repasse.model.ideb.Rede;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class IdebCSVController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdebCSVController.class);
	private static final String IDEB_CSV_PATH = "/home/pesilva/workspace/code/pessoal/repasse-analises/data/ideb/";
	
	
	public List<Ideb> pegaIdebTodosPeriodos() {
		List<Ideb> todos = new ArrayList<>();
		
		/*
		 * Quero mudar pra map, mas não está rolando! :-(
		 * List<List<Ideb>> collect = Arrays.asList(Periodo.values()).stream()
		 * .map(periodo -> converteCSV(periodo, recuperaArquivoPorPeriodo(periodo)))
		 * .collect(Collectors.toList());
		 */
		
		Arrays.asList(Periodo.values()).forEach(p -> todos.addAll(this.converteCSV(p, recuperaArquivoPorPeriodo(p))));
		return todos;
	}
	
	public List<Ideb> pegaIdebTodosPeriodosEscolasPublicas() {
		return pegaIdebTodosPeriodos().stream().filter(Ideb::ehRedePublica).collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		List<Ideb> pegaIdebTodsPeriodos = new IdebCSVController().pegaIdebTodosPeriodosEscolasPublicas();
		System.out.println(pegaIdebTodsPeriodos.size());
	}
	
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
	public List<Ideb> converteCSV(final Periodo periodo, final String arquivo) {
		
		List<Ideb> idebs = new ArrayList<>();
		
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(arquivo)).withSkipLines(1).build()) {
			
			reader.readAll()//.subList(0, 6)
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
	public Ideb criaIdeb(final String[] colunas, final Periodo periodo) {
		Municipio municipio = new Municipio(colunas[1], new Estado(colunas[0]));
		Rede rede = Rede.converteRedeStringParaRede(colunas[2]);
		return new Ideb(periodo, municipio, rede, criaNotasIdeb(colunas, periodo));
	}

	/**
	 * 
	 * @param colunas
	 * @param periodo
	 * @return
	 */
	private List<Nota> criaNotasIdeb(final String[] colunas, final Periodo periodo) {
		List<Nota> notas = new ArrayList<Nota>();
		Integer[] anosDisp = IdebAnos.pegaListaDeAnosPorPeriodo(periodo);
		
		//Atenção, no arquivo as notas devem começar a partir da coluna 3, para montar o sublist
		List<String> notasCSV = Arrays.asList(colunas).subList(3, (colunas.length));
		
		int index = 0;
		for (String nota : notasCSV) {
			notas.add(new Nota(anosDisp[index], "-".equals(nota) ? "NA" : nota));
			index++;
		}
		
		return notas;
	}

}
