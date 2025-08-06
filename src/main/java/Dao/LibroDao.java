package Dao;

import Conexion.Conexion;
import Entity.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LibroDao implements ILibroDao {

    private static final String ADD_LIBRO = "INSERT INTO Libro (id_libro,titulo, autor) VALUES (?,?,?)";
    private static final String UPDATE_LIBRO = "UPDATE Libro SET titulo = ?, autor = ? WHERE id_libro = ?";
    private static final String DELETE_LIBRO = "DELETE FROM Libro WHERE id_libro = ?";
    private static final String FIND_LIBRO_BY_ID = "SELECT * FROM Libro WHERE id_libro = ?";
    private static final String GET_LAST_ID = "SELECT id_libro FROM libro ORDER BY id_libro DESC LIMIT 1";
    private static final String FIND_ALL_LIBROS = "SELECT * FROM Libro";


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
    public boolean updateLibro(Libro libro) throws SQLException {
        boolean IsUpdate = false;
        Libro existingLibro = findById(libro.getIdLibro());
        try {
            if (existingLibro != null) {
                con = cn.getConnection();
                ps = con.prepareStatement(UPDATE_LIBRO);
                ps.setString(1, libro.getTitulo());
                ps.setString(2, libro.getAutor());
                ps.setString(3, libro.getIdLibro());
                IsUpdate = ps.executeUpdate() > 0;
            } else {
                System.out.println("Libro no encontrado para actualizar.");
            }
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return IsUpdate;
    }

    @Override
    public boolean deleteLibro(String idLibro) throws SQLException {
        boolean isDeleted = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(DELETE_LIBRO);
            ps.setString(1, idLibro);
            isDeleted = ps.executeUpdate() > 0;
        } finally {
            cn.close(ps);
            cn.close(con);
        }
        return isDeleted;
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

    @Override
    public List<Libro> findAll() throws SQLException {
        List<Libro> libros = new java.util.ArrayList<>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(FIND_ALL_LIBROS);
            rs = ps.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIdLibro(rs.getString("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setFechaCreacion(rs.getTimestamp("created_at"));
                libros.add(libro);
            }
        } finally {
            cn.close(rs);
            cn.close(ps);
            cn.close(con);
        }
        return libros;
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
