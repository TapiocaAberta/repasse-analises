package io.sjcdigital.repasse.model.pojo.censo;

import io.sjcdigital.repasse.model.entity.ideb.Periodo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class MatriculaPojo {
    
    private String ano;
    private MunicipioPojo municipio;
    private Periodo periodo;
    private Integer qntMatriculados;
    
    /**
     * @param ano
     * @param municipio
     * @param periodo
     * @param qntMatriculados
     */
    public MatriculaPojo(String ano, MunicipioPojo municipio, Periodo periodo, Integer qntMatriculados) {
        super();
        this.ano = ano;
        this.municipio = municipio;
        this.periodo = periodo;
        this.qntMatriculados = qntMatriculados;
    }

    /**
     * @return the ano
     */
    public String getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(String ano) {
        this.ano = ano;
    }

    /**
     * @return the municipio
     */
    public MunicipioPojo getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(MunicipioPojo municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the periodo
     */
    public Periodo getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the qntMatriculados
     */
    public Integer getQntMatriculados() {
        return qntMatriculados;
    }

    /**
     * @param qntMatriculados the qntMatriculados to set
     */
    public void setQntMatriculados(Integer qntMatriculados) {
        this.qntMatriculados = qntMatriculados;
    }
    
}