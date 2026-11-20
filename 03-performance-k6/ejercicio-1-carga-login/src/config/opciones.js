import {
  DURACION_RAMPA,
  DURACION_SOSTENIDA,
  TASA_ERROR_MAXIMA,
  TIEMPO_RESPUESTA_MAXIMO_MS,
  TPS_MINIMO,
  TPS_OBJETIVO,
} from './configuracion.js';

/**
 * Modelo de carga abierto (arrival-rate): k6 inyecta un número fijo de peticiones por segundo
 * sin importar cuánto tarde el servidor. Así se garantiza el throughput (TPS) exigido,
 * a diferencia de un modelo cerrado por VUs, donde el TPS cae si el servicio se degrada.
 */
export const opciones = {
  scenarios: {
    carga_login: {
      executor: 'ramping-arrival-rate',
      startRate: 1,
      timeUnit: '1s',
      preAllocatedVUs: 30,
      maxVUs: 100,
      stages: [
        { target: TPS_OBJETIVO, duration: DURACION_RAMPA },      // calentamiento
        { target: TPS_OBJETIVO, duration: DURACION_SOSTENIDA },  // carga sostenida
        { target: 0, duration: '10s' },                          // descenso
      ],
      tags: { prueba: 'carga-login' },
    },
  },
  thresholds: {
    // Tiempo de respuesta: el máximo absoluto y los percentiles altos no deben superar 1,5 s
    http_req_duration: [`max<=${TIEMPO_RESPUESTA_MAXIMO_MS}`, `p(95)<${TIEMPO_RESPUESTA_MAXIMO_MS}`],
    // Tasa de error < 3 %
    http_req_failed: [`rate<${TASA_ERROR_MAXIMA}`],
    // Throughput: al menos 20 TPS en promedio (incluye rampa y descenso, que lo bajan)
    'http_reqs{escenario:carga_login}': [`rate>=${TPS_MINIMO}`],
    // Validaciones funcionales de cada respuesta
    checks: [`rate>${1 - TASA_ERROR_MAXIMA}`],
  },
  summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(90)', 'p(95)', 'p(99)'],
  noConnectionReuse: false,
  userAgent: 'k6-prueba-carga-login/1.0',
};
