package io.sjcdigital.repasse.model.pojo.ibge;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class PopulacaoIBGEPojo {

    private String codigo;
    private Integer populacao;
    
    public PopulacaoIBGEPojo(String codigo, Integer populacao) {
        super();
        this.codigo = codigo;
        this.populacao = populacao;
    }

    public PopulacaoIBGEPojo() { }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the populacao
     */
    public Integer getPopulacao() {
        return populacao;
    }

    /**
     * @param populacao the populacao to set
     */
    public void setPopulacao(Integer populacao) {
        this.populacao = populacao;
    }

}
