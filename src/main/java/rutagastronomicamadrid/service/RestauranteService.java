package rutagastronomicamadrid.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rutagastronomicamadrid.controller.RestauranteController;
import rutagastronomicamadrid.dto.RestauranteAltaDTO;
import rutagastronomicamadrid.feign.RestauranteClient;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.repository.RestauranteRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    @Value("${google.api.key}")
    private String apiKey;

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);

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

    public List<Restaurante> obtenerPorNombrePlatoTipico(String nombrePlato) {
        return restauranteRepository.findByNombrePlatoTipico(nombrePlato.trim());
    }

    public List<Restaurante> obtenerTop10PorRating() {
        return restauranteRepository.findTop10ByOrderByRatingDesc();
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


    public List<Restaurante> obtenerRestaurantesPorPlato(String platoTipico) {
        String query = String.format(
                "restaurantes o bares españoles en capital Madrid que vendan %s",
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

    public List<Restaurante> obtenerRestaurantesPorNombre(RestauranteAltaDTO restauranteAltaDTO) {
        String query = String.format(" \"%s\" + %s",
                restauranteAltaDTO.getNombre(), restauranteAltaDTO.getCodigoPostal()
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
