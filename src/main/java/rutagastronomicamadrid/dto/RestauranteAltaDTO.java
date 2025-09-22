package rutagastronomicamadrid.dto;

public class RestauranteAltaDTO {

    private String nombre;
    private String codigoPostal;

    public RestauranteAltaDTO() {
    }

    public RestauranteAltaDTO(String nombre, String codigoPostal) {
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return "RestauranteAltaDTO{" +
                "nombre='" + nombre + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                '}';
    }
}
