package io.sjcdigital.repasse.controller.repasse;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sjcdigital.repasse.model.entity.repasse.TipoAgregacao;
import io.sjcdigital.repasse.model.pojo.base.DadosMunicipioPojo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;
import io.sjcdigital.repasse.model.pojo.repasse.AgregacaoPojo;
import io.sjcdigital.repasse.resource.client.repasse.AgregacaoServiceClient;
import io.sjcdigital.repasse.resource.client.repasse.MunicipioServiceClient;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseController {
	
	private static final String EDUCAÇÃO = "educação";
	private static final String FUNDEB = "fundeb";
	
	public static final String PATH = "http://repasse.icmc.usp.br/rest"; 
	private static final Logger LOGGER = LoggerFactory.getLogger(RepasseController.class);
	
	/**
	 * 
	 * @param ano
	 * @param idMunicipio
	 * @return
	 */
	public Optional<Double> buscaValoresAgregadosFundeb(final int ano, final int idMunicipio) {
	    return buscaValoresAgregadosPorLabel(ano, idMunicipio, FUNDEB, TipoAgregacao.ACAO);
	}
	
	/**
	 * 
	 * @param ano
	 * @param idMunicipio
	 * @return
	 */
	public Optional<Double> buscaValoresAgregadosEducacao(final int ano, final int idMunicipio) {
		return buscaValoresAgregadosPorLabel(ano, idMunicipio, EDUCAÇÃO, TipoAgregacao.AREA);
	}
	
	
	/**
	 * 
	 * @param ano
	 * @param idMunicipio
	 * @return
	 */
	public Optional<Double> buscaValoresAgregadosEducacao(final int ano, final int mes, final int idMunicipio) {
		return buscaValoresAgregadosPorLabel(ano, mes, idMunicipio, EDUCAÇÃO, TipoAgregacao.AREA);
	}
	
	/**
	 * 
	 * @param ano
	 * @param idMunicipio
	 * @param label
	 * @param tipoAgregacao 
	 * @return
	 */
	public Optional<Double> buscaValoresAgregadosPorLabel(final int ano, final int idMunicipio, final String label, final TipoAgregacao tipoAgregacao) {
	    
		Optional<AgregacaoPojo> buscaAgregacaoRepasse = buscaAgregacaoRepasse(ano, idMunicipio, tipoAgregacao);
		
		if(buscaAgregacaoRepasse.isPresent()) {
		    return Optional.ofNullable(buscaAgregacaoRepasse.get().getDadosAgregados()
	                                                              .entrySet().stream()
	                                                              .filter(m -> ((String) m.getKey()).toLowerCase().contains(label) )
	                                                              .map(x -> x.getValue())
	                                                              .reduce(0.0, Double::sum));
		    
		} else {
			return Optional.empty();
		}
		
	}
	
	
	/**
	 * 
	 * @param ano
	 * @param mes
	 * @param idMunicipio
	 * @param label
	 * @param tipoAgregacao 
	 * @return
	 */
	public Optional<Double> buscaValoresAgregadosPorLabel(final int ano, final int mes, final int idMunicipio, final String label, TipoAgregacao tipoAgregacao) {

		Optional<AgregacaoPojo> repasse = buscaAgregacaoRepasse(tipoAgregacao, ano, mes, idMunicipio);

		if(repasse.isPresent()) {
            return Optional.ofNullable(repasse.get().getDadosAgregados()
                                                    .entrySet().stream()
                                                    .filter(m -> ((String) m.getKey()).toLowerCase().contains(label) )
                                                    .map(x -> x.getValue())
                                                    .reduce(0.0, Double::sum));
            
        } else {
            return Optional.empty();
        }

	}
	
	/**
	 * 
	 * @param sigla
	 * @param nome
	 * @return
	 */
	public Optional<MunicipioPojo> buscaMunicipio(final String sigla, final String nome) {
		LOGGER.info("Buscando MunicipioPojo do Site do Repasse: " + nome + " - " + sigla);
		MunicipioServiceClient proxy = getTarget().proxy(MunicipioServiceClient.class);
		
		try {
		    MunicipioPojo municipio = proxy.buscaMunicipio(sigla, nome);
			return Optional.ofNullable(municipio);
		} catch (NotFoundException e) {
			LOGGER.info("MunicipioPojo não encontrado: " + nome + " - " + sigla);
			System.out.println("MunicipioPojo não encontrado: " + nome + " - " + sigla);
			return Optional.empty();
		}
		
	}
	
	/**
	 * 
	 * @param ano
	 * @param mes
	 * @param idMunicipio
	 * @return
	 */
	public Optional<AgregacaoPojo> buscaAgregacaoRepasse(final TipoAgregacao tipoAgregacao, final int ano, final int mes, final long idMunicipio ) {
		
		LOGGER.info("Buscando dados de Agregação do Site do Repasse");
		AgregacaoServiceClient proxy = getTarget().proxy(AgregacaoServiceClient.class);
		
		try {
			return Optional.ofNullable(proxy.agregaPorAreaAnoMesMunicipio(tipoAgregacao, ano, mes, idMunicipio));
		} catch (NotFoundException e) {
			LOGGER.info("Repasse não encontrado: " + ano + "/" + mes + ". Para municipio " + idMunicipio);
			System.out.println("Repasse não encontrado: " + ano + "/" + mes + ". Para municipio " + idMunicipio);
			return Optional.empty();
		}
	}
	
	/**
	 * 
	 * @param ano
	 * @param idMunicipio
	 * @param tipoAgregacao 
	 * @return
	 */
	public Optional<AgregacaoPojo> buscaAgregacaoRepasse(final int ano, final long idMunicipio, final TipoAgregacao tipoAgregacao ) {
	    
		LOGGER.info("Buscando dados de Agregação do Site do Repasse");
		AgregacaoServiceClient proxy = getTarget().proxy(AgregacaoServiceClient.class);
		
		try {
		    return Optional.ofNullable(proxy.agregaPorAreaAnoMunicipio(tipoAgregacao, ano, idMunicipio));
		} catch (NotFoundException e) {
            LOGGER.info("Dados de agregação não encontrado: " + ano + ". Para municipio " + idMunicipio);
            System.out.println("Dados de agregação não encontrado: " + ano + ". Para municipio " + idMunicipio);
            return Optional.empty();
        }
	}
	
	/**
	 * 
	 * @param sigla
	 * @param nome
	 * @return
	 */
	public List<DadosMunicipioPojo> buscaDadosMunicipio(final String sigla, final String nome) {
		LOGGER.info("Buscando dados de MunicipioPojo do Site do Repasse: " + nome + " - " + sigla);
		MunicipioServiceClient proxy = getTarget().proxy(MunicipioServiceClient.class);
		return proxy.buscaDadosMunicipio(sigla, nome);
	}

	/**
	 * 
	 * @return
	 */
	private static ResteasyWebTarget getTarget() {
		ResteasyClient client = new ResteasyClientBuilderImpl().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(PATH));
		return target;
	}

}
