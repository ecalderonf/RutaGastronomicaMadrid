package rutagastronomicamadrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rutagastronomicamadrid.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByReference(String reference);

    @Query("SELECT r FROM Restaurante r JOIN r.platosTipicos p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombrePlato, '%'))")
    List<Restaurante> findByNombrePlatoTipico(@Param("nombrePlato") String nombrePlato);
}
