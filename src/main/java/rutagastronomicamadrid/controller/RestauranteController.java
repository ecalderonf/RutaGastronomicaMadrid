package rutagastronomicamadrid.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rutagastronomicamadrid.dto.RestauranteAltaDTO;
import rutagastronomicamadrid.model.PlatoTipico;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.service.PlatoTipicoService;
import rutagastronomicamadrid.service.RestauranteService;

import java.util.*;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://gastromadrid.pickmyskills.com"
})
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
        List<Restaurante> restaurantes = restauranteService.obtenerPorNombrePlatoTipico(nombrePlato);

        if (restaurantes.isEmpty()) {
            log.info("→ No se encontraron restaurantes para el plato '{}'", nombrePlato);
            return ResponseEntity.noContent().build();
        }

        log.info("→ {} restaurantes encontrados para '{}'", restaurantes.size(), nombrePlato);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Restaurante>> obtenerTop10PorRating() {
        List<Restaurante> top10 = restauranteService.obtenerTop10PorRating();

        if (top10.isEmpty()) {
            log.info("obtenerTop10PorRating → No se encontraron restaurantes con rating");
            return ResponseEntity.noContent().build();
        }

        log.info("obtenerTop10PorRating → {} restaurantes encontrados", top10.size());
        return ResponseEntity.ok(top10);
    }

    @PostMapping
    public ResponseEntity<Restaurante> crear(@Valid @RequestBody RestauranteAltaDTO restauranteAltaDTO) {

        // Restaurantes
        List<Restaurante> restauranteList = restauranteService.obtenerRestaurantesPorNombre(restauranteAltaDTO);
        log.info(" - restauranteList ({} elementos):", restauranteList.size());

        if(restauranteList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(restauranteService.guardar(restauranteList.get(0)));
        }
    }

    @PostMapping("/carga-por-plato")
    public ResponseEntity<?> setRestaurantes(@RequestParam String platoTipico) {

        log.info(" → platoTipico: {}", platoTipico);

        // Restaurantes
        List<Restaurante> restauranteList = restauranteService.obtenerRestaurantesPorPlato(platoTipico);
        log.info(" - restauranteList ({} elementos):", restauranteList.size());

        // Plato típico
        List<PlatoTipico> platosTipicosBD = platoTipicoService.buscarPorNombre(platoTipico);
        if(platosTipicosBD.isEmpty()){
            log.info(" → platosTipicosBD (Lista vacía)");
        } else {
            log.info(" → platosTipicosBD ({} elementos):", platosTipicosBD.size());
            platosTipicosBD.forEach(plato -> log.info(" → platosTipicosBD {}", plato));
        }

        for(Restaurante restaurante:restauranteList){

            Optional<Restaurante> optRestauranteBD = restauranteService.obtenerPorReference(restaurante.getReference());
            if(optRestauranteBD.isEmpty()) { // Crear restaurante

                log.info(" → CREAR restaurante");
                restaurante.setPlatosTipicos(platosTipicosBD);
                restauranteService.guardar(restaurante);
            }
            else { // actualizar platosTipicos del restaurante

                log.info(" → ACTUALIZAR platosTipicos del restaurante");

                Restaurante restauranteResult = optRestauranteBD.get();
                log.info(" - restauranteResult: (ref: {}, nombre: {})", restauranteResult.getReference(), restauranteResult.getNombre());

                // Plato típico
                List<PlatoTipico> platosTipicosRestaurante =
                        restauranteResult.getPlatosTipicos() != null
                                ? restauranteResult.getPlatosTipicos() : new ArrayList<>();
                log.info(" → platosTipicosRestaurante ini: ({} elementos):", platosTipicosRestaurante.size());
                // Añade el nuevo plato tipico
                platosTipicosRestaurante.addAll(platosTipicosBD);

                log.info(" → platosTipicosRestaurante fin: ({} elementos):", platosTipicosRestaurante.size());

                restauranteResult.setPlatosTipicos( platosTipicosRestaurante);
                restauranteService.guardar(restauranteResult);
                log.info(" → restaurante {} - platosTipicos: ({} elementos):", restauranteResult.getReference(), restauranteResult.getPlatosTipicos().size());

            }
        }

        return ResponseEntity.ok(restauranteList);
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

}
