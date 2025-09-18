package rutagastronomicamadrid.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rutagastronomicamadrid.dto.GooglePlacesResponse;

@FeignClient(name = "restauranteClient", url = "https://maps.googleapis.com/maps/api/place")
public interface RestauranteClient {

    @GetMapping("/textsearch/json")
    ResponseEntity<GooglePlacesResponse> buscarRestaurantes(
            @RequestParam("query") String query,
            @RequestParam("key") String apiKey
    );
}


