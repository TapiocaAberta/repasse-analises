package io.sjcdigital.repasse.model.base;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class Estado {
	
	private String uf;
	
	/**
	 * 
	 */
	public Estado() {}

	/**
	 * @param uf
	 */
	public Estado(String uf) {
		super();
		this.uf = uf;
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

}
