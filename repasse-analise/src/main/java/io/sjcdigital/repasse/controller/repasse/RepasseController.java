package io.sjcdigital.repasse.controller.repasse;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sjcdigital.repasse.model.base.DadosMunicipio;
import io.sjcdigital.repasse.model.base.Municipio;
import io.sjcdigital.repasse.model.repasse.Agregacao;
import io.sjcdigital.repasse.resource.repasse.client.AgregacaoServiceClient;
import io.sjcdigital.repasse.resource.repasse.client.MunicipioServiceClient;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class RepasseController {
	
	public static final String PATH = "http://repasse.icmc.usp.br/rest"; 
	private static final Logger LOGGER = LoggerFactory.getLogger(RepasseController.class);
	
	/*
	 * @Inject
	 * @RestClient 
	 * MunicipioServiceClient dadosMunClient;
	 * buscaDadosMunicipio.stream().filter(m -> m.getAno() == 2010).forEach(s -> System.out.println(s.getIdhEducacao()));
	 */
	
	public static void main(String[] args) {
		Agregacao buscaAgregacaoRepasse = new RepasseController().buscaAgregacaoRepasse(2017, 11947);
		buscaAgregacaoRepasse.getDadosAgregados().entrySet().stream().filter(m -> "Educação".equals(m.getKey())).forEach(System.out::println);
	}
	
	public Optional<Double> buscaValoresAgregadosEducacao(final int ano, final int idMunicipio) {
		Optional<Entry<Object, Double>> agregacao = buscaAgregacaoRepasse(ano, idMunicipio).getDadosAgregados()
																						   .entrySet().stream()
																						   .filter(m -> "Educação".equals(m.getKey()))
																						   .findAny();
		if(agregacao.isPresent()) {
			return Optional.of(agregacao.get().getValue());
		} else {
			
		}
		
		return Optional.empty();
	}
	
	/**
	 * 
	 * @param sigla
	 * @param nome
	 * @return
	 */
	public Optional<Municipio> buscaMunicipio(final String sigla, final String nome) {
		LOGGER.info("Buscando Municipio do Site do Repasse: " + nome + " - " + sigla);
		MunicipioServiceClient proxy = getTarget().proxy(MunicipioServiceClient.class);
		
		try {
			Municipio municipio = proxy.buscaMunicipio(sigla, nome);
			return Optional.ofNullable(municipio);
		} catch (NotFoundException e) {
			System.out.println("Municipio não encontrado: " + nome + " - " + sigla);
			return Optional.empty();
		}
		
	}
	
	/**
	 * 
	 * @param ano
	 * @param idMunicipio
	 * @return
	 */
	public Agregacao buscaAgregacaoRepasse(final int ano, final long idMunicipio ) {
		LOGGER.info("Buscando dados de Agregação do Site do Repasse");
		AgregacaoServiceClient proxy = getTarget().proxy(AgregacaoServiceClient.class);
		return proxy.agregaPorAnoMunicipio(ano, idMunicipio);
	}
	
	/**
	 * 
	 * @param sigla
	 * @param nome
	 * @return
	 */
	public List<DadosMunicipio> buscaDadosMunicipio(final String sigla, final String nome) {
		LOGGER.info("Buscando dados de Municipio do Site do Repasse: " + nome + " - " + sigla);
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
