package rutagastronomicamadrid.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rutagastronomicamadrid.controller.RestauranteController;
import rutagastronomicamadrid.model.PlatoTipico;
import rutagastronomicamadrid.repository.PlatoTipicoRepository;

import java.util.List;

@Service
public class PlatoTipicoService {

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);
    private final PlatoTipicoRepository platoTipicoRepository;

    @Autowired
    public PlatoTipicoService(PlatoTipicoRepository ptRepository) {
        this.platoTipicoRepository = ptRepository;
    }

    // Crear o actualizar un plato típico
    public PlatoTipico guardar(PlatoTipico pt) { return platoTipicoRepository.save(pt); }

    // Obtenertodos los platos típicos
    public List<PlatoTipico> buscarPlatosTipicos(){ return platoTipicoRepository.findAll(); }

    // Buscar plato típico por id
    public PlatoTipico buscarPorId(Long id) {
        return platoTipicoRepository.findById(id)
                .orElse(null);
    }

    // Eliminar plato típico por id
    public void eliminar(Long id) {
        if (!platoTipicoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Plato típico no encontrado con id: " + id);
        }
        platoTipicoRepository.deleteById(id);
    }

    public List<PlatoTipico> buscarPorNombre(String texto) {
        log.info("buscarPorNombre - texto: {}", texto);
        return platoTipicoRepository.findByNombreContainingIgnoreCase(texto);
    }

    public List<PlatoTipico> obtenerTop10PlatosPorRestaurantes() {
        return platoTipicoRepository.findTop10PlatosPorRestaurantes();
    }


}
