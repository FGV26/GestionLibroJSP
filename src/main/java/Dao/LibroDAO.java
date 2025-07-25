package Dao;

import Conexion.Conexion;
import Entity.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDAO {
    private static final String ADD_LIBRO = "INSERT INTO Libro (titulo, autor) VALUES (?, ?)";
    private static final String FIND_LIBRO_BY_ID = "SELECT * FROM Libro WHERE id_libro = ?";

    private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean addLibro(Libro libro) throws SQLException {
        boolean isAdded = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(ADD_LIBRO);
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            isAdded = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isAdded;
    }

    public Libro findById(int idLibro) throws SQLException {
        Libro libro = null;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(FIND_LIBRO_BY_ID);
            ps.setInt(1, idLibro);
            rs = ps.executeQuery();
            if (rs.next()) {
                libro = new Libro();
                libro.setIdLibro(rs.getString("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return libro;
    }
}
