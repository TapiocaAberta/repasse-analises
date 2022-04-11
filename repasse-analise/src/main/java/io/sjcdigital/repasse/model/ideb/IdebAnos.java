package io.sjcdigital.repasse.model.ideb;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 * Anos disponíveis para consulta no site do IDEB
 */
public class IdebAnos {
	
	public static Integer[] ANOS_INICIAIS = {2005, 2007, 2009, 2011, 2013, 2015, 2017, 2019};
	public static Integer[] ANOS_FINAIS = {2005, 2007, 2009, 2011, 2013, 2015, 2017, 2019};
	public static Integer[] ANOS_ENSINO_MEDIO = {2017, 2019};
	
	public static Integer[] pegaListaDeAnosPorPeriodo(Periodo periodo) {
		switch (periodo) {
			case FUNDAMENTAL_ANOS_INICIAIS:
				return ANOS_INICIAIS;
			case FUNDAMENTAL_ANOS_FINAIS:
				return ANOS_FINAIS;
			case ENSINO_MEDIO:
				return ANOS_ENSINO_MEDIO;
			default:
				return new Integer[]{};
		}
	}

}
