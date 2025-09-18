package rutagastronomicamadrid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Photo(@JsonProperty("photo_reference") String photoReference) {}
