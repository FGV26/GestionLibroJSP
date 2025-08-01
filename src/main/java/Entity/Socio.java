package Entity;

public class  Socio {

    private String idSocio;
    private String nombre;
    private String correo;

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
}