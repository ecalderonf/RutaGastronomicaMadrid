package rutagastronomicamadrid.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rutagastronomicamadrid.model.PlatoTipico;
import rutagastronomicamadrid.service.PlatoTipicoService;

import java.util.List;

@RestController
@RequestMapping("/api/platosTipicos")
public class PlatoTipicoController {

    private final PlatoTipicoService platosTipicosService;

    @Autowired
    public PlatoTipicoController(PlatoTipicoService ptService){
        this.platosTipicosService = ptService;
    }

    // Obtener todos los platos típicos
    @GetMapping("/")
    List<PlatoTipico> buscarPlatosTipicos(){ return platosTipicosService.buscarPlatosTipicos(); }

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
            List<PlatoTipico> platoTipicoResult = platosTipicosService.buscarPorNombreODescripcion(texto);
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

    @DeleteMapping("/")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        platosTipicosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
