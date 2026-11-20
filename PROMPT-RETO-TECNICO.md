# Prompt base del reto técnico de automatización

Este documento contiene el **prompt maestro** que describe cómo construir los tres proyectos del reto (web con Serenity BDD, API con Karate y performance con k6), con las pautas de calidad, las buenas prácticas y los criterios de aceptación de cada uno. Al final hay un **análisis** de por qué el prompt está construido así y con qué normas y prácticas de QA se relaciona cada parte.

El prompt es único, pero cada proyecto tiene su propia sección con requisitos distintos: patrón de diseño, herramienta, tipo de validación y entregables.

---

## 1. Prompt maestro

> Copie todo el bloque siguiente como un único mensaje.

```text
# ROL
Actúa como un Ingeniero de Automatización de Pruebas (QA Automation) senior, con experiencia
en Serenity BDD, Karate y k6, y con dominio de los principios de ISTQB, Clean Code, SOLID y BDD.
Tu objetivo es entregar código de pruebas mantenible, legible, reproducible y listo para revisión.

# CONTEXTO
Estoy resolviendo un reto técnico para el cargo de Analista de Automatización de Pruebas.
El evaluador revisará: lógica, estructura de pensamiento, buenas prácticas y que cualquier persona
pueda reproducir la ejecución desde un repositorio público.
Entorno disponible: Windows 11, JDK 11, Gradle 7.6.3, Google Chrome, k6. Sin Maven.
Restricción: no inicialices repositorios git ni hagas commits; solo crea los proyectos y archivos.

Hay tres proyectos independientes, cada uno en su propia carpeta dentro de "reto-tecnico-sofka":
  01-automatizacion-web-serenity   -> Prueba E2E web (OpenCart)
  02-automatizacion-api-karate     -> Pruebas de servicios REST (PetStore)
  03-performance-k6                -> Prueba de carga (FakeStore login) + análisis de resultados

# PAUTAS GENERALES (aplican a los 3 proyectos)

## Antes de escribir código
1. Sondea el sistema bajo prueba antes de automatizar: verifica con curl o en el navegador
   que las URL responden, el contrato real de cada servicio (códigos de estado y cuerpo) y los
   selectores reales del HTML. No asumas el contrato por la documentación; si difieren,
   documenta la diferencia como hallazgo.
2. Identifica precondiciones, datos de prueba, resultado esperado y criterios de aceptación de
   cada caso antes de implementarlo.

## Diseño y Clean Code
3. Separa responsabilidades: cada clase, archivo o módulo tiene una sola razón para cambiar
   (principio de responsabilidad única).
4. Nombra todo en el lenguaje del dominio y en español: clases como acciones de negocio
   (AgregarAlCarrito), métodos que revelan su intención, sin abreviaturas ni nombres genéricos.
5. Nada de valores mágicos: URL, tiempos de espera, umbrales y datos van en configuración o en
   constantes con nombre, en un solo lugar.
6. Datos de prueba fuera de la lógica: tablas Gherkin, archivos JSON/JS/CSV o generadores.
7. Funciones pequeñas, sin duplicación (DRY): lo que se repite se extrae a una tarea, un helper o
   un módulo reutilizable.
8. Comentarios solo para explicar el "por qué" de una decisión no obvia, nunca el "qué".
9. Inmutabilidad cuando aplique (objetos de datos con Builder o copias, no mutación).

## Calidad de las pruebas
10. Pruebas independientes y repetibles: cada prueba crea sus propios datos (identificadores
    únicos por ejecución), no depende del orden ni de otra prueba, y limpia lo que crea.
11. Prohibido Thread.sleep o pausas fijas: usa esperas explícitas por condición o reintentos
    con condición de salida.
12. Validaciones significativas: código de estado, contenido, contrato o estructura y, cuando
    aplique, el estado posterior (por ejemplo, que un recurso eliminado ya no exista).
13. Una prueba que falla debe decir claramente qué esperaba y qué obtuvo.
14. Los criterios de aceptación se expresan como código (aserciones, esquemas, thresholds) para
    que la herramienta decida el resultado, no una revisión manual.

## Configuración, ejecución y evidencia
15. Versiones fijadas y centralizadas (gradle.properties, versiones exactas de librerías).
16. Incluye Gradle wrapper para ejecutar sin instalar Gradle.
17. Configuración por ambiente externa al código (serenity.conf, karate-config.js, variables de
    entorno en k6).
18. Cada proyecto genera un reporte navegable (HTML) y se incluye una copia de la última
    ejecución en el entregable.
19. Ejecuta cada proyecto al terminar y corrige hasta que pase. Si algo no se puede verificar,
    dilo explícitamente.

## Documentación obligatoria por proyecto
20. readme.txt: descripción, tecnologías con versiones exactas, estructura de carpetas, pasos de
    ejecución paso a paso (Windows y Linux/macOS) y ubicación del reporte.
21. conclusiones.txt: resultado de la ejecución, hallazgos (defectos, riesgos, comportamientos
    inesperados, contrato real vs. documentado), decisiones de diseño y recomendaciones.
22. .gitignore para build, target, .gradle y archivos del IDE.
23. README.md en la raíz con el índice de los proyectos, el estado de cada uno y la ejecución rápida.

# PROYECTO 1: PRUEBA E2E WEB CON SERENITY BDD (01-automatizacion-web-serenity)

Objetivo: automatizar el flujo de compra en http://opencart.abstracta.us/
  - Agregar dos productos al carrito
  - Visualizar el carrito y validar que contiene los dos productos
  - Completar el checkout como invitado ("Guest Checkout")
  - Finalizar la compra y validar el mensaje "Your order has been placed!"

Requisitos técnicos:
  - Serenity BDD 4.x + Cucumber + JUnit 4, Java 11, Gradle con el plugin de Serenity.
  - Patrón Screenplay obligatorio, con paquetes:
      runners, stepdefinitions, tasks, interactions, questions, userinterfaces, models, utils
  - Feature en Gherkin en español ("# language: es"), escrito en lenguaje de negocio, con
    Antecedentes, tags (@e2e, @smoke) y los datos del comprador en una tabla.
  - Step definitions delgados: solo delegan en tareas y validan con preguntas (seeThat).
  - Tasks de negocio compuestas (una tarea de alto nivel que encadena subtareas del checkout).
  - Localizadores como Target con nombre legible, agrupados por página; plantillas
    parametrizadas cuando el elemento depende de un dato (producto por nombre exacto).
  - Una interacción propia que espera a que el elemento sea clicable antes de hacer clic, porque
    el checkout carga sus pasos por AJAX.
  - Modelo inmutable del comprador con Builder, convertido desde la tabla con @DataTableType.
  - Correo único por ejecución para que la prueba sea repetible.
  - serenity.conf con Chrome, opción headless, timeouts y URL base por ambiente.
  - El reporte se genera aunque haya fallos (test -> aggregate).
Criterio de terminado: el escenario pasa en headless y el reporte muestra capturas por paso.

# PROYECTO 2: PRUEBAS DE API CON KARATE (02-automatizacion-api-karate)

Objetivo: probar el recurso User de https://petstore.swagger.io/v2
  - Crear un usuario (POST /user)
  - Buscar el usuario creado (GET /user/{username})
  - Actualizar el nombre y el correo (PUT /user/{username})
  - Buscar el usuario actualizado
  - Eliminar el usuario (DELETE /user/{username}) y verificar que ya no existe (404)
Identifica entradas, salidas, variables y validaciones de cada caso.

Requisitos técnicos:
  - Karate compatible con Java 11 (1.4.1), runner JUnit 5 que ejecuta en paralelo y falla si
    algún escenario falla.
  - Un escenario independiente por operación más un escenario @e2e con el ciclo de vida completo.
  - Tags por caso para ejecución selectiva.
  - karate-config.js con URL base, ruta de recursos, timeouts y política de reintentos.
  - Datos generados con funciones JS (username e id únicos); la actualización cambia solo nombre
    y correo partiendo del usuario original (PUT reemplaza el recurso completo).
  - Precondiciones en features reutilizables marcados @ignore (crear, actualizar, eliminar).
  - Limpieza automática con afterScenario, aunque el escenario falle.
  - Validación de contrato con esquemas JSON y de valores exactos (message = id o username).
  - La API pública es eventualmente consistente: usa "retry until" con condición, nunca pausas.
Criterio de terminado: todos los escenarios pasan en paralelo y el reporte HTML muestra
request y response de cada paso.

# PROYECTO 3: PERFORMANCE CON K6 (03-performance-k6)

## Ejercicio 1 (ejercicio-1-carga-login)
Objetivo: prueba de carga de POST https://fakestoreapi.com/auth/login
  - Parametrizar user,passwd desde data/usuarios.csv
  - Alcanzar al menos 20 TPS
  - Tiempo de respuesta máximo de 1,5 s
  - Tasa de error menor al 3 %

Requisitos técnicos:
  - Modelo de carga abierto (ramping-arrival-rate) para garantizar el TPS: rampa, carga
    sostenida por encima del mínimo (25 TPS) y descenso.
  - Criterios de aceptación como thresholds (max y p95 de duración, http_req_failed, tasa de
    http_reqs, checks) para que k6 termine con código distinto de 0 si no se cumplen.
  - Checks por respuesta: status, token JWT válido y duración.
  - Código modular: config (parámetros y opciones), datos (CSV con SharedArray y reparto
    round-robin), servicios (llamada y validaciones) y script principal.
  - Parámetros sobrescribibles con -e CLAVE=valor.
  - Registro en consola de cada fallo (usuario, status, error, duración) para trazabilidad.
  - handleSummary con reporte HTML, JSON y TXT en reports/, y opción de dashboard web.
  - Ejecuta la prueba completa y redacta conclusiones.txt con los resultados reales en una tabla
    de criterio esperado vs. obtenido.

## Ejercicio 2 (ejercicio-2-analisis-resultados)
Objetivo: analizar el textSummary y el diagrama de VUs vs. peticiones por segundo entregados y
producir InformeResultados (Word) con:
  - Resumen ejecutivo con veredicto
  - Contexto de la ejecución (duración, VUs, etapas, modelo de carga) derivado de los datos
  - Tabla de métricas clave con su lectura
  - Análisis de tiempos (TTFB vs. red, media vs. mediana, percentiles, máximos)
  - Análisis de errores por etapa y tipo (4xx/5xx)
  - Lectura del diagrama por tramos de tiempo y relación VUs-throughput (Ley de Little)
  - Conclusiones y recomendaciones accionables
  - Anexos con las evidencias
Cada cifra derivada debe mostrar cómo se calculó.

# FORMATO DE ENTREGA
- Crea los archivos completos (no fragmentos) en la estructura indicada.
- Al terminar cada proyecto, ejecútalo y reporta el resultado real.
- Entrega un resumen final con: qué se construyó, resultado de cada ejecución, hallazgos
  principales y qué queda pendiente o sin verificar.
```

---

## 2. Qué cambia entre los tres proyectos

El prompt aplica las mismas pautas generales a todos, pero cada proyecto las resuelve con su propio diseño:

| Aspecto | 01 · Web (Serenity) | 02 · API (Karate) | 03 · Performance (k6) |
|---|---|---|---|
| Nivel de prueba | Sistema / E2E de interfaz | Integración de servicios | No funcional: carga |
| Patrón de diseño | Screenplay (actor, tareas, preguntas) | Features + helpers reutilizables | Módulos ES con responsabilidad única |
| Lenguaje | Java + Gherkin en español | DSL de Karate + JavaScript | JavaScript (k6) |
| Datos de prueba | Tabla Gherkin → objeto inmutable | Generadores JS con UUID | CSV compartido con SharedArray |
| Independencia | Correo único por ejecución | Usuario único + limpieza en afterScenario | Reparto round-robin de credenciales |
| Sincronización | Esperas explícitas (WaitUntil) | `retry until` por condición | Modelo abierto (arrival-rate) |
| Oráculo / validación | Preguntas + matchers de Hamcrest | Status, esquemas JSON y valores | Checks + thresholds |
| Evidencia | Reporte Serenity con capturas | Reporte Karate con request/response | HTML, JSON, TXT y dashboard |
| Ejecución en paralelo | No (un escenario E2E) | Sí, 3 hilos | Concurrencia gestionada por los VUs |

---

## 3. Análisis del prompt

### 3.1 Técnicas de ingeniería de prompts usadas

| Técnica | Dónde está | Para qué sirve |
|---|---|---|
| **Rol** | `# ROL` | Fija el nivel de experiencia y el criterio técnico esperado (senior, ISTQB, Clean Code). |
| **Contexto** | `# CONTEXTO` | Explica quién evalúa y qué valora, el entorno disponible (Java 11, Gradle, sin Maven) y las restricciones (sin git). Evita elegir versiones o herramientas incompatibles. |
| **Instrucciones generales + específicas** | Pautas generales y una sección por proyecto | Las reglas comunes no se repiten; cada proyecto solo agrega lo propio. |
| **Restricciones explícitas** | "Prohibido Thread.sleep", "no asumas el contrato", "sin git" | Las restricciones negativas evitan los errores más comunes en automatización. |
| **Criterios de terminado** | "Criterio de terminado" en cada proyecto | Define cuándo el trabajo está completo y verificable. |
| **Verificación obligatoria** | Pautas 19 y formato de entrega | Obliga a ejecutar y a reportar resultados reales, no supuestos. |
| **Formato de salida** | `# FORMATO DE ENTREGA` | Archivos completos, estructura fija y resumen final con pendientes. |

### 3.2 Relación con normas y prácticas de QA

| Pauta del prompt | Norma o práctica de QA |
|---|---|
| Sondear el sistema y el contrato antes de automatizar (1, 2) | ISTQB: análisis y diseño de pruebas antes de la implementación; identificar base de prueba, precondiciones y resultado esperado. |
| Criterios de aceptación como código (14), thresholds, esquemas | ISTQB: oráculo de prueba explícito. ISO/IEC/IEEE 29119-3: criterios de paso/fallo definidos. |
| Pruebas independientes, repetibles y con limpieza (10) | Principios FIRST (Fast, Independent, Repeatable, Self-validating, Timely). |
| Esperas por condición, sin pausas fijas (11) | Prevención de pruebas inestables (flaky tests); buena práctica de Selenium y de pruebas de API. |
| Validación del estado posterior (12) | ISTQB: verificar resultados reales contra esperados, incluidos efectos secundarios (recurso eliminado → 404). |
| Gherkin en lenguaje de negocio, tags, Antecedentes | BDD: especificación por ejemplos, documentación viva y trazabilidad con la historia de usuario. |
| Screenplay, helpers, módulos (3, 7) | SOLID (responsabilidad única, abierto/cerrado), DRY y mantenibilidad (ISO/IEC 25010: mantenibilidad, modularidad, reutilización). |
| Nombres en el lenguaje del dominio, sin valores mágicos (4, 5) | Clean Code (Robert C. Martin): nombres que revelan intención, constantes con nombre. |
| Validación de contrato con esquemas JSON | Pruebas de contrato de API; ISO/IEC 25010: compatibilidad e interoperabilidad. |
| Prueba de carga con TPS, percentiles y tasa de error | ISO/IEC 25010: eficiencia de desempeño (comportamiento temporal, capacidad). ISTQB Performance Testing: perfiles de carga, modelo abierto vs. cerrado, métricas de percentiles. |
| Análisis con Ley de Little, TTFB, errores por etapa | Análisis de resultados de rendimiento según ISTQB Performance Testing: correlacionar throughput, concurrencia y tiempos para encontrar cuellos de botella. |
| readme, conclusiones, reportes con evidencia (18, 20, 21) | ISO/IEC/IEEE 29119-3: documentación de pruebas (informe de ejecución, registro de incidencias). ISTQB: el informe de pruebas comunica resultados, riesgos y recomendaciones. |
| Hallazgos: defectos, riesgos y contrato real vs. documentado | ISTQB: reporte de defectos objetivo y reproducible; las pruebas también detectan riesgos, no solo fallos. |
| Versiones fijadas, wrapper, configuración externa (15-17) | Reproducibilidad del entorno de pruebas y preparación para integración continua. |
| Código de salida distinto de 0 si no se cumplen los criterios | Pruebas como compuerta de calidad en un pipeline CI/CD (shift-left). |

### 3.3 Por qué el prompt produce proyectos distintos

- **El tipo de prueba cambia la arquitectura.** Una prueba E2E de interfaz necesita modelar al usuario y la página (Screenplay). Una prueba de API necesita modelar peticiones, contratos y datos (features, esquemas y helpers). Una prueba de carga necesita modelar tráfico y criterios estadísticos (escenarios y thresholds).
- **El tipo de inestabilidad cambia la estrategia de sincronización.** En la web la causa es el AJAX y las animaciones (esperas explícitas). En la API es la consistencia eventual entre réplicas (`retry until`). En carga, el throughput depende del tiempo de respuesta (modelo abierto).
- **El oráculo cambia según lo que se mide.** En la web es lo que ve el usuario. En la API es el contrato y el estado del recurso. En performance son métricas agregadas contra umbrales.

### 3.4 Checklist de verificación del prompt

Use esta lista para revisar que cada proyecto cumple lo que pide el prompt:

- [ ] Se sondeó el sistema real antes de automatizar y las diferencias con la documentación quedaron como hallazgo.
- [ ] Ninguna prueba depende de otra ni del orden de ejecución.
- [ ] No hay `Thread.sleep` ni pausas fijas.
- [ ] No hay URL, tiempos ni datos quemados en la lógica.
- [ ] Cada clase o archivo tiene una sola responsabilidad y un nombre del dominio.
- [ ] Los criterios de aceptación están como aserciones, esquemas o thresholds.
- [ ] Cada proyecto se ejecutó y su resultado real está en `conclusiones.txt`.
- [ ] Cada proyecto tiene `readme.txt`, `conclusiones.txt`, reporte y `.gitignore`.
- [ ] Las versiones están fijadas y el proyecto se puede ejecutar desde cero con los pasos del readme.
