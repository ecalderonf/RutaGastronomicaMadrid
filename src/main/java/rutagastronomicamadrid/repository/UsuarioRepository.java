package rutagastronomicamadrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.model.Usuario;

import java.util.List;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRestaurante(Restaurante restaurante);


    Optional<Usuario> findByNombre(String nombre);
}
