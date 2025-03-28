package co.edu.poli.ces3.gestoreventosdeportivos.dao;

import co.edu.poli.ces3.gestoreventosdeportivos.model.Jugador;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JugadorDAO {
    private static final Map<Integer, Jugador> jugadores = new HashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public static Jugador crearJugador(Jugador jugador) {
        // Validar que no exista jugador con mismo número en el mismo equipo
        for (Jugador j : jugadores.values()) {
            if (j.getEquipoId() == jugador.getEquipoId() && j.getNumero() == jugador.getNumero()) {
                return null;
            }
        }

        jugador.setId(idGenerator.getAndIncrement());
        jugadores.put(jugador.getId(), jugador);
        EquipoDAO.agregarJugadorAEquipo(jugador.getEquipoId(), jugador.getId());
        return jugador;
    }

    public static List<Jugador> obtenerTodos() {
        return new ArrayList<>(jugadores.values());
    }

    public static Jugador obtenerPorId(int id) {
        return jugadores.get(id);
    }

    public static boolean transferirJugador(int jugadorId, int equipoDestinoId) {
        Jugador jugador = jugadores.get(jugadorId);
        if (jugador == null || EquipoDAO.obtenerPorId(equipoDestinoId) == null) {
            return false;
        }

        // Verificar que no haya otro jugador con el mismo número en el equipo destino
        for (Jugador j : jugadores.values()) {
            if (j.getEquipoId() == equipoDestinoId && j.getNumero() == jugador.getNumero()) {
                return false;
            }
        }

        // Actualizar equipo del jugador
        int equipoAnteriorId = jugador.getEquipoId();
        jugador.setEquipoId(equipoDestinoId);

        // Actualizar listas de jugadores en los equipos
        EquipoDAO.removerJugadorDeEquipo(equipoAnteriorId, jugadorId);
        EquipoDAO.agregarJugadorAEquipo(equipoDestinoId, jugadorId);

        return true;
    }
}