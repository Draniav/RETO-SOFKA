// Parámetros de la prueba. Se pueden sobrescribir por variables de entorno (-e CLAVE=valor)
// sin tocar el código, p. ej.: k6 run -e TPS_OBJETIVO=30 -e DURACION_SOSTENIDA=5m src/login-load-test.js

export const URL_BASE = __ENV.URL_BASE || 'https://fakestoreapi.com';
export const RUTA_LOGIN = '/auth/login';

export const TPS_OBJETIVO = Number(__ENV.TPS_OBJETIVO || 25); // > 20 TPS exigidos, con margen
export const DURACION_RAMPA = __ENV.DURACION_RAMPA || '30s';
export const DURACION_SOSTENIDA = __ENV.DURACION_SOSTENIDA || '2m';

export const TIEMPO_RESPUESTA_MAXIMO_MS = 1500; // criterio: máximo 1,5 s
export const TASA_ERROR_MAXIMA = 0.03;          // criterio: < 3 % de las peticiones
export const TPS_MINIMO = 20;                    // criterio: al menos 20 TPS
