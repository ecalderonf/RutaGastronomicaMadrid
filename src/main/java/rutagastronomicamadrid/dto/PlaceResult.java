package rutagastronomicamadrid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlaceResult(
        String name,
        @JsonProperty("formatted_address") String domicilio,
        @JsonProperty("price_level") Integer priceLevel,
        Double rating,
        Geometry geometry,
        List<Photo> photos
) {}

