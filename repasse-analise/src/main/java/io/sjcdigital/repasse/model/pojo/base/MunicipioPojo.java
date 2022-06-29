package io.sjcdigital.repasse.model.pojo.base;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class MunicipioPojo {

	private int id;
	private String nome;
	private EstadoPojo estado;
	private String codMunicipio;
	private Integer populacao;

	/**
	 * 
	 */
	public MunicipioPojo() { }

	/**
     * @param nome
     * @param estado
     */
    public MunicipioPojo(String nome, EstadoPojo estado, String codMunicipio, Integer populacao) {
        super();
        this.nome = nome;
        this.estado = estado;
        this.populacao = populacao;
        this.codMunicipio = codMunicipio;
    }
    
	/**
	 * @param nome
	 * @param estado
	 */
	public MunicipioPojo(String nome, EstadoPojo estado) {
		super();
		this.nome = nome;
		this.estado = estado;
	}
	
	/**
	 * 
	 * @param nome
	 * @param estado
	 * @param codMunicipio
	 */
	public MunicipioPojo(String nome, EstadoPojo estado, String codMunicipio) {
        super();
        this.nome = nome;
        this.estado = estado;
        this.codMunicipio = codMunicipio;
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
	 * @return the estado
	 */
	public EstadoPojo getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoPojo estado) {
		this.estado = estado;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

    /**
     * @return the codMunicipio
     */
    public String getCodMunicipio() {
        return codMunicipio;
    }

    /**
     * @param codMunicipio the codMunicipio to set
     */
    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
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
