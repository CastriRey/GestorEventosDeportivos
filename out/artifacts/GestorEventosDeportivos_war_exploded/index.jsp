<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 26/03/2025
  Time: 1:11 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Eventos Deportivos</title>
  <style>
      table {
          width: 100%;
          border-collapse: collapse;
      }
      table, th, td {
          border: 1px solid black;
      }
      th, td {
          padding: 8px;
          text-align: left;
      }
  </style>
</head>
<body>
<h1>Eventos Deportivos</h1>

<form id="filtroForm">
  <label for="deporte">Deporte:</label>
  <input type="text" id="deporte" name="deporte">

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
  <!-- Los datos se cargarán dinámicamente con JavaScript -->
  </tbody>
</table>

<script>
    // Cargar eventos al cargar la página
    window.onload = function() {
        cargarEventos();
    };

    function cargarEventos(filtros = {}) {
        let url = '/eventos';
        const params = new URLSearchParams();

        if (filtros.deporte) {
            params.append('deporte', filtros.deporte);
        }

        if (filtros.estado) {
            params.append('estado', filtros.estado);
        }

        if (params.toString()) {
            url += '?' + params.toString();
        }

        fetch(url)
            .then(response => response.json())
            .then(data => {
                const tabla = document.querySelector('#tablaEventos tbody');
                tabla.innerHTML = '';

                data.forEach(evento => {
                    const fila = document.createElement('tr');

                    fila.innerHTML = `
                            <td>${evento.nombre}</td>
                            <td>${evento.fecha}</td>
                            <td>${evento.lugar}</td>
                            <td>${evento.deporte}</td>
                            <td>${evento.capacidad}</td>
                            <td>${evento.entradasVendidas}</td>
                            <td>${evento.estado}</td>
                            <td>${evento.equiposParticipantes.join(', ')}</td>
                        `;

                    tabla.appendChild(fila);
                });
            })
            .catch(error => console.error('Error:', error));
    }

    function filtrarEventos() {
        const deporte = document.getElementById('deporte').value;
        const estado = document.getElementById('estado').value;

        cargarEventos({
            deporte: deporte,
            estado: estado
        });
    }
</script>
</body>
</html>