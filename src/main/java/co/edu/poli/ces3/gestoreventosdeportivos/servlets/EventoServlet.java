package co.edu.poli.ces3.gestoreventosdeportivos.servlets;

import co.edu.poli.ces3.gestoreventosdeportivos.dao.EventoDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.model.Evento;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "EventoServlet", urlPatterns = {"/eventos", "/eventos/vender-entradas", "/eventos/actualizar-estado", "/estadisticas"})
public class EventoServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Evento evento = gson.fromJson(request.getReader(), Evento.class);

        Evento eventoCreado = EventoDAO.crearEvento(evento);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (eventoCreado != null) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(eventoCreado));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No se pudo crear el evento. Asegúrese de que hay al menos 2 equipos participantes del mismo deporte.\"}");
        }

        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getServletPath();

        if (pathInfo.equals("/estadisticas")) {
            obtenerEstadisticas(response);
        } else {
            listarEventos(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getServletPath();

        if (pathInfo.equals("/eventos/vender-entradas")) {
            venderEntradas(request, response);
        } else if (pathInfo.equals("/eventos/actualizar-estado")) {
            actualizarEstado(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listarEventos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String deporte = request.getParameter("deporte");
        String estado = request.getParameter("estado");
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        List<Evento> eventos = EventoDAO.filtrarEventos(deporte, estado, fechaInicio, fechaFin);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(eventos));
        out.flush();
    }

    private void venderEntradas(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String eventoIdParam = request.getParameter("eventoId");
        String cantidadParam = request.getParameter("cantidad");

        try {
            int eventoId = Integer.parseInt(eventoIdParam);
            int cantidad = Integer.parseInt(cantidadParam);

            boolean vendido = EventoDAO.venderEntradas(eventoId, cantidad);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (vendido) {
                out.print("{\"message\": \"Entradas vendidas exitosamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"No se pudieron vender las entradas. Verifique la disponibilidad.\"}");
            }

            out.flush();
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Parámetros inválidos\"}");
        }
    }

    private void actualizarEstado(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String eventoIdParam = request.getParameter("eventoId");
        String estado = request.getParameter("estado");

        try {
            int eventoId = Integer.parseInt(eventoIdParam);

            boolean actualizado = EventoDAO.actualizarEstado(eventoId, estado);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (actualizado) {
                out.print("{\"message\": \"Estado del evento actualizado exitosamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"No se pudo actualizar el estado del evento\"}");
            }

            out.flush();
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"ID de evento inválido\"}");
        }
    }

    private void obtenerEstadisticas(HttpServletResponse response) throws IOException {
        // Implementar estadísticas
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Ejemplo de estadísticas
        out.print("{\"estadisticas\": \"Aquí irían las estadísticas calculadas\"}");
        out.flush();
    }
}
