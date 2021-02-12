package io.qiot.covid19.datahub.station.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.covid19.datahub.station.client.LocalizationServiceClient;
import io.qiot.covid19.datahub.station.domain.dto.StationDTO;
import io.qiot.covid19.datahub.station.domain.pojo.Station;
import io.qiot.covid19.datahub.station.persistence.StationRepository;
import io.qiot.covid19.datahub.station.util.converter.StationConverter;

@ApplicationScoped
public class StationService {
    private static final String COUNTRY_CODE = "ccode";

    private static final String COUNTRY = "country";

    private static final String CITY = "city";

    @Inject
    Logger LOGGER;

//    @Inject
//    GeometryFactory gfactory;

    @Inject
    StationRepository repository;

    @Inject
    @RestClient
    LocalizationServiceClient serviceClient;

    @Inject
    StationConverter converter;

    public String add(String serial, String name, double longitude,
            double latitude) {
        Station station = new Station();
        station.serial = serial;
        station.name = name;
//        station.geometry = gfactory
//                .createPoint(new Coordinate(longitude, latitude));
        station.longitude=longitude;
        station.latitude=latitude;

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
            LOGGER.error(
                    "An error occurred retrieving city and country for the following coordinates: "
                            + "[longitude=" + longitude
                            + ",latitude="+latitude+"] :\n{}",
                    e);
        }

        repository.persist(station);
        return station.id.toString();
    }

    public StationDTO getById(String id) {
        Station station = repository.findById(id);
        return converter.convert(station);
    }

    public List<StationDTO> getAllStations() {
        List<StationDTO> stationDTOs = null;
        List<Station> stations = repository.findAllStations();
        stationDTOs = new ArrayList<>(stations.size());
        for (Station station : stations)
            stationDTOs.add(converter.convert(station));
        return stationDTOs;
    }
}
