# 🏆 Gestor de Eventos Deportivos

![Java](https://img.shields.io/badge/Java-11%2B-blue)
![Apache Tomcat](https://img.shields.io/badge/Apache_Tomcat-9.0-red)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema completo para gestión de eventos deportivos con API REST y interfaz web.

## 📌 Características Principales

- **CRUD Completo** para:
  - Equipos (con Ids y deportes)
  - Jugadores (con transferencias)
  - Eventos deportivos

- **Funcionalidades Avanzadas**:
  - Venta controlada de entradas
  - Cambio de estados de eventos
  - Filtros dinámicos

- **Validaciones**:
  - No duplicados en equipos
  - Números únicos por equipo
  - Mínimo 2 equipos por evento

## 🚀 Tecnologías Utilizadas

| Capa         | Tecnologías                                                                 |
|--------------|-----------------------------------------------------------------------------|
| **Backend**  | Java 11, Servlets, Gson, Apache Tomcat 9                                   |
| **Frontend** | JSP, JavaScript (Fetch API), CSS3                                          |
| **Herramientas** | Maven, IntelliJ IDEA, Postman                                          |

## 🛠️ Instalación

1. **Requisitos**:
   ```bash
   JDK 11+, Apache Tomcat 9.x, Maven 3.6+
