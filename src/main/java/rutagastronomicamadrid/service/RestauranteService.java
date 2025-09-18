package rutagastronomicamadrid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rutagastronomicamadrid.feign.GooglePlacesClient;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.repository.RestauranteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestauranteRepository repository;

    private final GooglePlacesClient client;

    public RestauranteService(RestauranteRepository repository, GooglePlacesClient client) {
        this.repository = repository;
        this.client = client;
    }

    public List<Restaurante> listarTodos() {
        return repository.findAll();
    }

    public Optional<Restaurante> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Restaurante guardar(Restaurante restaurante) {
        return repository.save(restaurante);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Restaurante actualizar(Long id, Restaurante datosActualizados) {
        return repository.findById(id)
                .map(r -> {
                    r.setNombre(datosActualizados.getNombre());
                    r.setDomicilio(datosActualizados.getDomicilio());
                    r.setPricelevel(datosActualizados.getPricelevel());
                    r.setRating(datosActualizados.getRating());
                    r.setLatitude(datosActualizados.getLatitude());
                    r.setLongitude(datosActualizados.getLongitude());
                    r.setPhotoreference(datosActualizados.getPhotoreference());
                    return repository.save(r);
                })
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
    }

    public List<Restaurante> obtenerTopRestaurantes(String platoTipico) {
        String query = String.format(
                "10 mejores restaurantes españoles en capital Madrid que vendan %s",
                platoTipico
        );
        var response = client.buscarRestaurantes(query, apiKey);

        return response.results().stream()
                .map(r -> new Restaurante(
                        r.name(),
                        r.domicilio(),
                        r.priceLevel(),
                        r.rating(),
                        r.geometry().location().lat(),
                        r.geometry().location().lng(),
                        r.photos() != null && !r.photos().isEmpty() ? r.photos().get(0).photoReference() : null
                ))
                .collect(Collectors.toList());
    }



}