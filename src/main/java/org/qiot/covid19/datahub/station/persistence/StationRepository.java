package org.qiot.covid19.datahub.station.persistence;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.qiot.covid19.datahub.station.domain.pojo.Station;
import org.slf4j.Logger;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

/**
 * @author abattagl
 *
 */
@ApplicationScoped
public class StationRepository implements PanacheRepositoryBase<Station, UUID> {

    @Inject
    Logger LOGGER;

    /**
     * @param id
     * @return
     */
    public Station findById(String id) {
        LOGGER.debug("Searching for Measurement StationDTO with ID {}", id);
        Station ms = findById(UUID.fromString(id));
        if (ms != null)
            LOGGER.debug("Found StationDTO {}", ms);
        return ms;
    }

}
