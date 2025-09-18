package rutagastronomicamadrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rutagastronomicamadrid.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
