package io.sjcdigital.repasse.model.pojo.repasse;

import java.util.Map;

import io.sjcdigital.repasse.model.pojo.base.EstadoPojo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class AgregacaoPojo {
	
	/**
	 * Ano das transferências
	 */
	private int ano;

	/**
	 * Mês das transferências
	 */
	private int mes;

	/**
	 * EstadoPojo
	 */
	private EstadoPojo estado;

	/**
	 * O município
	 */
	private MunicipioPojo municipio;

	/**
	 * O conjunto chave valor dos dados agregados
	 */
	private Map<Object, Double> dadosAgregados;

	public AgregacaoPojo() {

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

	public MunicipioPojo getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioPojo municipio) {
		this.municipio = municipio;
	}

	public EstadoPojo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPojo estado) {
		this.estado = estado;
	}

}
