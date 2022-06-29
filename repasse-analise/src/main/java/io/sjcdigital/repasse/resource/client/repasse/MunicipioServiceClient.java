package io.sjcdigital.repasse.resource.client.repasse;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.sjcdigital.repasse.model.pojo.base.DadosMunicipioPojo;
import io.sjcdigital.repasse.model.pojo.base.MunicipioPojo;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */

@Path("/municipio")
@RegisterRestClient(configKey="repasse-api")
public interface MunicipioServiceClient {
	
	@GET
	@Path("/{sigla}/{nome}")
	MunicipioPojo buscaMunicipio(@PathParam("sigla") String sigla, @PathParam("nome") String nome);
	
	@GET
	@Path("/{sigla}/{nome}/dados")
	List<DadosMunicipioPojo> buscaDadosMunicipio(@PathParam("sigla") String sigla, @PathParam("nome") String nome);

}
