PRUEBAS DE SERVICIOS REST - USUARIOS DE LA API PETSTORE (KARATE)
==================================================================

API bajo prueba: https://petstore.swagger.io/v2  (documentación: https://petstore.swagger.io/)

Casos automatizados (src/test/java/co/com/sofka/petstore/usuarios/gestion-usuarios.feature):
  @crear                  Crear un usuario                        POST   /user
  @consultar              Buscar el usuario creado                GET    /user/{username}
  @actualizar             Actualizar el nombre y el correo        PUT    /user/{username}
  @consultar_actualizado  Buscar el usuario actualizado           GET    /user/{username}
  @eliminar               Eliminar el usuario (y comprobar 404)   DELETE /user/{username}
  @e2e                    Ciclo de vida completo en un solo flujo (los 5 pasos en orden)


TECNOLOGÍAS Y VERSIONES
-----------------------
  - Java (JDK)    11 (probado con Temurin 11.0.32)
  - Gradle        7.6.3 (incluido con el wrapper: gradlew / gradlew.bat)
  - Karate        1.4.1 (com.intuit.karate:karate-junit5)
  - JUnit         5 (incluido en karate-junit5)


ESTRUCTURA DEL PROYECTO
-----------------------
src/test/java
  ├── karate-config.js                     URL base, timeouts y política de reintentos
  └── co/com/sofka/petstore
      ├── PetstoreRunnerTest.java          Runner JUnit 5 (ejecución en paralelo)
      ├── usuarios/gestion-usuarios.feature  Casos de prueba
      ├── helpers/                         Features reutilizables (@ignore): crear,
      │                                    actualizar y eliminar (limpieza de datos)
      ├── data/                            Generador de usuarios únicos y datos de actualización
      └── schemas/                         Esquemas JSON para validar el contrato
reportes/karate-reports/                   Reporte de la última ejecución (abrir karate-summary.html)


ENTRADAS, SALIDAS Y VARIABLES
-----------------------------
  - Entradas: el usuario se genera en data/generar-usuario.js (id y username únicos por escenario).
    La actualización (data/actualizar-nombre-y-correo.js) cambia solo firstName y email.
  - Salidas capturadas: status HTTP, cuerpo de la respuesta (code, type, message) y el usuario
    consultado. Todo queda registrado (request/response) en el reporte HTML.
  - Validaciones: código de estado, esquema JSON (schemas/*.json), valores exactos
    (message = id del usuario al crear/actualizar, message = username al eliminar) y que el
    usuario eliminado responda 404 "User not found".
  - Variables: baseUrl y rutaBase (karate-config.js), usuario, usuarioActualizado.


PASOS DE EJECUCIÓN
------------------
1. Instalar JDK 11 o superior y verificar:        java -version
2. Abrir una terminal en la carpeta del proyecto (02-automatizacion-api-karate).
3. Ejecutar todas las pruebas:

     Windows:      gradlew.bat clean test
     Linux/macOS:  ./gradlew clean test

4. Ejecutar solo un caso por etiqueta (ejemplo: solo el flujo E2E):

     gradlew.bat clean test "-Dkarate.options=--tags @e2e"

5. Ver el reporte:
     build/karate-reports/karate-summary.html

   Si ya tiene Gradle 7.x instalado, también puede usar "gradle clean test".
