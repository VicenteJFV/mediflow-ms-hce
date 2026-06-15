# MediFlow - Microservicio HCE (ms-hce)

Este microservicio forma parte de la plataforma de salud digital **MediFlow**. Está enfocado en el **Core HCE** (Gestión de historias clínicas electrónicas), permitiendo gestionar los datos demográficos y la historia clínica (alergias y condiciones crónicas) de los pacientes mediante una API REST.

Construido con **Spring Boot**, **JPA/Hibernate**, **Maven** y **PostgreSQL**.

---

## Requisitos Previos

* **Java JDK 21** o superior.
* **Maven 3.8+** (se incluye el Maven Wrapper `mvnw`).
* **PostgreSQL** instalado y corriendo localmente en el puerto `5432`.

---

## Configuración de la Base de Datos

1. Abre tu herramienta de administración de bases de datos preferida (pgAdmin, DBeaver, o la consola psql).
2. Conéctate a tu servidor PostgreSQL local (generalmente puerto `5432` con usuario `postgres` y contraseña `postgres`).
3. Ejecuta la siguiente instrucción SQL para crear la base de datos:
   ```sql
   CREATE DATABASE mediflow_hce;
   ```

*(Nota: Si tus credenciales de PostgreSQL o puerto difieren de las por defecto, puedes actualizarlas en el archivo `src/main/resources/application.properties`)*.

---

## Instalación y Ejecución

### 1. Compilar el Proyecto
Para descargar las dependencias y compilar el proyecto sin ejecutar las pruebas:
```bash
./mvnw clean package -DskipTests=true
```

### 2. Ejecutar la Aplicación
Puedes arrancar el servidor embebido de Spring Boot con el siguiente comando:
```bash
./mvnw spring-boot:run
```
El servidor se iniciará por defecto en el puerto `8080` (`http://localhost:8080`).

---

## Documentación de la API REST

### 1. Guardar o Actualizar Historial Clínico de Paciente
Guarda los datos de un paciente y su historial clínico. Si el `documentNumber` ya existe, se actualizarán los datos de ese paciente y su historia clínica en vez de duplicarlos.

* **URL:** `/api/patients`
* **Método:** `POST`
* **Content-Type:** `application/json`
* **Cuerpo de la Petición (Ejemplo):**
  ```json
  {
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@email.com",
    "phoneNumber": "+56912345678",
    "birthDate": "1990-05-15",
    "documentNumber": "12345678-K",
    "allergies": "Penicilina, Polen",
    "chronicConditions": "Hipertensión arterial"
  }
  ```
* **Respuesta Exitosa (201 Created):**
  ```json
  {
    "id": 1,
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@email.com",
    "phoneNumber": "+56912345678",
    "birthDate": "1990-05-15",
    "documentNumber": "12345678-K",
    "allergies": "Penicilina, Polen",
    "chronicConditions": "Hipertensión arterial"
  }
  ```

### 2. Consultar Historial Clínico de Paciente
Obtiene la información demográfica e historial clínico completo de un paciente por su ID.

* **URL:** `/api/patients/{id}`
* **Método:** `GET`
* **Respuesta Exitosa (200 OK):**
  ```json
  {
    "id": 1,
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@email.com",
    "phoneNumber": "+56912345678",
    "birthDate": "1990-05-15",
    "documentNumber": "12345678-K",
    "allergies": "Penicilina, Polen",
    "chronicConditions": "Hipertensión arterial"
  }
  ```
* **Respuesta de Error (404 Not Found):**
  * Si el ID no existe en la base de datos.

---

## Ejecutar Pruebas Unitarias

El proyecto cuenta con pruebas unitarias implementadas con **JUnit 5** y **Mockito** para asegurar una cobertura de código robusta (>60%).

Para ejecutar las pruebas:
```bash
./mvnw test
```
o en sistemas Windows:
```cmd
.\mvnw.cmd test
```
