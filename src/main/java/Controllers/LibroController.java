package Controllers;

import Dao.LibroDAO;
import Entity.Libro;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/libros")
public class LibroController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LibroDAO libroDAO = new LibroDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");

        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Libro libro = libroDAO.findById(id);
                if (libro != null) {
                    out.print(gson.toJson(libro));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Libro no encontrado");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'id' requerido");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        if (titulo == null || autor == null || titulo.isEmpty() || autor.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos");
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);

        try {
            libroDAO.addLibro(libro);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(libro));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar libro");
        }
    }
}

