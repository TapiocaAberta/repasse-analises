package io.sjcdigital.repasse.model.pojo.ideb;

import java.util.List;

import io.sjcdigital.repasse.model.entity.ideb.Periodo;
import io.sjcdigital.repasse.model.entity.ideb.Rede;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;

/**
 * 
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class IdebPojo {

	private Periodo perido;
	private MunicipioPojo municipio;
	private List<NotaPojo> notas;
	private Rede rede;

	/**
	 * @param perido
	 * @param uf
	 * @param municipio
	 * @param notas
	 */
	public IdebPojo(Periodo perido, MunicipioPojo municipio, Rede rede, List<NotaPojo> notas) {
		super();
		this.perido = perido;
		this.municipio = municipio;
		this.notas = notas;
		this.rede = rede;
	}

	/**
	 * 
	 */
	public IdebPojo() {}
	
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
	public MunicipioPojo getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(MunicipioPojo municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the notas
	 */
	public List<NotaPojo> getNotas() {
		return notas;
	}

	/**
	 * @param notas the notas to set
	 */
	public void setNotas(List<NotaPojo> nostas) {
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
		return "UF: " + this.municipio.getEstado().getUf() + " - MunicipioPojo: " + this.municipio.getNome() + " - Periodo: " + this.perido + " - Rede: "
				+ this.rede.getRedeTexto() + " - Notas { " + this.notas.toString();
	}

}
