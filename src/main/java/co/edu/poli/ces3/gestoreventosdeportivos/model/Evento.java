package co.edu.poli.ces3.gestoreventosdeportivos.model;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private int id;
    private String nombre;
    private String fecha;
    private String lugar;
    private String deporte;
    private List<Integer> equiposParticipantes;
    private int capacidad;
    private int entradasVendidas;
    private String estado; // "Programado", "En curso", "Finalizado", "Cancelado"

    // Constructor, getters y setters
    public Evento() {
        this.equiposParticipantes = new ArrayList<>();
        this.estado = "Programado";
        this.entradasVendidas = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public List<Integer> getEquiposParticipantes() {
        return equiposParticipantes;
    }

    public void setEquiposParticipantes(List<Integer> equiposParticipantes) {
        this.equiposParticipantes = equiposParticipantes;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}