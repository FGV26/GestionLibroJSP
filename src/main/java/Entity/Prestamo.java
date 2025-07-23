package Entity;

import java.time.LocalDate;

public class Prestamo {

    private String idPrestamo;
    private Libro libro;
    private Socio socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaRetorno;

    // Constructors, getters y setters

    public Prestamo() { }

    public Prestamo(String idPrestamo, Libro libro, Socio socio,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.idPrestamo     = idPrestamo;
        this.libro          = libro;
        this.socio          = socio;
        this.fechaPrestamo  = fechaPrestamo;
        this.fechaDevolucion= fechaDevolucion;
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