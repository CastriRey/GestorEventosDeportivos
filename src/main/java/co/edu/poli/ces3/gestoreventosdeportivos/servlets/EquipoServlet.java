package co.edu.poli.ces3.gestoreventosdeportivos.servlets;

import co.edu.poli.ces3.gestoreventosdeportivos.dao.EquipoDAO;
import co.edu.poli.ces3.gestoreventosdeportivos.model.Equipo;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "EquipoServlet", urlPatterns = {"/equipos"})
public class EquipoServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener datos del equipo desde JSON o parámetros
        Equipo equipo = gson.fromJson(request.getReader(), Equipo.class);

        Equipo equipoCreado = EquipoDAO.crearEquipo(equipo);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (equipoCreado != null) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(equipoCreado));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No se pudo crear el equipo. Puede que ya exista un equipo con el mismo nombre y deporte.\"}");
        }

        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageParam = request.getParameter("page");
        String sizeParam = request.getParameter("size");

        List<Equipo> equipos;

        if (pageParam != null && sizeParam != null) {
            try {
                int page = Integer.parseInt(pageParam);
                int size = Integer.parseInt(sizeParam);
                equipos = EquipoDAO.obtenerPagina(page, size);
            } catch (NumberFormatException e) {
                equipos = EquipoDAO.obtenerTodos();
            }
        } else {
            equipos = EquipoDAO.obtenerTodos();
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(equipos));
        out.flush();
    }
}