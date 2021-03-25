/**
 * 
 */

package io.qiot.covid19.datahub.station.client;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.qiot.covid19.datahub.station.domain.dto.Location;

/**
 * @author andreabattaglia
 *
 */
@Path("/v1")
@RegisterRestClient(configKey = "localization-api")
public interface LocalizationServiceClient {

    /**
     * 
     * Sometime the API return 429...
     * 
     * @param longitude
     * @param latitude
     * @return
     */
    @Retry(maxRetries = 4, delay = 300, delayUnit = ChronoUnit.MILLIS)
    @GET
    @Path("/location")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    Location getLocation(@QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude) throws Exception;

}
