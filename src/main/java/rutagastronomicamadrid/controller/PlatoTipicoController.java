package rutagastronomicamadrid.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rutagastronomicamadrid.model.PlatoTipico;
import rutagastronomicamadrid.service.PlatoTipicoService;

import java.util.List;

@RestController
@RequestMapping("/api/platosTipicos")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://gastromadrid.pickmyskills.com"
})
public class PlatoTipicoController {

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);
    private final PlatoTipicoService platosTipicosService;

    @Autowired
    public PlatoTipicoController(PlatoTipicoService ptService){
        this.platosTipicosService = ptService;
    }

    // Obtener todos los platos típicos
    @GetMapping("")
    List<PlatoTipico> buscarPlatosTipicos(){ return platosTipicosService.buscarPlatosTipicos(); }

    // Obtener platos top
    @GetMapping("/top10")
    public ResponseEntity<List<PlatoTipico>> obtenerTop10Platos() {
        List<PlatoTipico> topPlatos = platosTipicosService.obtenerTop10PlatosPorRestaurantes();
        log.info(" → Top 10 platos típicos ({} elementos)", topPlatos.size());
        topPlatos.forEach(p -> log.info(" → [{}] {}", p.getId_plato(), p.getNombre()));
        return ResponseEntity.ok(topPlatos);
    }

    // Obtener plato tipico por Id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            PlatoTipico platoTipicoResult = platosTipicosService.buscarPorId(id);
            return ResponseEntity.ok(platoTipicoResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();

    }

    // Obtener platos tipicos por nombre o descripcion
    @GetMapping("/nombre/{texto}")
    public ResponseEntity<?> buscarPorNombreODescripcion(@PathVariable String texto) {
        try {
            List<PlatoTipico> platoTipicoResult = platosTipicosService.buscarPorNombre(texto);
            return ResponseEntity.ok(platoTipicoResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();

    }

    // Crear o actualizar plato tipico
    @PostMapping("/")
    public ResponseEntity<PlatoTipico> guardar(@Valid @RequestBody PlatoTipico platoTipico){
        PlatoTipico platoTipicoResult = platosTipicosService.guardar(platoTipico);
        return ResponseEntity.ok(platoTipicoResult);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        platosTipicosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
