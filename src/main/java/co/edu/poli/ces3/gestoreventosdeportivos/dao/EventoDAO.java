package co.edu.poli.ces3.gestoreventosdeportivos.dao;

import co.edu.poli.ces3.gestoreventosdeportivos.model.Evento;
import co.edu.poli.ces3.gestoreventosdeportivos.model.Equipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EventoDAO {
    private static final Map<Integer, Evento> eventos = new HashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public static Evento crearEvento(Evento evento) {
        // Validar que haya al menos 2 equipos del mismo deporte
        if (evento.getEquiposParticipantes().size() < 2) {
            return null;
        }

        String deporte = EquipoDAO.obtenerPorId(evento.getEquiposParticipantes().get(0)).getDeporte();
        for (int equipoId : evento.getEquiposParticipantes()) {
            Equipo equipo = EquipoDAO.obtenerPorId(equipoId);
            if (equipo == null || !equipo.getDeporte().equals(deporte)) {
                return null;
            }
        }

        evento.setId(idGenerator.getAndIncrement());
        eventos.put(evento.getId(), evento);
        return evento;
    }

    public static List<Evento> obtenerTodos() {
        return new ArrayList<>(eventos.values());
    }

    public static List<Evento> filtrarEventos(String deporte, String estado, String fechaInicio, String fechaFin) {
        List<Evento> filtrados = new ArrayList<>();

        for (Evento evento : eventos.values()) {
            boolean cumpleFiltro = true;

            if (deporte != null && !deporte.isEmpty() && !evento.getDeporte().equalsIgnoreCase(deporte)) {
                cumpleFiltro = false;
            }

            if (estado != null && !estado.isEmpty() && !evento.getEstado().equalsIgnoreCase(estado)) {
                cumpleFiltro = false;
            }

            // Implementar lógica de fechas si es necesario

            if (cumpleFiltro) {
                filtrados.add(evento);
            }
        }

        return filtrados;
    }

    public static boolean venderEntradas(int eventoId, int cantidad) {
        Evento evento = eventos.get(eventoId);
        if (evento == null || cantidad <= 0) {
            return false;
        }

        if (evento.getEntradasVendidas() + cantidad > evento.getCapacidad()) {
            return false;
        }

        evento.setEntradasVendidas(evento.getEntradasVendidas() + cantidad);
        return true;
    }

    public static boolean actualizarEstado(int eventoId, String nuevoEstado) {
        Evento evento = eventos.get(eventoId);
        if (evento == null) {
            return false;
        }

        evento.setEstado(nuevoEstado);
        return true;
    }

    // Métodos para estadísticas
    public static Map<String, Integer> contarEventosPorDeporte() {
        Map<String, Integer> conteo = new HashMap<>();

        for (Evento evento : eventos.values()) {
            conteo.put(evento.getDeporte(), conteo.getOrDefault(evento.getDeporte(), 0) + 1);
        }

        return conteo;
    }

    public static Map<Integer, Double> calcularPorcentajeOcupacion() {
        Map<Integer, Double> porcentajes = new HashMap<>();

        for (Evento evento : eventos.values()) {
            double porcentaje = (double) evento.getEntradasVendidas() / evento.getCapacidad() * 100;
            porcentajes.put(evento.getId(), porcentaje);
        }

        return porcentajes;
    }
}