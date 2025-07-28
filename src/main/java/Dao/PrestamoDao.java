package Dao;

import Conexion.Conexion;
import Entity.Libro;
import Entity.Prestamo;
import Entity.Socio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDao implements IPrestamoDao {

    private final String addPrestamo = "INSERT INTO prestamo (id_prestamo, id_libro, id_socio, fecha_prestamo, fecha_devolucion, fecha_retorno) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String allPrestamos =
            "SELECT p.id_prestamo, l.id_libro, l.titulo, s.id_socio, s.nombre, " +
                    "p.fecha_prestamo, p.fecha_devolucion, p.fecha_retorno " +
                    "FROM prestamo p " +
                    "INNER JOIN libro l ON p.id_libro = l.id_libro " +
                    "INNER JOIN socio s ON p.id_socio = s.id_socio";

    private static final String GET_LAST_ID = "SELECT id_prestamo FROM prestamo ORDER BY id_prestamo DESC LIMIT 1";

    private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean addPrestamo(Prestamo prestamo) throws SQLException {
        Boolean isAdded = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(addPrestamo);
            ps.setString(1, prestamo.getIdPrestamo());
            ps.setString(2, prestamo.getLibro().getIdLibro());
            ps.setString(3, prestamo.getSocio().getIdSocio());
            ps.setObject(4, prestamo.getFechaPrestamo());
            ps.setObject(5, prestamo.getFechaDevolucion());
            ps.setObject(6, prestamo.getFechaRetorno());
            isAdded = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isAdded;
    }

    @Override
    public List<Prestamo> allPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(allPrestamos);
            rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getString("id_prestamo"));

                // Crear libro y socio desde los datos del join
                Libro libro = new Libro();
                libro.setIdLibro(rs.getString("id_libro"));
                libro.setTitulo(rs.getString("titulo"));

                Socio socio = new Socio();
                socio.setIdSocio(rs.getString("id_socio"));
                socio.setNombre(rs.getString("nombre"));

                prestamo.setLibro(libro);
                prestamo.setSocio(socio);

                prestamo.setFechaPrestamo(rs.getObject("fecha_prestamo", LocalDate.class));
                prestamo.setFechaDevolucion(rs.getObject("fecha_devolucion", LocalDate.class));
                prestamo.setFechaRetorno(rs.getObject("fecha_retorno", LocalDate.class));

                prestamos.add(prestamo);
            }

        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return prestamos;
    }

    @Override
    public String getLastId() throws SQLException {
        String lastId = null;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(GET_LAST_ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                lastId = rs.getString("id_prestamo");
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return lastId;
    }

}
