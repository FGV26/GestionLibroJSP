package Dto;

import Entity.Prestamo;

import java.time.LocalDate;

public class PrestamoDTO {
    private String idPrestamo;
    private String idLibro;
    private String tituloLibro;
    private String idSocio;
    private String nombreSocio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaRetorno;

    public PrestamoDTO() {
        // Constructor vacío para permitir la deserialización
    }

    public PrestamoDTO(Prestamo prestamo) {
        this.idPrestamo = prestamo.getIdPrestamo();
        System.out.println("DTO: idPrestamo = " + this.idPrestamo);

        if (prestamo.getLibro() != null) {
            this.idLibro = prestamo.getLibro().getIdLibro();
            this.tituloLibro = prestamo.getLibro().getTitulo();
            System.out.println("DTO: Libro OK = " + this.idLibro);
        } else {
            System.out.println("DTO: Libro es NULL");
        }

        if (prestamo.getSocio() != null) {
            this.idSocio = prestamo.getSocio().getIdSocio();
            this.nombreSocio = prestamo.getSocio().getNombre();
            System.out.println("DTO: Socio OK = " + this.idSocio);
        } else {
            System.out.println("DTO: Socio es NULL");
        }

        this.fechaPrestamo = prestamo.getFechaPrestamo();
        this.fechaDevolucion = prestamo.getFechaDevolucion();
        this.fechaRetorno = prestamo.getFechaRetorno();
    }


    // Getters y setters
    public String getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(String idPrestamo) { this.idPrestamo = idPrestamo; }

    public String getIdLibro() { return idLibro; }
    public void setIdLibro(String idLibro) { this.idLibro = idLibro; }

    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

    public String getIdSocio() { return idSocio; }
    public void setIdSocio(String idSocio) { this.idSocio = idSocio; }

    public String getNombreSocio() { return nombreSocio; }
    public void setNombreSocio(String nombreSocio) { this.nombreSocio = nombreSocio; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public LocalDate getFechaRetorno() { return fechaRetorno; }
    public void setFechaRetorno(LocalDate fechaRetorno) { this.fechaRetorno = fechaRetorno; }
}
