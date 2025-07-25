package Dto;

import Entity.Libro;

public class LibroDTO {

    private String id;
    private String title;
    private String author;

    public LibroDTO(Libro libro) {
        this.id = libro.getIdLibro();
        this.title = libro.getTitulo();
        this.author = libro.getAutor();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

