package Dao;

import Conexion.Conexion;
import Entity.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SocioDao implements ISocioDao{

    private static final String addSocio = "INSERT INTO socio (id_socio, nombre, correo) VALUES (?, ?, ?)";
    private static final String removeSocio= "DELETE FROM socio WHERE id_socio = ?";
    private static final String findSocioById = "SELECT * FROM socio WHERE id_socio = ?";

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
}
