package io.sjcdigital.repasse.controller.ibge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import io.sjcdigital.repasse.model.pojo.ibge.PopulacaoIBGEPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class PopulacaoIBGEController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PopulacaoIBGEController.class);
    private static final String POP_CSV_PATH = "/home/pesilva/workspace/code/pessoal/repasse-analises/data/municipios.csv";
    
    
    /**
     * @param arquivo
     * @return 
     * @return
     */
    public Map<String, PopulacaoIBGEPojo> populacaoPorMunicipio() {

        List<PopulacaoIBGEPojo> pops = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(POP_CSV_PATH)).withSkipLines(1).build()) {

            reader.readAll()
                    .forEach(linha -> {
                        pops.add(new PopulacaoIBGEPojo(linha[0], Integer.valueOf(linha[17])));
                    });
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (CsvValidationException e) {
            LOGGER.error(e.getMessage());
        } catch (CsvException e) {
            LOGGER.error(e.getMessage());
        }

        return pops.stream().collect(Collectors.toMap(PopulacaoIBGEPojo::getCodigo, Function.identity()));
    }

}
