package Dao;

import Entity.Libro;
import java.sql.SQLException;

interface ILibroDao {
    //Implementar metodos necesarios para la interfaz ILibroDao
    boolean addLibro(Libro libro) throws SQLException;
    Libro findById(int idLibro) throws SQLException;
}
