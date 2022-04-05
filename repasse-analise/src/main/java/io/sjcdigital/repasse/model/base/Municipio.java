package io.sjcdigital.repasse.model.base;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class Municipio {

	private int id;
	private String nome;
	private Estado estado;

	/**
	 * 
	 */
	public Municipio() {
	}

	/**
	 * @param nome
	 * @param estado
	 */
	public Municipio(String nome, Estado estado) {
		super();
		this.nome = nome;
		this.estado = estado;
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
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
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

}
