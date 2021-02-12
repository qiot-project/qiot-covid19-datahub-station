package io.qiot.covid19.datahub.station.domain.pojo;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Entity
@Cacheable
@Table(name = "station")
@RegisterForReflection
public class Station extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    public UUID id;
    @Column(nullable = false, unique = true)
    public String serial;
    @Column(nullable = false)
    public String name;
    // only allow geometries with SRID 4326 and not null (be able to create
    // spatial indexes)
    // @Column(nullable = false, columnDefinition = "GEOMETRY SRID 4326")
    // public Geometry<Position> geometry;
    // @Column(nullable = false, columnDefinition = "GEOMETRY SRID 4326")
    // public Point geometry;
    @Column(nullable = false)
    public double longitude;
    @Column(nullable = false)
    public double latitude;
    @Column(nullable = true)
    public String city;
    @Column(nullable = true)
    public String country;
    @Column(name = "country_code", nullable = true)
    public String countryCode;
    @Column(name = "registered_on", columnDefinition = "TIMESTAMP")
    public Instant registeredOn;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Station other = (Station) obj;
        return id == other.id;
    }

}