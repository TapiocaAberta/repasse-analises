package io.sjcdigital.repasse.resource.client.repasse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.sjcdigital.repasse.model.entity.repasse.TipoAgregacao;
import io.sjcdigital.repasse.model.pojo.repasse.AgregacaoPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
@Path("/agregacao")
@RegisterRestClient(configKey="repasse-api")
public interface AgregacaoServiceClient {
	
	@GET
	@Path("/{tipoAgregacao}/{ano}/municipio/{idMunicipio}")	
	public AgregacaoPojo agregaPorAreaAnoMunicipio(@PathParam("tipoAgregacao") TipoAgregacao tipoAgregacao, @PathParam("ano") int ano, @PathParam("idMunicipio") long idMunicipio);
	
	@GET
	@Path("/{tipoAgregacao}/{ano}/{mes}/municipio/{idMunicipio}")	
	public AgregacaoPojo agregaPorAreaAnoMesMunicipio(@PathParam("tipoAgregacao") TipoAgregacao tipoAgregacao, @PathParam("ano") int ano, @PathParam("mes") int mes, @PathParam("idMunicipio") long idMunicipio);
 

}
