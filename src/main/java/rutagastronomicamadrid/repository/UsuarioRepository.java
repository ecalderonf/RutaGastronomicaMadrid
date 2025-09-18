package rutagastronomicamadrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rutagastronomicamadrid.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);
}
