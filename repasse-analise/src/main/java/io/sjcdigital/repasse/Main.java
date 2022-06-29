package io.sjcdigital.repasse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.sjcdigital.repasse.controller.repasse.RepasseController;
import io.sjcdigital.repasse.model.pojo.repasse.RepasseMesPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class Main {

	public static void salvaArquivo(final List<RepasseMesPojo> repasses) {

		try (var writer = Files.newBufferedWriter(
				Paths.get("/home/pesilva/workspace/code/pessoal/repasse-analises/data/repasse_ano.csv"),
				StandardCharsets.UTF_8)) {

			StatefulBeanToCsv<RepasseMesPojo> beanToCsv = new StatefulBeanToCsvBuilder<RepasseMesPojo>(writer)
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();

			beanToCsv.write(repasses);

		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Integer anoFim = 2021;
		Integer mesFim;

		Integer idMunicipio = 2865;

		RepasseController controller = new RepasseController();
		List<RepasseMesPojo> repasses = new ArrayList<>();

		for (int ano = 2012; ano <= anoFim; ano++) {

			mesFim = ano == 2021 ? 9 : 12;

			for (int mes = 1; mes <= mesFim; mes++) {

				LocalDate data = LocalDate.of(ano, mes, 1);

				System.out.println("buscando dados para " + data.format(DateTimeFormatter.ISO_DATE));

				RepasseMesPojo repasse = new RepasseMesPojo();
				repasse.setUf("SP");
				repasse.setMunicipio("São José dos Campos");
				repasse.setArea("Educação");
				repasse.setAnoRepasse(data);

				Optional<Double> valor = controller.buscaValoresAgregadosEducacao(ano, mes, idMunicipio);

				if (valor.isPresent()) {
					repasse.setValor(valor.get());
				} else {
					System.out.println("Valor não presente para o ano de " + ano);
				}

				repasses.add(repasse);

			}

		}

		salvaArquivo(repasses);

	}

}
