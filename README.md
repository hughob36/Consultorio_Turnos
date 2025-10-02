# 🏥 API de Gestión de Turnos Médicos (Appointment Management API)

## 🌟 Resumen del Proyecto

Este es un proyecto *backend* desarrollado con **Spring Boot** que simula un sistema robusto para la **gestión de turnos (citas)**, especialistas, roles y permisos.

El objetivo principal es demostrar habilidades en la creación de una **API RESTful profesional**, la implementación de **seguridad basada en roles (RBAC) con Spring Security** y la generación de **documentación interactiva con Swagger/OpenAPI**.

-----

## 🛠️ Tecnologías Clave

  * **Lenguaje:** Java 21
  * **Framework Principal:** **Spring Boot 3**
  * **API REST:** Spring Web
  * **Seguridad:** **Spring Security** (Autenticación JWT, Autorización `@PreAuthorize`)
  * **Base de Datos:** **MySQL**
  * **Persistencia:** Spring Data JPA / Hibernate
  * **Documentación:** **Swagger/OpenAPI 3**
  * **Validación:** Jakarta Validation (`@Valid`)
  * **Mapeo:** Uso de **DTOs** para Request/Response (patrón profesional).

-----

## 🔒 Arquitectura de Seguridad

La aplicación utiliza un esquema de **Autorización y Autenticación basado en Tokens JWT (JSON Web Tokens)**, implementando una seguridad granular:

  * **Roles Definidos:** `ADMIN` y `USER`.
  * **Acceso a Endpoints:** El acceso se controla mediante la anotación **`@PreAuthorize`**, garantizando que solo los roles autorizados puedan realizar ciertas operaciones.
      * **Ejemplo:** Solo `ADMIN` puede crear, modificar o eliminar especialistas, roles y usuarios. `USER` solo puede consultar listas o sus propios datos.
  * **Controladores Seguros:** El controlador base tiene un *fail-safe* (`@PreAuthorize("denyAll")`) para forzar la autorización explícita en cada método.

-----

## 🗺️ Estructura de Endpoints (API RESTful)

La API está organizada en controladores claros y sigue convenciones RESTful estándar, utilizando códigos de estado HTTP apropiados (`200 OK`, `201 CREATED`, `404 NOT FOUND`, `409 CONFLICT`).

| Controlador | Recurso Base | Operaciones Clave | Seguridad |
| :--- | :--- | :--- | :--- |
| **`AuthenticationController`** | `/auth` | `POST /login` (Generación de JWT) | Pública |
| **`AppointmentController`** | `/api/appointment` | CRUD completo, `PATCH` para cambio de estado. | `ADMIN`, `USER` |
| **`SpecialistController`** | `/api/specialist` | CRUD, Borrado Lógico (`DELETE`). | `ADMIN`, `USER` |
| **`UserAppController`** | `/api/user` | CRUD de usuarios con asignación de roles. | `ADMIN` |
| **`RoleController`** | `/api/role` | CRUD de roles con asignación de permisos. | `ADMIN` |
| **`PermissionController`** | `/api/permission` | CRUD de permisos. | `ADMIN` |

-----

## ⚙️ Configuración y Ejecución

### 1\. Requisitos Previos

Asegúrate de tener instalado:

  * [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
  * [Maven](https://maven.apache.org/download.cgi) 

### 2\. Clonar el Repositorio

```bash
git clone https://github.com/hughob36/Consultorio_Turnos.git

```

### 3\. Configuración de la Base de Datos

Edita el archivo `application.properties` ubicado en `src/main/resources/` con tus credenciales de base de datos:

```properties
# Ejemplo para MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/gestionTurno?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

### 4\. Ejecutar la Aplicación

Puedes iniciar la aplicación usando Maven (o tu herramienta de construcción preferida):

```bash
# Con Maven
./mvnw spring-boot:run
```

La API estará disponible por defecto en `http://localhost:8080`.

-----

## 📖 Documentación Interactivas (Swagger UI)

Una vez que la aplicación esté corriendo, la documentación interactiva de Swagger UI estará disponible automáticamente en:

👉 **http://localhost:8080/swagger-ui/index.html**

Utiliza esta interfaz para probar todos los *endpoints*, **incluyendo la autenticación (`/auth/login`) para obtener un token JWT** que luego podrás usar para acceder a los recursos protegidos.

-----

## 🔑 Autenticación y uso del Token JWT

1. En **Swagger UI** entra a `POST /auth/login` y envía:

```json
{
  "username": "admin",
  "password": "admin123"
}



| Nombre                   | Rol                   | Contacto                                                    |
| :----------------------- | :-------------------- | :---------------------------------------------------------- |
| **Hugo Orlando Benitez** | Desarrollador Backend | [LinkedIn](https://www.linkedin.com/in/hugo-benitez-hob36/) |


