package rutagastronomicamadrid.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class PlatoTipico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plato;

    @NotNull
    private String nombre;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoriaPlato categoria;

    private String descripcion;

    private String ingredientes;

    private String receta;

    private String foto;

    public PlatoTipico(String nombre, CategoriaPlato categoria, String descripcion, String ingredientes, String receta, String foto) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.receta = receta;
        this.foto = foto;
    }

    public PlatoTipico() {
    }

    public Long getId_plato() {
        return id_plato;
    }

    public void setId_plato(Long id_plato) {
        this.id_plato = id_plato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaPlato  getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPlato  categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "PlatoTipico{" +
                "id_plato=" + id_plato +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", descripcion='" + descripcion + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", receta='" + receta + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
