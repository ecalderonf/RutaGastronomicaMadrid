package rutagastronomicamadrid.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rutagastronomicamadrid.config.JwtUtil;
import rutagastronomicamadrid.dto.LoginDTO;
import rutagastronomicamadrid.dto.UsuarioDTO;
import rutagastronomicamadrid.model.Usuario;
import rutagastronomicamadrid.repository.UsuarioRepository;
import rutagastronomicamadrid.service.UsuarioService;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://gastromadrid.pickmyskills.com"
})
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.update(id, dto);
    }


    @GetMapping
    public List<UsuarioDTO> getAll () {
        return usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public UsuarioDTO getById(@PathVariable Long id) {
        return usuarioService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO post(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.post(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id ) {
        usuarioService.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        return usuarioService.login(dto);
    }

    @GetMapping("/current")
    public ResponseEntity<UsuarioDTO> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        // Extraer el token del header
        String token = authorizationHeader.substring(7); // Remover "Bearer " del inicio

        if (token == null || !jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }

        // Obtener el nombre del usuario desde el token
        String username = jwtUtil.extractUsername(token);

        // Buscar el usuario en la base de datos usando el servicio
        Usuario usuario = usuarioService.getByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(new UsuarioDTO(usuario));
    }

}
