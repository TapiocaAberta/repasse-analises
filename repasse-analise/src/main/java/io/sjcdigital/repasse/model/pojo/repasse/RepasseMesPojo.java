package io.sjcdigital.repasse.model.pojo.repasse;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseMesPojo {
	
	@CsvBindByName(column = "UF")
	private String uf;

	@CsvBindByName(column = "MUNICIPIO")
	private String municipio;
	
	@CsvBindByName(column = "DATA")
	private LocalDate data;
	
	@CsvBindByName(column = "VALOR")
	private Double valor;
	
	@CsvBindByName(column = "AREA")
	private String area;
	
	/**
	 * @param uf
	 * @param municipio
	 * @param data
	 * @param valor
	 * @param area
	 */
	public RepasseMesPojo(String uf, String municipio, LocalDate data, Double valor, String area) {
		super();
		this.uf = uf;
		this.municipio = municipio;
		this.data = data;
		this.valor = valor;
		this.area = area;
	}

	/**
	 * 
	 */
	public RepasseMesPojo() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the data
	 */
	public LocalDate getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setAnoRepasse(LocalDate data) {
		this.data = data;
	}

	/**
	 * @return the valor
	 */
	public Double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

}
