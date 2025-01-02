Microservicio de Gestión de Usuarios

Descripción
  Este proyecto implementa un microservicio para la creación y consulta de usuarios utilizando Spring Boot 2.5.14 y Gradle 7.4. El sistema permite la creación de usuarios con validaciones y el uso de JWT para autenticación.

Requisitos
  Tecnologías necesarias
    Java: Versión 11
    Gradle: Versión 7.4 o anterior
    Spring Boot: Versión 2.5.14

Dependencias principales
  Spring Web: Para construir APIs REST
  Spring Data JPA: Para persistencia de datos
  Spring Security: Para manejo de autenticación con JWT
  Spring Validation: Validación de datos en los endpoints

Instalación y Ejecución
  Pasos para levantar el proyecto
    Clonar el repositorio:
      git clone <URL_DEL_REPOSITORIO>
      cd <NOMBRE_DEL_PROYECTO>

  Ejecutar el proyecto:
    ./gradlew bootRun

  Acceder al microservicio:
    La aplicación estará disponible en: http://localhost:8080.
    

Endpoints
  POST /api/sign-up
    Descripción: Crea un nuevo usuario.
    Ejemplo de solicitud:
      {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "password": "A1validpass",
        "phones": [
          {
            "number": 987654321,
            "citycode": 1,
            "contrycode": "+56"
          }
        ]
      }

    Ejemplo de respuesta exitosa:
      {
        "id": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
        "created": "2024-12-23T15:00:00",
        "lastLogin": "2024-12-23T15:00:00",
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "isActive": true,
        "name": "John Doe",
        "email": "john.doe@example.com",
        "phones": [
          {
            "number": 987654321,
            "citycode": 1,
            "contrycode": "+56"
          }
        ]
      }

    Errores posibles:
      Formato de correo no válido.
      Contraseña no cumple con las reglas de validación.
      Usuario ya existe.

  POST /api/login
    Descripción: Permite iniciar sesión con un token generado previamente.
    Requiere token en el encabezado:
      Authorization: Bearer <TOKEN>
    Ejemplo de respuesta exitosa:
      {
        "id": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
        "created": "2024-12-23T15:00:00",
        "lastLogin": "2024-12-23T16:00:00",
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "isActive": true,
        "name": "John Doe",
        "email": "john.doe@example.com",
        "phones": [
          {
            "number": 987654321,
            "citycode": 1,
            "contrycode": "+56"
          }
        ]
      }

    Errores posibles:
      Token no válido.
      Usuario no encontrado.

Pruebas
  Ejecución de pruebas
    Ejecutar las pruebas unitarias con el siguiente comando: ./gradlew test

  Cobertura de pruebas
    El proyecto incluye pruebas unitarias para los servicios principales, con una cobertura mínima del 80%. Las pruebas se encuentran en src/test/java/service/userservicetest.

