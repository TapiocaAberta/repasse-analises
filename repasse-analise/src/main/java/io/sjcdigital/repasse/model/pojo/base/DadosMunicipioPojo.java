package io.sjcdigital.repasse.model.pojo.base;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class DadosMunicipioPojo {
	
	private MunicipioPojo municipio;
	private int ano;
	private long populacao;
	private float idhm;
	
	/**
	 * IDH educação
	 */
	private float idhEducacao;
	
	/**
	 * IDH Longetividade
	 */
	private float idhLongevidade;
	
	/**
	 * IDH Renda
	 */
	private float idhRenda;

	/**
	 * @return the ano
	 */
	public int getAno() {
		return ano;
	}

	/**
	 * @param ano the ano to set
	 */
	public void setAno(int ano) {
		this.ano = ano;
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

}
