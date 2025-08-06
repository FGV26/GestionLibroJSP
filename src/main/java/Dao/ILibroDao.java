package Dao;

import Entity.Libro;
import java.sql.SQLException;
import java.util.List;

interface ILibroDao {
    //Implementar metodos necesarios para la interfaz ILibroDao
    boolean addLibro(Libro libro) throws SQLException;
    boolean updateLibro(Libro libro) throws SQLException;
    boolean deleteLibro(String idLibro) throws SQLException;
    Libro findById(String idLibro) throws SQLException;
    List<Libro> findAll() throws SQLException;
    String getLastId() throws SQLException;
}
