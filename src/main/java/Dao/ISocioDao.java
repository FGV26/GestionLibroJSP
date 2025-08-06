package Dao;

import Entity.Libro;
import Entity.Socio;

import java.sql.SQLException;
import java.util.List;

public interface ISocioDao {

    //una interfaz es un contrato que las demas clases deben seguir
    boolean addSocio(Socio socio) throws SQLException;
    boolean updateSocio(Socio socio) throws SQLException;
    boolean removeSocio(String idSocio) throws SQLException;
    Socio findById(String idSocio) throws SQLException;
    List<Socio> findAll() throws SQLException;
    String getLastId() throws SQLException;
}
