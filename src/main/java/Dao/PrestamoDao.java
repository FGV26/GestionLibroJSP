package Dao;

import Conexion.Conexion;
import Entity.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDao implements IPrestamoDao {

    private static final String addSocio = "Insert INTO prestamo (id_prestamo, id_libro, id_socio, fecha_prestamo, fecha_devolucion, fecha_retorno) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String allPrestamos= "Select * FROM prestamo";

    private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean addPrestamo(Prestamo prestamo) throws SQLException {
        Boolean isAdded = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(addSocio);
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
                // Assuming Libro and Socio have methods to find by ID
                //prestamo.setLibro(new LibroDao().findById(rs.getString("id_libro")));
                //prestamo.setSocio(new SocioDao().findById(rs.getString("id_socio")));
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
}
