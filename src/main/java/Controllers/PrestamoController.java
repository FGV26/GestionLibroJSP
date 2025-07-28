package Controllers;

import Dao.PrestamoDao;
import Dto.PrestamoDTO;
import Entity.Libro;
import Entity.Socio;
import Entity.Prestamo;
import Services.IncrementID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.EntityType;
import util.LocalDateAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/prestamos")
public class PrestamoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PrestamoDao prestamoDAO = new PrestamoDao();
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idSocio = request.getParameter("idSocio");
        String idLibro = request.getParameter("idLibro");
        String fechaDevolucionParam = request.getParameter("fechaDevolucion");

        if (idSocio == null || idLibro == null || fechaDevolucionParam == null
                || idSocio.isEmpty() || idLibro.isEmpty() || fechaDevolucionParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos requeridos");
            return;
        }

        try {
            LocalDate fechaPrestamo = LocalDate.now();
            LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionParam);

            // Crear objetos con solo los IDs
            Libro libro = new Libro();
            libro.setIdLibro(idLibro);

            Socio socio = new Socio();
            socio.setIdSocio(idSocio);

            IncrementID incrementID = new IncrementID();

            Prestamo prestamo = new Prestamo();
            prestamo.setIdPrestamo(incrementID.generateNewId(EntityType.PRESTAMO));
            prestamo.setLibro(libro);
            prestamo.setSocio(socio);
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);
            prestamo.setFechaRetorno(fechaDevolucion);

            prestamoDAO.addPrestamo(prestamo);

            PrestamoDTO dto = new PrestamoDTO(prestamo);
            out.print(gson.toJson(dto));

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al crear préstamo");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            List<Prestamo> prestamos = prestamoDAO.allPrestamos();

            List<PrestamoDTO> prestamosDTO = new ArrayList<>();
            for (Prestamo p : prestamos) {
                PrestamoDTO dto = new PrestamoDTO();
                dto.setIdPrestamo(p.getIdPrestamo());
                dto.setIdLibro(p.getLibro().getIdLibro());
                dto.setTituloLibro(p.getLibro().getTitulo());
                dto.setIdSocio(p.getSocio().getIdSocio());
                dto.setNombreSocio(p.getSocio().getNombre());
                dto.setFechaPrestamo(p.getFechaPrestamo());
                dto.setFechaDevolucion(p.getFechaDevolucion());
                dto.setFechaRetorno(p.getFechaRetorno());
                prestamosDTO.add(dto);
            }

            out.print(gson.toJson(prestamosDTO));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los préstamos");
            e.printStackTrace();
        }
    }

}
