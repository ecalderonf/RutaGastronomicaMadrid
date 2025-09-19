package rutagastronomicamadrid.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rutagastronomicamadrid.model.PlatoTipico;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.service.PlatoTipicoService;
import rutagastronomicamadrid.service.RestauranteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "http://localhost:4200")
public class RestauranteController {

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);
    private final RestauranteService restauranteService;
    private final PlatoTipicoService platoTipicoService;

    public RestauranteController(RestauranteService restauranteService, PlatoTipicoService platoTipicoService) {
        this.restauranteService = restauranteService;
        this.platoTipicoService = platoTipicoService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Restaurante> listaRestaurantes = restauranteService.listarTodos();
        return ResponseEntity.ok(listaRestaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> obtenerPorId(@PathVariable Long id) {
        return restauranteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-plato")
    public ResponseEntity<List<Restaurante>> buscarPorPlatoTipico(@RequestParam String nombrePlato) {
        log.info("buscarPorPlatoTipico - nombrePlato: '{}'", nombrePlato);
        List<Restaurante> restaurantes = restauranteService.buscarPorNombrePlatoTipico(nombrePlato);

        if (restaurantes.isEmpty()) {
            log.info("→ No se encontraron restaurantes para el plato '{}'", nombrePlato);
            return ResponseEntity.noContent().build();
        }

        log.info("→ {} restaurantes encontrados para '{}'", restaurantes.size(), nombrePlato);
        return ResponseEntity.ok(restaurantes);
    }

    @PostMapping
    public ResponseEntity<Restaurante> crear(@RequestBody Restaurante restaurante) {
        return ResponseEntity.ok(restauranteService.guardar(restaurante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> actualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        return ResponseEntity.ok(restauranteService.actualizar(id, restaurante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        restauranteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top")
    public ResponseEntity<?> obtenerTopRestaurantes(@RequestParam String platoTipico) {

        log.info("obtenerTopRestaurantes - platoTipico: {}", platoTipico);

        // Restaurantes
        List<Restaurante> listaTopRestaurantes = restauranteService.obtenerTopRestaurantes(platoTipico);

        // Plato típico
        List<PlatoTipico> platosTipicosBD = platoTipicoService.buscarPorNombreODescripcion(platoTipico);
        if(platosTipicosBD.isEmpty()){
            log.info("obtenerTopRestaurantes → Lista vacía de platos típicos");
        } else {
            log.info("obtenerTopRestaurantes - platosTipicosBD ({} elementos):", platosTipicosBD.size());
            platosTipicosBD.forEach(plato -> log.info(" → {}", plato));
        }

        for(Restaurante restaurante:listaTopRestaurantes){

            log.info("obtenerTopRestaurantes - restaurante: {}", restaurante.toString());

            Optional<Restaurante> optRestauranteBD = restauranteService.obtenerPorReference(restaurante.getReference());
            if(optRestauranteBD.isEmpty()) { // Crear restaurante

                log.info("obtenerTopRestaurantes → Crear restaurante");
                restaurante.setPlatosTipicos(platosTipicosBD);
                restauranteService.guardar(restaurante);
            }
            else { // actualizar platosTipicos del restaurante

                log.info("obtenerTopRestaurantes → actualizar platosTipicos del restaurante");
                // Restaurante
                Restaurante restauranteResult = optRestauranteBD.get();

                // Plato típico
                List<PlatoTipico> platosTipicosRestaurante =
                        restauranteResult.getPlatosTipicos() != null
                                ? restauranteResult.getPlatosTipicos() : new ArrayList<>();
                // Añade el nuevo plato tipico
                platosTipicosRestaurante.addAll(platosTipicosBD);

                restauranteResult.setPlatosTipicos( platosTipicosBD);
                restauranteService.actualizar(restauranteResult.getId_restaurante(), restauranteResult);
            }
        }

        return ResponseEntity.ok(listaTopRestaurantes);
    }
}
