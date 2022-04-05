package io.sjcdigital.repasse.model.ideb;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public enum Rede {

	MUNICIPAL("Municipal"), ESTADUAL("Estadual"), FEDERAL("Federal"), PUBLICA("Pública"), OUTRA("Outra");

	private String redeTexto;

	Rede(String texto) {
		this.redeTexto = texto;
	}

	public static Rede converteRedeStringParaRede(String rede) {

		rede = rede.toLowerCase();

		switch (rede) {
		case "municipal":
			return Rede.MUNICIPAL;
		case "federal":
			return Rede.FEDERAL;
		case "estadual":
			return Rede.ESTADUAL;
		case "pública":
			return Rede.PUBLICA;
		default:
			return Rede.OUTRA;
		}

	}

	/**
	 * @return the redeTexto
	 */
	public String getRedeTexto() {
		return redeTexto;
	}

}
