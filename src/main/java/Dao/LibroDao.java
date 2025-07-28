package Dao;

import Conexion.Conexion;
import Entity.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDao implements ILibroDao {

    private static final String ADD_LIBRO = "INSERT INTO Libro (id_libro,titulo, autor) VALUES (?,?,?)";
    private static final String FIND_LIBRO_BY_ID = "SELECT * FROM Libro WHERE id_libro = ?";
    private static final String GET_LAST_ID = "SELECT id_libro FROM libro ORDER BY id_libro DESC LIMIT 1";

    private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean addLibro(Libro libro) throws SQLException {
        boolean isAdded = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(ADD_LIBRO);
            ps.setString(1, libro.getIdLibro());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            isAdded = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isAdded;
    }

    @Override
    public Libro findById(String idLibro) throws SQLException {
        Libro libro = null;
        System.out.println("Buscando libro con ID: " + idLibro);
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(FIND_LIBRO_BY_ID); // "SELECT * FROM libro WHERE id_libro = ?"
            ps.setString(1, idLibro);  // ahora usamos String
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

    public String getLastId() throws SQLException {
        String lastId = null;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(GET_LAST_ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                lastId = rs.getString("id_libro");
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return lastId;
    }

}
