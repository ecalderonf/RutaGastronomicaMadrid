package rutagastronomicamadrid.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_restaurante;

    @NotNull
    private String nombre;

    private String domicilio;

    private Integer pricelevel;

    private Double rating;

    private Double latitude;

    private Double longitude;

    @Column(length = 1000)
    private String photoreference;

    public Restaurante() {
    }

    public Restaurante(String nombre, String domicilio, Integer pricelevel, Double rating, Double latitude, Double longitude, String photoreference) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.pricelevel = pricelevel;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoreference = photoreference;
    }

    // getter y setter


    public Long getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(Long id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getPricelevel() {
        return pricelevel;
    }

    public void setPricelevel(Integer pricelevel) {
        this.pricelevel = pricelevel;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhotoreference() {
        return photoreference;
    }

    public void setPhotoreference(String photoreference) {
        this.photoreference = photoreference;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "id_restaurante=" + id_restaurante +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", pricelevel=" + pricelevel +
                ", rating=" + rating +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", photoreference='" + photoreference + '\'' +
                '}';
    }
}
