# 🏡 Software de Gestión de Cabañas - Backend

Este proyecto es un sistema de gestión diseñado para administrar reservas, clientes y disponibilidad en un complejo de cabañas. Desarrollado con tecnologías modernas, busca ofrecer un backend seguro, eficiente y escalable.

---

## 🚀 Tecnologías utilizadas

### 🧠 Backend
- ![Java](https://img.shields.io/badge/Java-ED8B00?logo=java&logoColor=white) **Java 21**
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=spring-boot&logoColor=white) **Spring Boot**
- 🔐 **Spring Security**
- 🛡️ **JWT (JSON Web Tokens)**

### 🗄️ Base de Datos
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?logo=postgresql&logoColor=white) **PostgreSQL**
- ☁️ **Alojamiento inicial**: Railway  
- ☁️ **Migración a producción**: AWS S3 (mayor estabilidad y escalabilidad)

---

## 🔐 Seguridad

Implementamos **Spring Security** con **JWT**, y las **sesiones se almacenan en la base de datos** para garantizar persistencia y trazabilidad de usuarios autenticados.

---

## 📦 Estructura del proyecto

- `controller/` - Maneja solicitudes HTTP
- `service/` - Lógica de negocio (reservas, clientes, etc.)
- `repository/` - Acceso a la base de datos con JPA
- `dto/` - **DTOs** para intercambio de datos limpios y seguros
- `model/` - Entidades JPA vinculadas a la base de datos

---

## ⚙️ Funcionalidades principales

- 📅 Gestión de reservas
- 🧍‍♂️ Registro y administración de clientes
- 🛏️ Control de disponibilidad de cabañas
- 🔐 Seguridad con login, roles y permisos
- 💾 Persistencia de sesiones en PostgreSQL

---

## 💻 Cómo correr el proyecto

```bash
git clone https://github.com/tuusuario/NomadNook.git
cd software-cabanas
./mvnw spring-boot:run
