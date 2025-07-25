package Dao;

import Entity.Socio;

import java.sql.SQLException;

public interface ISocioDao {

    boolean addSocio(Socio socio) throws SQLException;
    boolean removeSocio(String idSocio) throws SQLException;
    Socio findById(String idSocio) throws SQLException;
}
