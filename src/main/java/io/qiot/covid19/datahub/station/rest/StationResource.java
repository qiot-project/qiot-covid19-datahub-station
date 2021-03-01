package io.qiot.covid19.datahub.station.rest;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import io.qiot.covid19.datahub.station.domain.dto.StationDTO;
import io.qiot.covid19.datahub.station.service.StationService;

/**
 * Validation through hibernate validator:
 * https://quarkus.io/guides/validation#rest-end-point-validation
 * 
 * @author andreabattaglia
 *
 */
@Path("/station")
public class StationResource {

    @Inject
    Logger LOGGER;

    @Inject
    StationService service;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<StationDTO> getAll() {
        return service.getAllStations();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public StationDTO getById(@QueryParam("id") @NotNull String id) {
        return service.getById(id);
    }

    /*
     * http://localhost:5033/v1/station?serial=bcyiu35r&name=BattagliaTest&
     * longitude=30&latitude=30
     */
    @Transactional
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@QueryParam("serial") @NotNull String serial,
            @QueryParam("name") @NotNull String name,
            @QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("register(String) - start");
        }

        String id = service.add(serial, name, longitude, latitude);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("register(String) - end");
        }
        return id;
    }

}