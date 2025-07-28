package Dto;

import Entity.Prestamo;
import Entity.Socio;

import java.util.List;

public class SocioDTO {
    //Data transport Object, solo transportara la informacionn que vera el usuario

    private String idSocio;
    private String nombre;
    private String correo;

    public SocioDTO(Socio socio) {
        this.idSocio = socio.getIdSocio();
        this.nombre = socio.getNombre();
        this.correo = socio.getCorreo();
    }

    public SocioDTO() {
    }

    // --- Getters y Setters ---
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
