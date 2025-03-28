<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Eventos Deportivos</title>
  <style>
      /* Estilos mejorados */
      body {
          font-family: Arial, sans-serif;
          margin: 20px;
          line-height: 1.6;
      }

      h1 {
          color: #2c3e50;
          text-align: center;
      }

      #filtroForm {
          background: #f9f9f9;
          padding: 15px;
          border-radius: 5px;
          margin-bottom: 20px;
          display: flex;
          flex-wrap: wrap;
          gap: 10px;
          align-items: center;
      }

      #filtroForm label {
          font-weight: bold;
      }

      #filtroForm input, #filtroForm select {
          padding: 8px;
          border: 1px solid #ddd;
          border-radius: 4px;
      }

      #filtroForm button {
          padding: 8px 15px;
          background-color: #3498db;
          color: white;
          border: none;
          border-radius: 4px;
          cursor: pointer;
      }

      #filtroForm button:hover {
          background-color: #2980b9;
      }

      #tablaEventos {
          width: 100%;
          border-collapse: collapse;
          margin-top: 20px;
      }

      #tablaEventos th, #tablaEventos td {
          border: 1px solid #ddd;
          padding: 12px;
          text-align: left;
      }

      #tablaEventos th {
          background-color: #3498db;
          color: white;
      }

      #tablaEventos tr:nth-child(even) {
          background-color: #f2f2f2;
      }

      #tablaEventos tr:hover {
          background-color: #e6f7ff;
      }

      .loading {
          text-align: center;
          padding: 20px;
          font-style: italic;
          color: #666;
      }

      .error-message {
          color: #e74c3c;
          font-weight: bold;
      }
  </style>
</head>
<body>
<h1>Eventos Deportivos</h1>

<form id="filtroForm">
  <label for="deporte">Deporte:</label>
  <input type="text" id="deporte" name="deporte" placeholder="Ej: Fútbol">

  <label for="estado">Estado:</label>
  <select id="estado" name="estado">
    <option value="">Todos</option>
    <option value="Programado">Programado</option>
    <option value="En curso">En curso</option>
    <option value="Finalizado">Finalizado</option>
    <option value="Cancelado">Cancelado</option>
  </select>

  <button type="button" onclick="filtrarEventos()">Filtrar</button>
</form>

<table id="tablaEventos">
  <thead>
  <tr>
    <th>Nombre</th>
    <th>Fecha</th>
    <th>Lugar</th>
    <th>Deporte</th>
    <th>Capacidad</th>
    <th>Entradas Vendidas</th>
    <th>Estado</th>
    <th>Equipos Participantes</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td colspan="8" class="loading">Cargando eventos...</td>
  </tr>
  </tbody>
</table>

<script>
    // Funciones auxiliares JavaScript - Versión corregida
    const formatValue = (value) => {
        if (value === false || value === null || value === undefined) return 'N/A';
        if (typeof value === 'string' && value.trim() === '') return 'N/A';
        return value;
    };

    const formatNumber = (value) => {
        if (value === false || value === null || isNaN(value)) return '0';
        return value;
    };

    const formatTeams = (teams) => {
        if (!teams || !Array.isArray(teams)) return 'N/A';
        return teams.join(', ');
    };

    // Función para escapar HTML (seguridad)
    const escapeHtml = (text) => {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    };

    // Función principal corregida
    const cargarEventos = async (filtros = {}) => {
        const tablaBody = document.querySelector('#tablaEventos tbody');

        try {
            tablaBody.innerHTML = '<tr><td colspan="8" class="loading">Cargando eventos...</td></tr>';

            const url = new URL('/GestorEventosDeportivos/eventos', window.location.origin);

            if (filtros.deporte && filtros.deporte.trim() !== '') {
                url.searchParams.append('deporte', filtros.deporte.trim());
            }

            if (filtros.estado && filtros.estado.trim() !== '') {
                url.searchParams.append('estado', filtros.estado.trim());
            }

            const response = await fetch(url.toString());

            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }

            const data = await response.json();
            tablaBody.innerHTML = '';

            if (!data || data.length === 0) {
                tablaBody.innerHTML = '<tr><td colspan="8">No hay eventos registrados</td></tr>';
                return;
            }

            // Versión corregida - sin template literals que confundan a JSP
            data.forEach(evento => {
                const row = document.createElement('tr');

                // Crear celdas manualmente para evitar problemas con JSP
                const celdas = [
                    formatValue(evento.nombre),
                    formatValue(evento.fecha),
                    formatValue(evento.lugar),
                    formatValue(evento.deporte),
                    formatNumber(evento.capacidad),
                    formatNumber(evento.entradasVendidas),
                    formatValue(evento.estado),
                    formatTeams(evento.equiposParticipantes)
                ];

                celdas.forEach(texto => {
                    const td = document.createElement('td');
                    td.textContent = texto;
                    row.appendChild(td);
                });

                tablaBody.appendChild(row);
            });

        } catch (error) {
            console.error('Error:', error);
            tablaBody.innerHTML = `
            <tr>
                <td colspan="8" class="error-message">
                    Error al cargar los eventos. Por favor intente nuevamente.
                </td>
            </tr>
        `;
        }
    };

    // Inicialización
    document.addEventListener('DOMContentLoaded', () => {
        cargarEventos();
    });

    // Función para aplicar filtros
    const filtrarEventos = () => {
        const deporte = document.getElementById('deporte').value;
        const estado = document.getElementById('estado').value;

        cargarEventos({
            deporte: deporte,
            estado: estado
        });
    };

    // Cargar eventos cuando la página esté lista
    document.addEventListener('DOMContentLoaded', cargarEventos);
</script>
</body>
</html>