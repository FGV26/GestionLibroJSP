package Conexion;

import Dao.LibroDao;

import java.sql.Connection;
import java.sql.SQLException;

public class conection {
    public static void main(String[] args) {

        // Aquí puedes probar la conexión a la base de datos
        try {
            Connection conn = Conexion.getConnection();
            System.out.println("Conexión exitosa a la base de datos.");
            Conexion.close(conn);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        //Listar libros
        LibroDao libroDao = new LibroDao();
        try {
            libroDao.findAll().forEach(libro ->
                System.out.println("ID: " + libro.getIdLibro() + ", Título: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Fecha de Creación: " + libro.getFechaCreacion())
            );
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
    }
}
