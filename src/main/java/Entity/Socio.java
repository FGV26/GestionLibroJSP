
package Entity;

import java.util.List;


public class Socio {

    private String idSocio;
    private String nombre;
    private String correo;
    private List<Prestamo> prestamos;

    public Socio() { }

    public Socio(String idSocio, String nombre, String correo) {
        this.idSocio = idSocio;
        this.nombre  = nombre;
        this.correo  = correo;
    }

    public String getIdSocio() {
        return idSocio;
    }
    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}