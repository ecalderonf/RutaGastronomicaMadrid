package rutagastronomicamadrid.dto;

import jakarta.validation.constraints.*;
import rutagastronomicamadrid.model.Rol;
import rutagastronomicamadrid.model.Usuario;

public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private Rol rol;

    //info basica del restaurante
    private Long restauranteId;
    private String restauranteNombre;


    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();

        this.rol=usuario.getRol();

        if(usuario.getRestaurante() != null){
            this.restauranteId = usuario.getRestaurante().getId_restaurante();
            this.restauranteNombre = usuario.getRestaurante().getNombre();
        }

        this.password = usuario.getPassword(); // ⚠️ No recomendado en respuestas. Mejor omitir.
    }

    public UsuarioDTO(Long id, String nombre, String password, Rol rol, Long restauranteId, String restauranteNombre) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.restauranteId = restauranteId;
        this.restauranteNombre = restauranteNombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getRestauranteNombre() {
        return restauranteNombre;
    }

    public void setRestauranteNombre(String restauranteNombre) {
        this.restauranteNombre = restauranteNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
