package io.sjcdigital.repasse.model.entity.base;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public enum Regiao {
    
    NORTE, NORDESTE, CENTRO_OESTE, SUDESTE, SUL, NAO_ENCONTRADO;
    
    public static Regiao porSigla(String sigla) {
        
        Regiao[] regioes = Regiao.values();
        
        for (int i = 0; i < regioes.length; i++) {
            Regiao regiao = regioes[i];
            
            if(regiao.estados().contains(sigla)) {
                return regiao;
            }
        }
        
        return NAO_ENCONTRADO;
    }
    
    public List<String> estados() {
        switch (this) {
            case NORTE:
                return Arrays.asList("AC", "AP", "AM", "RO", "RR", "TO");
            case NORDESTE:
                return Arrays.asList("AL", "BA", "CE", "MA", "PB", "PI", "PE", "RN", "SE");
            case CENTRO_OESTE:
                return Arrays.asList("DF", "GO", "MT", "MS");
            case SUDESTE:
                return Arrays.asList("ES", "MG", "RJ", "SP");
            case SUL:
                return Arrays.asList("PR", "RS", "SC");
            default:
                return Arrays.asList();
        }
    }

}
