package io.sjcdigital.repasse.model.ideb;

/**
 * 
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class Nota {

	private Integer ano;
	private String notaIdeb;
	
	/**
	 * 
	 */
	public Nota() {}

	/**
	 * @param ano
	 * @param rede
	 * @param notaIdeb
	 */
	public Nota(Integer ano, String notaIdeb) {
		super();
		this.ano = ano;
		this.notaIdeb = notaIdeb;
	}

	/**
	 * @return the ano
	 */
	public Integer getAno() {
		return ano;
	}

	/**
	 * @param ano the ano to set
	 */
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	/**
	 * @return the notaIdeb
	 */
	public String getNotaIdeb() {
		return notaIdeb;
	}

	/**
	 * @param notaIdeb the notaIdeb to set
	 */
	public void setNotaIdeb(String notaIdeb) {
		this.notaIdeb = notaIdeb;
	}
	
	@Override
	public String toString() {
		return "ano: " + this.ano + " - nota: " + this.getNotaIdeb();
	}

}
