package Controllers;

import Dao.LibroDAO;
import Entity.Libro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/controller/libro")
public class LibroController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    LibroDAO libroDAO = new LibroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        if (id <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del libro no válido.");
            return;
        }
        try {
            Libro lb = libroDAO.findById(id);
            request.setAttribute("libro", lb);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al acceder a la base de datos.");
            return;
        }

        //Quiero mandar el libro pero en varible para que ja lo obtenga el JSP

    }
}
