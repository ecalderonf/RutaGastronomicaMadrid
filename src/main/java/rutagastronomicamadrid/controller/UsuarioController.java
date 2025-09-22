package rutagastronomicamadrid.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rutagastronomicamadrid.dto.LoginDTO;
import rutagastronomicamadrid.dto.UsuarioDTO;
import rutagastronomicamadrid.service.UsuarioService;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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


}
