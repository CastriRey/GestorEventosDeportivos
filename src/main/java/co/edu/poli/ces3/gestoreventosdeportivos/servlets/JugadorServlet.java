package co.edu.poli.ces3.gestoreventosdeportivos.servlets;

import co.edu.poli.ces3.gestoreventosdeportivos.dao.JugadorDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.model.Jugador;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "JugadorServlet", urlPatterns = {"/jugadores", "/jugadores/transferir"})
public class JugadorServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Jugador jugador = gson.fromJson(request.getReader(), Jugador.class);

        Jugador jugadorCreado = JugadorDAO.crearJugador(jugador);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (jugadorCreado != null) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(jugadorCreado));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No se pudo crear el jugador. Puede que ya exista un jugador con el mismo número en el equipo.\"}");
        }

        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getServletPath();

        if (pathInfo.equals("/jugadores/transferir")) {
            transferirJugador(request, response);
        } else {
            listarJugadores(response);
        }
    }

    private void transferirJugador(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String jugadorIdParam = request.getParameter("jugadorId");
        String equipoDestinoParam = request.getParameter("equipoDestino");

        try {
            int jugadorId = Integer.parseInt(jugadorIdParam);
            int equipoDestino = Integer.parseInt(equipoDestinoParam);

            boolean transferido = JugadorDAO.transferirJugador(jugadorId, equipoDestino);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (transferido) {
                out.print("{\"message\": \"Jugador transferido exitosamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"No se pudo transferir el jugador. Verifique los IDs y que no haya otro jugador con el mismo número en el equipo destino.\"}");
            }

            out.flush();
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Parámetros inválidos\"}");
        }
    }

    private void listarJugadores(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(JugadorDAO.obtenerTodos()));
        out.flush();
    }
}