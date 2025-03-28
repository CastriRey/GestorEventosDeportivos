package co.edu.poli.ces3.gestoreventosdeportivos.dao;


import co.edu.poli.ces3.gestoreventosdeportivos.model.Equipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EquipoDAO {
    private static final Map<Integer, Equipo> equipos = new HashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public static Equipo crearEquipo(Equipo equipo) {
        // Validar que no exista equipo con mismo nombre y deporte
        for (Equipo e : equipos.values()) {
            if (e.getNombre().equals(equipo.getNombre()) && e.getDeporte().equals(equipo.getDeporte())) {
                return null;
            }
        }

        equipo.setId(idGenerator.getAndIncrement());
        equipos.put(equipo.getId(), equipo);
        return equipo;
    }

    public static List<Equipo> obtenerTodos() {
        return new ArrayList<>(equipos.values());
    }

    public static List<Equipo> obtenerPagina(int page, int size) {
        List<Equipo> todos = obtenerTodos();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= todos.size()) {
            return new ArrayList<>();
        }
        int toIndex = Math.min(fromIndex + size, todos.size());
        return todos.subList(fromIndex, toIndex);
    }

    public static Equipo obtenerPorId(int id) {
        return equipos.get(id);
    }

    public static void agregarJugadorAEquipo(int equipoId, int jugadorId) {
        Equipo equipo = equipos.get(equipoId);
        if (equipo != null) {
            equipo.getJugadores().add(jugadorId);
        }
    }

    public static void removerJugadorDeEquipo(int equipoId, int jugadorId) {
        Equipo equipo = equipos.get(equipoId);
        if (equipo != null) {
            equipo.getJugadores().removeIf(id -> id == jugadorId);
        }
    }
}