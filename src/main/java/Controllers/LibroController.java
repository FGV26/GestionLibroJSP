package Controllers;

import Dao.LibroDao;
import Dto.LibroDTO;
import Entity.Libro;
import Services.IncrementID;
import com.google.gson.Gson;
import util.EntityType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/libros")
public class LibroController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LibroDao libroDAO = new LibroDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
            return;
        }

        switch (action) {
            case "delete":
                eliminarLibro(request, response);
                break;
            case "ListarTodo":
                listarLibros(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("action");
        if (accion == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
            return;
        }
        switch (accion){
            case "agregar":
                agregarLibro(request, response);
                break;
            case "update":
                actualizarLibro(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    private void agregarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        IncrementID incrementID = new IncrementID();

        if (titulo == null || autor == null || titulo.isEmpty() || autor.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos");
            return;
        }

        Libro libro = new Libro();
        try {
            libro.setIdLibro(incrementID.generateNewId(EntityType.LIBRO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        libro.setTitulo(titulo);
        libro.setAutor(autor);

        try {
            libroDAO.addLibro(libro);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            LibroDTO libroDTO = new LibroDTO(libro);
            out.print(gson.toJson(libroDTO));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar libro");
        }
    }

    private void actualizarLibro(HttpServletRequest request, HttpServletResponse response)throws IOException {

        String idLibro =  request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        if (idLibro == null || autor == null || titulo.isEmpty() || autor.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos");
            return;
        }

        System.out.println("Actualizando libro con ID: " + idLibro + ", Título: " + titulo + ", Autor: " + autor);

        Libro libro = new Libro();
        libro.setIdLibro(idLibro);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        try {
            boolean isUpdated = libroDAO.updateLibro(libro);
            if (isUpdated) {
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                LibroDTO libroDTO = new LibroDTO(libro);
                out.print(gson.toJson(libroDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Libro no encontrado");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar libro");
        }
    }

    private void eliminarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if( id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de libro no especificado");
            return;
        }
        try {
            Libro buscar = libroDAO.findById(id);
            boolean isDelate = libroDAO.deleteLibro(id);
            if(isDelate){
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                LibroDTO libroDTO = new LibroDTO(buscar);
                out.print(gson.toJson(libroDTO));
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Libro no encontrado");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el libro");
        }
    }

    private void listarLibros(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        System.out.println("Listando todos los libros...");

        try (PrintWriter out = response.getWriter()) {
            List<Libro> libros = libroDAO.findAll();
            System.out.println("Total libros encontrados: " + libros.size());

            List<LibroDTO> librosDTO = new ArrayList<>();
            for (Libro libro : libros) {
                System.out.println("Libro ID: " + libro.getIdLibro() + ", Título: " + libro.getTitulo() + ", Autor: " + libro.getAutor());
                librosDTO.add(new LibroDTO(libro));
            }

            out.print(gson.toJson(librosDTO));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los libros");
            e.printStackTrace();
        }
    }



}

