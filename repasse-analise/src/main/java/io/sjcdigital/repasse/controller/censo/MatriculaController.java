package io.sjcdigital.repasse.controller.censo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import io.sjcdigital.repasse.controller.ibge.PopulacaoIBGEController;
import io.sjcdigital.repasse.model.entity.ideb.Periodo;
import io.sjcdigital.repasse.model.pojo.base.EstadoPojo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;
import io.sjcdigital.repasse.model.pojo.censo.MatriculaPojo;
import io.sjcdigital.repasse.model.pojo.ibge.PopulacaoIBGEPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class MatriculaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatriculaController.class);
    private static final String MATRICULAS_CSV_PATH = "/home/pesilva/workspace/code/pessoal/repasse-analises/data/censo_matriculas/";

    /**
     * 
     * @param periodo
     * @param municipio
     * @return
     */
    public static Predicate<? super MatriculaPojo> filtraPorPeriodoUFECidade(Periodo periodo, MunicipioPojo municipio) {
        return m -> m.getPeriodo().equals(periodo) && 
                    m.getMunicipio().getNome().equals(municipio.getNome()) && 
                    m.getMunicipio().getEstado().getUf().equals(municipio.getEstado().getUf());
    }

    /**
     * 
     * @return
     */
    public Map<String, Collection<MatriculaPojo>> recuperaMatriculas() {

        List<MatriculaPojo> matriculas = new ArrayList<>();
        Map<String, PopulacaoIBGEPojo> populacaoPorMunicipio = new PopulacaoIBGEController().populacaoPorMunicipio();
        
        todosCsvDoDiretorio().forEach(arquivo -> {
            matriculas.addAll(criaMatriculas(arquivo, populacaoPorMunicipio));
        });

        return matriculas.stream().collect(
                Collectors.groupingBy(MatriculaPojo::getAno, HashMap::new, Collectors.toCollection(ArrayList::new)));
    }

    /**
     * @param arquivo
     * @param populacaoPorMunicipio 
     * @return
     */
    private Collection<? extends MatriculaPojo> criaMatriculas(final Path arquivo, final Map<String, PopulacaoIBGEPojo> populacaoPorMunicipio) {

        List<MatriculaPojo> matriculas = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(arquivo.toString())).withSkipLines(1).build()) {

            reader.readAll()
                    .forEach(linha -> {
                        matriculas.add(novaMatricula(linha, populacaoPorMunicipio));
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

        return matriculas;
    }

    /**
     * @param populacaoPorMunicipio 
     * @param linha
     * @return
     */
    private MatriculaPojo novaMatricula(String[] linhas, Map<String, PopulacaoIBGEPojo> populacaoPorMunicipio) {
        String ano = linhas[0];
        EstadoPojo estado = new EstadoPojo(linhas[2], linhas[3]);
        String codMunicipio = linhas[7];
        
        PopulacaoIBGEPojo populacaoIbge = populacaoPorMunicipio.get(codMunicipio);
        MunicipioPojo municipio;
        
        if(Objects.nonNull(populacaoIbge)) {
            municipio = new MunicipioPojo(linhas[4], estado, codMunicipio, populacaoIbge.getPopulacao());
        } else {
            municipio = new MunicipioPojo(linhas[4], estado, codMunicipio, 0);
        }
        
        return new MatriculaPojo(ano, municipio, Periodo.valueOf(linhas[5]), Integer.valueOf(linhas[6]));
    }

    /**
     * 
     * @return
     */
    private List<Path> todosCsvDoDiretorio() {

        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(MATRICULAS_CSV_PATH))) {
            pathList = stream.map(Path::normalize).filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".csv")).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("ERRO ao ler arquivos de " + MATRICULAS_CSV_PATH);
        }

        return pathList;
    }

}
