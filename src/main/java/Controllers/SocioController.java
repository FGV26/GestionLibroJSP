package Controllers;

import Dao.SocioDao;
import Dto.SocioDTO;
import Entity.Socio;
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

@WebServlet("/api/socios")
public class SocioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SocioDao socioDAO = new SocioDao();
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
                eliminarSocio(request, response);
                break;
            case "ListarTodo":
                listarSocios(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
            return;
        }

        switch (action) {
            case "agregar":
                agregarSocio(request, response);
                break;
            case "update":
                actualizarSocio(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    private void agregarSocio(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");

        if (nombre == null || correo == null || nombre.isEmpty() || correo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos");
            return;
        }

        Socio socio = new Socio();
        try {
            socio.setIdSocio(new IncrementID().generateNewId(EntityType.SOCIO));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando ID");
            return;
        }

        socio.setNombre(nombre);
        socio.setCorreo(correo);

        try {
            socioDAO.addSocio(socio);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new SocioDTO(socio)));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar socio");
        }
    }

    private void actualizarSocio(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");

        if (id == null || nombre == null || correo == null || nombre.isEmpty() || correo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos");
            return;
        }

        Socio socio = new Socio();
        socio.setIdSocio(id);
        socio.setNombre(nombre);
        socio.setCorreo(correo);

        try {
            boolean actualizado = socioDAO.updateSocio(socio);
            if (actualizado) {
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(new SocioDTO(socio)));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Socio no encontrado");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar socio");
        }
    }

    private void eliminarSocio(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no especificado");
            return;
        }

        try {
            Socio socio = socioDAO.findById(id);
            if (socio == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Socio no encontrado");
                return;
            }

            boolean eliminado = socioDAO.removeSocio(id);
            if (eliminado) {
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(new SocioDTO(socio)));
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo eliminar el socio");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar socio");
        }
    }

    private void listarSocios(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            List<Socio> socios = socioDAO.findAll();
            List<SocioDTO> sociosDTO = new ArrayList<>();
            for (Socio socio : socios) {
                sociosDTO.add(new SocioDTO(socio));
            }
            out.print(gson.toJson(sociosDTO));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener socios");
        }
    }
}
