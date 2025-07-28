package Dao;

import Entity.Prestamo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IPrestamoDao {

    // Implementar metodos necesarios para la interfaz IPrestamoDao
    boolean addPrestamo(Prestamo prestamo) throws SQLException;
    List<Prestamo> allPrestamos() throws SQLException;
    String getLastId() throws SQLException;
}