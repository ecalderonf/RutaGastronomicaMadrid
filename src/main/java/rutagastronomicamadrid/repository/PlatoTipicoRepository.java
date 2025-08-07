package rutagastronomicamadrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rutagastronomicamadrid.model.PlatoTipico;

import java.util.List;

public interface PlatoTipicoRepository extends JpaRepository<PlatoTipico, Long> {
    List<PlatoTipico> findByNombreOrDescripcionContainingIgnoreCase(String nombre, String descripcion);
}
