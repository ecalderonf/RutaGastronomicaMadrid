package rutagastronomicamadrid.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rutagastronomicamadrid.dto.GooglePlacesResponse;

@FeignClient(name = "googlePlacesClient", url = "https://maps.googleapis.com/maps/api/place")
public interface GooglePlacesClient {

    @GetMapping("/textsearch/json")
        GooglePlacesResponse buscarRestaurantes(
            @RequestParam("query") String query,
            @RequestParam("key") String apiKey
    );
}


