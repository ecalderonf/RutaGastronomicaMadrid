package rutagastronomicamadrid.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.service.RestauranteService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
//@CrossOrigin()
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
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
        List<Restaurante> listaTopRestaurantes = restauranteService.obtenerTopRestaurantes(platoTipico);
        for(Restaurante restauranteResult:listaTopRestaurantes){
            restauranteService.guardar(restauranteResult);
        }
        return ResponseEntity.ok(listaTopRestaurantes);
    }
}
