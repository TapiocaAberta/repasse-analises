package io.sjcdigital.repasse.resource.repasse.client;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.sjcdigital.repasse.model.base.DadosMunicipio;
import io.sjcdigital.repasse.model.base.Municipio;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */

@Path("/municipio")
@RegisterRestClient(configKey="repasse-api")
public interface MunicipioServiceClient {
	
	@GET
	@Path("/{sigla}/{nome}")
	Municipio buscaMunicipio(@PathParam("sigla") String sigla, @PathParam("nome") String nome);
	
	@GET
	@Path("/{sigla}/{nome}/dados")
	List<DadosMunicipio> buscaDadosMunicipio(@PathParam("sigla") String sigla, @PathParam("nome") String nome);

}
