package io.sjcdigital.repasse.model.repasse;

import java.util.Map;

import io.sjcdigital.repasse.model.base.Estado;
import io.sjcdigital.repasse.model.base.Municipio;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class Agregacao {
	
	/**
	 * Ano das transferências
	 */
	private int ano;

	/**
	 * Mês das transferências
	 */
	private int mes;

	/**
	 * Estado
	 */
	private Estado estado;

	/**
	 * O município
	 */
	private Municipio municipio;

	/**
	 * O conjunto chave valor dos dados agregados
	 */
	private Map<Object, Double> dadosAgregados;

	public Agregacao() {

	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public Map<Object, Double> getDadosAgregados() {
		return dadosAgregados;
	}

	public void setDadosAgregados(Map<Object, Double> dadosAgregados) {
		this.dadosAgregados = dadosAgregados;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
