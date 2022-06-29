package io.sjcdigital.repasse.model.pojo.base;

import io.sjcdigital.repasse.model.entity.base.Regiao;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class EstadoPojo {
	
	private String uf;
	private String nome;
	private Regiao regiao;
	
	/**
	 * 
	 */
	public EstadoPojo() {}

	/**
	 * @param uf
	 */
	public EstadoPojo(String uf) {
		super();
		this.uf = uf;
		this.regiao = Regiao.porSigla(uf);
	}
	
	/**
	 * 
	 * @param uf
	 * @param nome
	 */
    public EstadoPojo(String uf, String nome) {
        this(uf);
        this.nome = nome;
    }

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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the regiao
     */
    public Regiao getRegiao() {
        return regiao;
    }

    /**
     * @param regiao the regiao to set
     */
    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

}
