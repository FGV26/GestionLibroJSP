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

@WebServlet("/api/socios")
public class SocioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SocioDao socioDAO = new SocioDao();
    private final Gson gson = new Gson();

    // GET para obtener un socio por ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");

        try {
            if (idParam != null && !idParam.isEmpty()) {
                Socio socio = socioDAO.findById(idParam);
                if (socio != null) {
                    SocioDTO socioDTO = new SocioDTO(socio);
                    out.print(gson.toJson(socioDTO));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Socio no encontrado");
                }
            } else {
                // Si no se proporciona ID en un GET, se asume que no hay una acción por defecto para "listar todo"
                // como no quieres listar por ahora, esta parte manejaría el caso de ID nulo/vacío
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'id' requerido");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // POST para agregar un nuevo socio
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8"); // La respuesta sigue siendo JSON
        PrintWriter out = response.getWriter();

        // --- CAMBIO AQUÍ: Leer los parámetros del formulario directamente como tu LibroController ---
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        // ---------------------------------------------------------------------------------------

        System.out.println("Datos recibidos: nombre=" + nombre + ", correo=" + correo);

        if (nombre == null || correo == null || nombre.isEmpty() || correo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos (nombre o correo).");
            return;
        }

        Socio socio = new Socio();
        try {
            socio.setIdSocio(IncrementID.generateNewId(EntityType.SOCIO));
        } catch (Exception e) {
            // Un RuntimeException no es ideal aquí si quieres controlar el error,
            // pero si tu LibroController usa RuntimeException, lo mantendremos por coherencia.
            // Para una gestión más robusta de errores, un response.sendError() sería mejor.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar ID de socio: " + e.getMessage());
            e.printStackTrace(); // Imprime la traza para depuración en el servidor
            return;
        }
        socio.setNombre(nombre);
        socio.setCorreo(correo);

        try {
            socioDAO.addSocio(socio);
            response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created para una creación exitosa
            SocioDTO socioDTO = new SocioDTO(socio);
            out.print(gson.toJson(socioDTO));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar socio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // doPut y doDelete no están incluidos por ahora, para mantener la simplicidad solicitada.
}