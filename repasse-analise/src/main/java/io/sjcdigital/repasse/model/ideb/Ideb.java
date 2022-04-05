package io.sjcdigital.repasse.model.ideb;

import java.util.List;

import io.sjcdigital.repasse.model.base.Municipio;

/**
 * 
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class Ideb {

	private Periodo perido;
	private Municipio municipio;
	private List<Nota> notas;
	private Rede rede;

	/**
	 * @param perido
	 * @param uf
	 * @param municipio
	 * @param notas
	 */
	public Ideb(Periodo perido, Municipio municipio, Rede rede, List<Nota> notas) {
		super();
		this.perido = perido;
		this.municipio = municipio;
		this.notas = notas;
		this.rede = rede;
	}

	/**
	 * 
	 */
	public Ideb() {}
	
	/**
	 * Retorna se a nota é de rede pública, ou seja media das escolas municipais, estaduais e federais;
	 * @return
	 */
	public boolean ehRedePublica() {
		return this.rede.equals(Rede.PUBLICA);
	}

	/**
	 * @return the rede
	 */
	public Rede getRede() {
		return rede;
	}

	/**
	 * @param rede the rede to set
	 */
	public void setRede(Rede rede) {
		this.rede = rede;
	}

	/**
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the notas
	 */
	public List<Nota> getNotas() {
		return notas;
	}

	/**
	 * @param notas the notas to set
	 */
	public void setNotas(List<Nota> nostas) {
		this.notas = nostas;
	}

	/**
	 * @return the perido
	 */
	public Periodo getPerido() {
		return perido;
	}

	/**
	 * @param perido the perido to set
	 */
	public void setPerido(Periodo perido) {
		this.perido = perido;
	}

	@Override
	public String toString() {
		return "UF: " + this.municipio.getEstado().getUf() + " - Municipio: " + this.municipio.getNome() + " - Periodo: " + this.perido + " - Rede: "
				+ this.rede.getRedeTexto() + " - Notas { " + this.notas.toString();
	}

}
