package org.qiot.covid19.datahub.station.service;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.qiot.covid19.datahub.station.client.LocalizationServiceClient;
import org.qiot.covid19.datahub.station.domain.dto.StationDTO;
import org.qiot.covid19.datahub.station.domain.pojo.Station;
import org.qiot.covid19.datahub.station.persistence.StationRepository;
import org.slf4j.Logger;

@ApplicationScoped
public class StationService {
    private static final String COUNTRY_CODE = "ccode";

    private static final String COUNTRY = "country";

    private static final String CITY = "city";

    @Inject
    Logger LOGGER;

    @Inject
    GeometryFactory gfactory;

    @Inject
    StationRepository repository;

    @Inject
    @RestClient
    LocalizationServiceClient serviceClient;

    public String add(String serial, String name, double longitude,
            double latitude) {
        Station station = new Station();
        station.serial = serial;
        station.name = name;
        station.geometry = gfactory
                .createPoint(new Coordinate(longitude, latitude));

        String jsonResult;
        try {
            jsonResult = serviceClient.getLocation(longitude, latitude);
            LOGGER.debug(jsonResult);
            try (StringReader sr = new StringReader(jsonResult);
                    JsonReader reader = Json.createReader(sr)) {
                JsonObject jsonObject = reader.readObject();

                if (jsonObject.containsKey(CITY))
                    station.city = jsonObject.getString(CITY);
                if (jsonObject.containsKey(COUNTRY)) {
                    station.country = jsonObject.getString(COUNTRY);
                    station.countryCode = jsonObject.getString(COUNTRY_CODE)
                            .toUpperCase();
                }
            }
        } catch (Exception e) {
            LOGGER.debug("An error occurred retrieving city and country", e);
            LOGGER.warn(
                    "An error occurred retrieving city and country for the following coordinates: "
                            + "[longitude={},latitude={}] :\n{}",
                    longitude, latitude, e.getCause());
        }

        repository.persist(station);
        return station.id.toString();
    }

    public StationDTO getById(String id) {
        Station station = repository.findById(id);
        StationDTO dto = new StationDTO();
        dto.id = station.id.toString();
        dto.serial = station.serial;
        dto.name = station.name;
        dto.longitude = station.geometry.getX();
        dto.latitude = station.geometry.getY();
        dto.city = station.city;
        dto.country = station.country;
        station = null;
        return dto;
    }
}
