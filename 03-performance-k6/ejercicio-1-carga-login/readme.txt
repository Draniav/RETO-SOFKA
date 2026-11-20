PRUEBA DE CARGA - SERVICIO DE LOGIN (FAKESTORE API) CON K6
=============================================================

Servicio:  POST https://fakestoreapi.com/auth/login   (cURL entregado en docs/curl-entregado.png)
Datos:     data/usuarios.csv (columnas user,passwd), repartidos en round-robin

Criterios de aceptación, implementados como thresholds (la prueba falla si no se cumplen):
  - Throughput:            al menos 20 TPS      -> http_reqs rate >= 20
  - Tiempo de respuesta:   máximo 1,5 s         -> http_req_duration max <= 1500 ms y p(95) < 1500 ms
  - Tasa de error:         menor al 3 %         -> http_req_failed rate < 0.03
  - Validaciones de cada respuesta (checks): status 200/201, token JWT y duración <= 1,5 s


TECNOLOGÍAS Y VERSIONES
-----------------------
  - k6                              v2.3.0 (probado). Compatible desde v0.46
  - Librerías remotas (las descarga k6 en tiempo de ejecución, se necesita internet):
      papaparse 5.1.1        https://jslib.k6.io/papaparse/5.1.1/index.js
      k6-summary 0.0.2       https://jslib.k6.io/k6-summary/0.0.2/index.js
      k6-reporter 2.4.0      https://raw.githubusercontent.com/benc-uk/k6-reporter/2.4.0/dist/bundle.js


ESCENARIO DE CARGA
------------------
Modelo abierto con el executor "ramping-arrival-rate": k6 inyecta un número fijo de peticiones
por segundo, sin importar cuánto tarde el servidor. Así se garantiza el TPS exigido.
  1. Rampa de subida      1 -> 25 TPS en 30 s
  2. Carga sostenida      25 TPS durante 2 min
  3. Descenso             25 -> 0 TPS en 10 s
  VUs: 30 preasignados y hasta 100 si el servicio se pone lento.
Todo es configurable por variables de entorno (ver src/config/configuracion.js).


ESTRUCTURA
----------
  src/login-load-test.js       Script principal (escenario + generación de reportes)
  src/config/configuracion.js  Parámetros y criterios de aceptación
  src/config/opciones.js       Escenario de carga y thresholds
  src/datos/usuarios.js        Lectura del CSV con SharedArray
  src/servicios/login.js       Petición al login y validaciones (checks)
  data/usuarios.csv            Credenciales parametrizadas
  reports/                     Reportes generados (HTML, JSON y TXT)


PASOS DE EJECUCIÓN
------------------
1. Instalar k6:  https://grafana.com/docs/k6/latest/set-up/install-k6/
     Windows:  winget install k6 --source winget      (o: choco install k6)
     macOS:    brew install k6
     Linux:    ver la guía oficial (paquetes apt/dnf)
   Verificar:  k6 version

2. Abrir una terminal en la carpeta del ejercicio (03-performance-k6/ejercicio-1-carga-login).
   Es importante ejecutar desde aquí porque los reportes se escriben en ./reports.

3. Ejecutar la prueba:
     k6 run src/login-load-test.js

   Opcional, con el dashboard web de k6 y su exportación a HTML:
     Windows (PowerShell):
       $env:K6_WEB_DASHBOARD="true"; $env:K6_WEB_DASHBOARD_EXPORT="reports/dashboard.html"; k6 run src/login-load-test.js
     Linux/macOS:
       K6_WEB_DASHBOARD=true K6_WEB_DASHBOARD_EXPORT=reports/dashboard.html k6 run src/login-load-test.js

   Cambiar la carga sin tocar el código (ejemplo: 30 TPS durante 5 minutos):
     k6 run -e TPS_OBJETIVO=30 -e DURACION_SOSTENIDA=5m src/login-load-test.js

4. Revisar los resultados:
     reports/reporte-carga-login.html   Reporte visual
     reports/resumen-carga-login.txt    Resumen en texto (el mismo de la consola)
     reports/resumen-carga-login.json   Datos completos
   El código de salida de k6 es distinto de 0 si algún criterio (threshold) no se cumple.
