package Entity;

import java.util.List;


public class Libro {

    private String id;
    private String titulo;
    private String autor;

    // Constructors, getters y setters

    public Libro() { }

    public Libro(String idLibro, String titulo, String autor) {
        this.id = idLibro;
        this.titulo  = titulo;
        this.autor   = autor;
    }

    public String getIdLibro() {
        return id;
    }
    public void setIdLibro(String idLibro) {
        this.id = idLibro;
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
