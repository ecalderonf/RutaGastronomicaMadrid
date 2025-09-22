package rutagastronomicamadrid.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToOne
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id_restaurante")
    private Restaurante restaurante;

    @Enumerated(EnumType.STRING)
    private Rol rol;


    private String password;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, Restaurante restaurante, Rol rol, String password) {
        this.id = id;
        this.nombre = nombre;
        this.restaurante = restaurante;
        this.rol = rol;
        this.password = password;
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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
