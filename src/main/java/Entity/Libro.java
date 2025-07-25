package Entity;

import java.util.List;


public class Libro {

    private String idLibro;
    private String titulo;
    private String autor;

    // Constructors, getters y setters

    public Libro() { }

    public Libro(String idLibro, String titulo, String autor) {
        this.idLibro = idLibro;
        this.titulo  = titulo;
        this.autor   = autor;
    }

    public String getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

}
