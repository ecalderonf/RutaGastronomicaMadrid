package rutagastronomicamadrid.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rutagastronomicamadrid.dto.LoginDTO;
import rutagastronomicamadrid.dto.UsuarioDTO;
import rutagastronomicamadrid.model.Usuario;
import rutagastronomicamadrid.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
       // this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO post(UsuarioDTO dto) {
        System.out.println("Recibiendo DTO para guardar: " + dto.getNombre());
        String passwordplano = dto.getPassword();
        Usuario usuario = new Usuario (
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getPassword()
               //passwordEncoder.encode(passwordplano)
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

        System.out.println("Login exitoso");
        return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
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
