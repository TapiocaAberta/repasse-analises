package io.sjcdigital.repasse.model.repasse;

import com.opencsv.bean.CsvBindByName;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseIDEB {

	@CsvBindByName
	private String uf;

	@CsvBindByName
	private String municipio;

	@CsvBindByName
	private String rede;

	@CsvBindByName
	private String idebNota;

	@CsvBindByName
	private int ano;

	@CsvBindByName
	private String periodo;

	@CsvBindByName
	private long populacao;

	@CsvBindByName
	private float idhm;

	@CsvBindByName
	private float idhEducacao;

	@CsvBindByName
	private float idhLongevidade;

	@CsvBindByName
	private float idhRenda;
	
	@CsvBindByName
	private Double valorRepasseEducacao;

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
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		str.append("uf: ").append(this.uf).append("\n")
		   .append("municipio: ").append(this.municipio).append("\n")
		   .append("rede: ").append(this.rede).append("\n")
		   .append("idebNota: ").append(this.idebNota).append("\n")
		   .append("ano ").append(this.ano).append("\n")
		   .append("periodo ").append(this.periodo).append("\n")
		   .append("populacao ").append(this.populacao).append("\n")
		   .append("idhm: ").append(this.idhm).append("\n")
		   .append("idhEducacao: ").append(this.idhEducacao).append("\n")
		   .append("idhLongevidade: ").append(this.idhLongevidade).append("\n")
		   .append("idhRenda ").append(this.idhRenda).append("\n")
		   .append("valorRepasseEducacao ").append(this.valorRepasseEducacao);
		
		return str.toString();
	}

}
