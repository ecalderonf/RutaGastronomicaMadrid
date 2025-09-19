package rutagastronomicamadrid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rutagastronomicamadrid.feign.RestauranteClient;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.repository.RestauranteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestauranteRepository restauranteRepository;

    private final RestauranteClient client;

    public RestauranteService(RestauranteRepository restauranteRepository, RestauranteClient client) {
        this.restauranteRepository = restauranteRepository;
        this.client = client;
    }

    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> obtenerPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    public Optional<Restaurante> obtenerPorReference(String reference) {
        return restauranteRepository.findByReference(reference);
    }

    public List<Restaurante> buscarPorNombrePlatoTipico(String nombrePlato) {
        return restauranteRepository.findByNombrePlatoTipico(nombrePlato.trim());
    }
    public Restaurante guardar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public void eliminar(Long id) {
        restauranteRepository.deleteById(id);
    }

    public Restaurante actualizar(Long id, Restaurante datosActualizados) {
        return restauranteRepository.findById(id)
                .map(r -> {
                    r.setNombre(datosActualizados.getNombre());
                    r.setDomicilio(datosActualizados.getDomicilio());
                    r.setPricelevel(datosActualizados.getPricelevel());
                    r.setRating(datosActualizados.getRating());
                    r.setLatitude(datosActualizados.getLatitude());
                    r.setLongitude(datosActualizados.getLongitude());
                    r.setPhotoreference(datosActualizados.getPhotoreference());
                    r.setPlatosTipicos(datosActualizados.getPlatosTipicos());
                    return restauranteRepository.save(r);
                })
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
    }

    public List<Restaurante> obtenerTopRestaurantes(String platoTipico) {
        String query = String.format(
                "10 mejores restaurantes españoles en capital Madrid que vendan %s",
                platoTipico
        );
        var response = client.buscarRestaurantes(query, apiKey);

        return response.getBody().results().stream()
                .map(r -> new Restaurante(
                        r.reference(),
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