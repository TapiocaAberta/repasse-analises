package io.sjcdigital.repasse.resource.repasse.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.sjcdigital.repasse.model.repasse.Agregacao;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
@Path("/agregacao")
@RegisterRestClient(configKey="repasse-api")
public interface AgregacaoServiceClient {
	
	@GET
	@Path("/AREA/{ano}/municipio/{idMunicipio}")	
	public Agregacao agregaPorAnoMunicipio(@PathParam("ano") int ano, @PathParam("idMunicipio") long idMunicipio);
 

}
