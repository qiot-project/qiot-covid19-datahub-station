package org.qiot.covid19.datahub.station.domain.dto;

import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class StationDTO implements Comparable<StationDTO> {
    public String id;
    public String serial;
    public String name;
    public double longitude;
    public double latitude;
    public String city;
    public String country;

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
        StationDTO other = (StationDTO) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StationDTO [id=");
        builder.append(id);
        builder.append(", serial=");
        builder.append(serial);
        builder.append(", name=");
        builder.append(name);
        builder.append(", longitude=");
        builder.append(longitude);
        builder.append(", latitude=");
        builder.append(latitude);
        builder.append(", city=");
        builder.append(city);
        builder.append(", country=");
        builder.append(country);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(StationDTO o) {
        return id.compareTo(o.id);
    }
}