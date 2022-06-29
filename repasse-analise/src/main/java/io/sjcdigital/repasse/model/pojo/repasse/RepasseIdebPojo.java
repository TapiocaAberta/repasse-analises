package io.sjcdigital.repasse.model.pojo.repasse;

import java.util.Objects;

import com.opencsv.bean.CsvBindByName;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseIdebPojo {

	@CsvBindByName(column = "UF")
	private String uf;

	@CsvBindByName
	private String municipio;
	
	@CsvBindByName(column = "CODIGO_MUNICIPIO")
	private String codigoMunicipio;

	@CsvBindByName
	private String rede;

	@CsvBindByName
	private String periodo;
	
	@CsvBindByName(column = "ANO_IDEB")
	private int anoIdeb;
	
	@CsvBindByName(column = "IDEB_NOTA")
	private String idebNota;
	
	@CsvBindByName(column = "ANO_REPASSE")
	private int anoRepasse;
	
	@CsvBindByName(column = "ANO_IDH")
	private int anoIdh;

	@CsvBindByName
	private long populacao;

	@CsvBindByName
	private float idhm;

	@CsvBindByName(column = "IDH_EDUCACAO")
	private float idhEducacao;

	@CsvBindByName(column = "IDH_LONGEVIDADE")
	private float idhLongevidade;

	@CsvBindByName(column = "IDH_RENDA")
	private float idhRenda;
	
	@CsvBindByName(column = "VALOR_REPASSADO_EDUCACAO")
	private Double valorRepasseEducacao;
	
	@CsvBindByName
	private Integer matricula;
	
	@CsvBindByName(column = "REPASSE_MATRICULA")
	private Double repassePorMatricula;
	
	@CsvBindByName(column = "ANO_MATRICULA")
	private int anoMatriculas;

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the rede
	 */
	public String getRede() {
		return rede;
	}

	/**
	 * @param rede the rede to set
	 */
	public void setRede(String rede) {
		this.rede = rede;
	}

	/**
	 * @return the idebNota
	 */
	public String getIdebNota() {
		return idebNota;
	}

	/**
	 * @param idebNota the idebNota to set
	 */
	public void setIdebNota(String idebNota) {
		this.idebNota = idebNota;
	}

	/**
	 * @return the anoIdeb
	 */
	public int getAnoIdeb() {
		return anoIdeb;
	}

	/**
	 * @param anoIdeb the anoIdeb to set
	 */
	public void setAnoIdeb(int ano) {
		this.anoIdeb = ano;
	}

	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the populacao
	 */
	public long getPopulacao() {
		return populacao;
	}

	/**
	 * @param populacao the populacao to set
	 */
	public void setPopulacao(long populacao) {
		this.populacao = populacao;
	}

	/**
	 * @return the idhm
	 */
	public float getIdhm() {
		return idhm;
	}

	/**
	 * @param idhm the idhm to set
	 */
	public void setIdhm(float idhm) {
		this.idhm = idhm;
	}

	/**
	 * @return the idhEducacao
	 */
	public float getIdhEducacao() {
		return idhEducacao;
	}

	/**
	 * @param idhEducacao the idhEducacao to set
	 */
	public void setIdhEducacao(float idhEducacao) {
		this.idhEducacao = idhEducacao;
	}

	/**
	 * @return the idhLongevidade
	 */
	public float getIdhLongevidade() {
		return idhLongevidade;
	}

	/**
	 * @param idhLongevidade the idhLongevidade to set
	 */
	public void setIdhLongevidade(float idhLongevidade) {
		this.idhLongevidade = idhLongevidade;
	}

	/**
	 * @return the idhRenda
	 */
	public float getIdhRenda() {
		return idhRenda;
	}

	/**
	 * @param idhRenda the idhRenda to set
	 */
	public void setIdhRenda(float idhRenda) {
		this.idhRenda = idhRenda;
	}

	/**
	 * @return the valorRepasseEducacao
	 */
	public Double getValorRepasseEducacao() {
		return valorRepasseEducacao;
	}

	/**
	 * @param valorRepasseEducacao the valorRepasseEducacao to set
	 */
	public void setValorRepasseEducacao(Double valorRepasseEducacao) {
		this.valorRepasseEducacao = valorRepasseEducacao;
	}
	
	/**
	 * @return the anoRepasse
	 */
	public int getAnoRepasse() {
		return anoRepasse;
	}

	/**
	 * @param anoRepasse the anoRepasse to set
	 */
	public void setAnoRepasse(int anoRepasse) {
		this.anoRepasse = anoRepasse;
	}

	/**
	 * @return the anoIdh
	 */
	public int getAnoIdh() {
		return anoIdh;
	}

	/**
	 * @param anoIdh the anoIdh to set
	 */
	public void setAnoIdh(int anoIdhs) {
		this.anoIdh = anoIdhs;
	}

    /**
     * @return the matricula
     */
    public Integer getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    /**
     * @return the repassePorMatricula
     */
    public Double getRepassePorMatricula() {
        return repassePorMatricula;
    }

    /**
     * 
     */
    public void setRepassePorMatricula() {
        if(Objects.nonNull(this.matricula) && Objects.nonNull(this.valorRepasseEducacao)) {
            if(this.valorRepasseEducacao != 0.0 && this.matricula != 0) {
                this.repassePorMatricula = this.valorRepasseEducacao/this.matricula;
            } else {
                System.out.println("Valor do repasse ou matriculas igual a zero");
                this.repassePorMatricula = 0.0;
            } 
        } else {
            System.out.println("Valor do repasse ou matriculas igual a NULL");
            this.repassePorMatricula = 0.0;
        }
    }
    
    
    /**
     * 
     */
    @Override
    public String toString() {
        
        StringBuilder str = new StringBuilder();
        str.append("uf: ").append(this.uf).append("\n")
           .append("municipio: ").append(this.municipio).append("\n")
           .append("rede: ").append(this.rede).append("\n")
           .append("idebNota: ").append(this.idebNota).append("\n")
           .append("anoIdeb ").append(this.anoIdeb).append("\n")
           .append("periodo ").append(this.periodo).append("\n")
           .append("populacao ").append(this.populacao).append("\n")
           .append("idhm: ").append(this.idhm).append("\n")
           .append("idhEducacao: ").append(this.idhEducacao).append("\n")
           .append("idhLongevidade: ").append(this.idhLongevidade).append("\n")
           .append("idhRenda ").append(this.idhRenda).append("\n")
           .append("valorRepasseEducacao ").append(this.valorRepasseEducacao)
           .append("matricula ").append(this.matricula)
           .append("repassePorMatricula ").append(this.repassePorMatricula);
        
        return str.toString();
    }

    /**
     * @return the anoMatriculas
     */
    public int getAnoMatriculas() {
        return anoMatriculas;
    }

    /**
     * @param anoMatriculas the anoMatriculas to set
     */
    public void setAnoMatriculas(int anoMatriculas) {
        this.anoMatriculas = anoMatriculas;
    }

    /**
     * @return the codigoMunicipio
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * @param codigoMunicipio the codigoMunicipio to set
     */
    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

}
