# Reto técnico – Analista de Automatización de Pruebas

Solución de la Prueba Técnica V9 (automatización funcional) y del Ejercicio de Performance.
Cada carpeta es un proyecto independiente. Cada una tiene su propio `readme.txt` con los pasos de ejecución y un `conclusiones.txt` con los hallazgos.

| Carpeta | Ejercicio | Herramienta | Estado de la última ejecución |
|---|---|---|---|
| [01-automatizacion-web-serenity](01-automatizacion-web-serenity) | Prueba técnica · Ejercicio 1: compra E2E como invitado en OpenCart | Serenity BDD 4 + Screenplay + Cucumber (Java 11, Gradle) | ✅ 1/1 escenario |
| [02-automatizacion-api-karate](02-automatizacion-api-karate) | Prueba técnica · Ejercicio 2: CRUD de usuarios en la API PetStore | Karate 1.4.1 (Java 11, Gradle) | ✅ 6/6 escenarios |
| [03-performance-k6/ejercicio-1-carga-login](03-performance-k6/ejercicio-1-carga-login) | Performance · Ejercicio 1: prueba de carga del login (≥ 20 TPS) | k6 | ✅ Todos los criterios: 21,96 TPS, p95 441 ms, 0,03 % de errores |
| [03-performance-k6/ejercicio-2-analisis-resultados](03-performance-k6/ejercicio-2-analisis-resultados) | Performance · Ejercicio 2: análisis de resultados | Informe Word | `InformeResultados.docx` |

## Requisitos generales

- JDK 11 o superior (`java -version`)
- Google Chrome (para el proyecto de Serenity)
- k6 (para la prueba de carga): <https://grafana.com/docs/k6/latest/set-up/install-k6/>
- No hace falta instalar Gradle: cada proyecto Java incluye el wrapper (`gradlew` / `gradlew.bat`).

## Ejecución rápida

```bash
# 1. E2E web (OpenCart)
cd 01-automatizacion-web-serenity
./gradlew clean test            # Windows: gradlew.bat clean test
# Reporte: target/site/serenity/index.html

# 2. API (PetStore)
cd ../02-automatizacion-api-karate
./gradlew clean test            # Windows: gradlew.bat clean test
# Reporte: build/karate-reports/karate-summary.html

# 3. Carga (login FakeStore)
cd ../03-performance-k6/ejercicio-1-carga-login
k6 run src/login-load-test.js
# Reportes: reports/
```

## Buenas prácticas aplicadas

- **Screenplay** en la automatización web: tareas de negocio, interacciones, preguntas y localizadores en capas separadas. Nombres en el lenguaje del dominio.
- **Datos fuera del código**: tablas Gherkin, generadores de datos únicos, CSV para la carga y configuración externa (`serenity.conf`, `karate-config.js`, variables de entorno de k6).
- **Pruebas independientes y repetibles**: datos únicos en cada ejecución y limpieza automática en la API, lo que permite ejecutar en paralelo.
- **Esperas explícitas** y reintentos por condición, nunca pausas fijas (`Thread.sleep`).
- **Criterios de aceptación como código**: thresholds de k6 y validaciones de contrato con esquemas JSON en Karate.
- **Versiones fijadas y centralizadas** (`gradle.properties`, URLs de librerías con versión).
- **Reportes incluidos** de la última ejecución en la carpeta `reportes/` de cada proyecto.
