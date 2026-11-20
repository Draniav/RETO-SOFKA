PRUEBA E2E - FLUJO DE COMPRA COMO INVITADO EN OPENCART
=========================================================

Prueba funcional automatizada (E2E) sobre http://opencart.abstracta.us/ que:
  1. Agrega dos productos al carrito (iPhone y MacBook)
  2. Visualiza el carrito y valida que estén los dos productos
  3. Completa el checkout como invitado ("Guest Checkout")
  4. Finaliza la compra y valida el mensaje "Your order has been placed!"


TECNOLOGÍAS Y VERSIONES
-----------------------
  - Java (JDK)             11 (probado con Temurin 11.0.32)
  - Gradle                 7.6.3 (incluido con el wrapper: gradlew / gradlew.bat)
  - Serenity BDD           4.0.46 (patrón Screenplay)
  - Cucumber               7 (incluido en serenity-cucumber), Gherkin en español
  - JUnit                  4.13.2
  - Navegador              Google Chrome (el driver se descarga automáticamente)


ESTRUCTURA DEL PROYECTO
-----------------------
src/test/java/co/com/sofka/opencart
  ├── runners/            Runner de Cucumber con Serenity
  ├── stepdefinitions/    Pasos Gherkin, hooks y conversión de tablas a objetos
  ├── tasks/              Tareas de negocio (AgregarAlCarrito, FinalizarLaCompraComoInvitado, ...)
  ├── interactions/       Interacciones reutilizables (clic con espera explícita)
  ├── questions/          Preguntas para las validaciones (productos del carrito, mensaje final)
  ├── userinterfaces/     Localizadores (Targets) por página
  ├── models/             Modelo inmutable del comprador (patrón Builder)
  └── utils/              Constantes de espera y generador de correos únicos
src/test/resources
  ├── features/           compra_como_invitado.feature
  └── serenity.conf       Configuración del navegador y la URL base
reportes/serenity/        Reporte de Serenity de la última ejecución (abrir index.html)


PASOS DE EJECUCIÓN
------------------
1. Instalar JDK 11 o superior y verificar:            java -version
2. Tener Google Chrome instalado.
3. Abrir una terminal en la carpeta del proyecto (01-automatizacion-web-serenity).
4. Ejecutar las pruebas:

     Windows:      gradlew.bat clean test
     Linux/macOS:  ./gradlew clean test

   Sin interfaz gráfica (headless):
     Windows:      gradlew.bat clean test -Dheadless.mode=true
     Linux/macOS:  ./gradlew clean test -Dheadless.mode=true

   Si ya tiene Gradle 7.x instalado, también puede usar "gradle clean test".

5. Abrir el reporte generado:
     target/site/serenity/index.html

   La tarea "test" ejecuta al final "aggregate", que genera el reporte aunque haya fallos.


NOTAS
-----
  - Cada ejecución genera un correo único, así que la prueba se puede repetir sin limpiar datos.
  - Los datos del comprador están en la tabla del .feature, no en el código.
  - Las esperas son explícitas (WaitUntil), sin Thread.sleep.
