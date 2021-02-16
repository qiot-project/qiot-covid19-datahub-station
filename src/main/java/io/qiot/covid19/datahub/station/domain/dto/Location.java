package io.qiot.covid19.datahub.station.domain.dto;

import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Location implements Comparable<Location> {

    public String city;
    public String country;
    public String ccode;

    @Override
    public int hashCode() {
        return Objects.hash(city, country);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        return Objects.equals(city, other.city)
                && Objects.equals(country, other.country);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Location [city=");
        builder.append(city);
        builder.append(", country=");
        builder.append(country);
        builder.append(", country_code=");
        builder.append(ccode);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(Location o) {
        if (country.compareTo(o.country) != 0)
            return city.compareTo(o.city);
        return 0;
    }
}