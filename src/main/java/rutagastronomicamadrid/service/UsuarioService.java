package rutagastronomicamadrid.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rutagastronomicamadrid.config.JwtUtil;
import rutagastronomicamadrid.dto.LoginDTO;
import rutagastronomicamadrid.dto.UsuarioDTO;
import rutagastronomicamadrid.model.Restaurante;
import rutagastronomicamadrid.model.Rol;
import rutagastronomicamadrid.model.Usuario;
import rutagastronomicamadrid.repository.RestauranteRepository;
import rutagastronomicamadrid.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioService {



    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;


    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.restauranteRepository = restauranteRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO post(UsuarioDTO dto) {

        System.out.println("Recibiendo DTO para guardar: " + dto.getNombre());

        String passwordplano = dto.getPassword();

        Restaurante restaurante = null;
        if (dto.getRestauranteId() != null) {
            restaurante = restauranteRepository.findById(dto.getRestauranteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado"));
        }

        var rolUsuario = dto.getRol() !=null ? dto.getRol() : Rol.PROPIETARIO;

        Usuario usuario = new Usuario (
                null,
                dto.getNombre(),
                restaurante,
                rolUsuario,
                passwordEncoder.encode(passwordplano)

        );
        Usuario guardado = usuarioRepository.save(usuario);
        System.out.println("Usuario guardado: " + guardado.getId());
        return new UsuarioDTO(guardado);
    }

    public ResponseEntity<String> login (LoginDTO dto){
        System.out.println("Buscando usuario: " + dto.getNombreUsuario());
        Usuario usuario  = usuarioRepository.findByNombre(dto.getNombreUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));
        System.out.println("Usuario encontrado: " + usuario.getNombre());

        // Validar contraseña
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }

        //generemos el token
        String token = jwtUtil.generateToken(usuario.getNombre());

        System.out.println("Login exitoso");
        return ResponseEntity.ok(token);
    }

    public void delete (Long id ) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public List<UsuarioDTO> getAll(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    public UsuarioDTO getById (Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario no encontrado"));

        return new UsuarioDTO(usuario);
    }
}
