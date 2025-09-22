package rutagastronomicamadrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rutagastronomicamadrid.model.PlatoTipico;

import java.util.List;

public interface PlatoTipicoRepository extends JpaRepository<PlatoTipico, Long> {
    List<PlatoTipico> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = """
    SELECT pt.*
    FROM plato_tipico pt
    JOIN (
        SELECT plato_tipico_id, COUNT(*) AS total
        FROM restaurante_plato_tipico
        GROUP BY plato_tipico_id
    ) ranking ON pt.id_plato = ranking.plato_tipico_id
    ORDER BY ranking.total DESC, pt.nombre ASC
    LIMIT 10
    """, nativeQuery = true)
    List<PlatoTipico> findTop10PlatosPorRestaurantes();
}
