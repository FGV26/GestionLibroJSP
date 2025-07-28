package Dto;

import Entity.Libro;
import Entity.Socio;

import java.time.LocalDate;

public class PrestamoDTO {

    private String idPrestamo;
    private Libro libro;
    private Socio socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaRetorno;

    // Constructors, getters y setters


    public PrestamoDTO(PrestamoDTO prestamoDTO) {

        this.idPrestamo = prestamoDTO.getIdPrestamo();
        this.libro = prestamoDTO.getLibro();
        this.socio = prestamoDTO.getSocio();
        this.fechaPrestamo = prestamoDTO.getFechaPrestamo();
        this.fechaDevolucion = prestamoDTO.getFechaDevolucion();
        this.fechaRetorno = prestamoDTO.getFechaRetorno();
    }

    public PrestamoDTO() {
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }
    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Socio getSocio() {
        return socio;
    }
    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public LocalDate getFechaRetorno() {
        return fechaRetorno;
    }
    public void setFechaRetorno(LocalDate fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

}