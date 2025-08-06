package Dao;

import Conexion.Conexion;
import Entity.Libro;
import Entity.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SocioDao implements ISocioDao{

    private static final String addSocio = "INSERT INTO socio (id_socio, nombre, correo) VALUES (?, ?, ?)";
    private static final String updateSocio = "UPDATE socio SET nombre = ?, correo = ? WHERE id_socio = ?";
    private static final String removeSocio= "DELETE FROM socio WHERE id_socio = ?";
    private static final String findSocioById = "SELECT * FROM socio WHERE id_socio = ?";
    private static final String GET_LAST_ID = "SELECT id_socio FROM socio ORDER BY id_socio DESC LIMIT 1";
    private static final String FIND_ALL_SOCIOS = "SELECT * FROM socio";

    private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean addSocio(Socio socio) throws SQLException {
        boolean isAdded = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(addSocio);
            ps.setString(1, socio.getIdSocio());
            ps.setString(2, socio.getNombre());
            ps.setString(3, socio.getCorreo());
            isAdded = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isAdded;
    }

    @Override
    public boolean updateSocio(Socio socio) throws SQLException {
        boolean isUpdated = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(updateSocio);
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getCorreo());
            ps.setString(3, socio.getIdSocio());
            isUpdated = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isUpdated;
    }

    @Override
    public boolean removeSocio(String idSocio) throws SQLException {
        boolean isAdded = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(removeSocio);
            ps.setString(1, idSocio);
            isAdded = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isAdded;
    }

    @Override
    public Socio findById(String idSocio) throws SQLException {
        Socio socio = null;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(findSocioById);
            ps.setString(1, idSocio);
            rs = ps.executeQuery();
            if (rs.next()) {
                socio = new Socio();
                socio.setIdSocio(rs.getString("id_socio"));
                socio.setNombre(rs.getString("nombre"));
                socio.setCorreo(rs.getString("correo"));
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return socio;
    }

    @Override
    public List<Socio> findAll() throws SQLException {
        List<Socio> socios = new java.util.ArrayList<>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(FIND_ALL_SOCIOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setIdSocio(rs.getString("id_socio"));
                socio.setNombre(rs.getString("nombre"));
                socio.setCorreo(rs.getString("correo"));
                socios.add(socio);
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return socios;
    }

    @Override
    public String getLastId() throws SQLException {
        String lastId = null;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(GET_LAST_ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                lastId = rs.getString("id_socio");
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return lastId;
    }
}
