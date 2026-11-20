import http from 'k6/http';
import { check } from 'k6';
import { RUTA_LOGIN, TIEMPO_RESPUESTA_MAXIMO_MS, URL_BASE } from '../config/configuracion.js';

const CABECERAS = { 'Content-Type': 'application/json' };
const TIMEOUT_PETICION = '60s'; // equivalente al --max-time 60 del cURL entregado
const PATRON_JWT = /^[\w-]+\.[\w-]+\.[\w-]+$/;

/**
 * Invoca el servicio de login con las credenciales del usuario y valida la respuesta.
 * Se acepta 200 o 201: el contrato real de fakestoreapi responde 201 Created.
 */
export function iniciarSesion(usuario) {
  const cuerpo = JSON.stringify({ username: usuario.user, password: usuario.passwd });

  const respuesta = http.post(`${URL_BASE}${RUTA_LOGIN}`, cuerpo, {
    headers: CABECERAS,
    timeout: TIMEOUT_PETICION,
    tags: { name: 'POST /auth/login', escenario: 'carga_login' },
    responseCallback: http.expectedStatuses(200, 201),
  });

  const exitosa = check(respuesta, {
    'login: status 200/201': (r) => r.status === 200 || r.status === 201,
    'login: devuelve un token JWT': (r) => PATRON_JWT.test(tokenDe(r)),
    [`login: responde en <= ${TIEMPO_RESPUESTA_MAXIMO_MS} ms`]: (r) =>
      r.timings.duration <= TIEMPO_RESPUESTA_MAXIMO_MS,
  });

  if (!exitosa) {
    registrarFallo(usuario, respuesta);
  }

  return respuesta;
}

// Deja trazabilidad de cada fallo (código, error y usuario) para poder analizar su causa
function registrarFallo(usuario, respuesta) {
  console.warn(
    `Login fallido | usuario=${usuario.user} | status=${respuesta.status} | ` +
    `error=${respuesta.error || '-'} | duracion=${respuesta.timings.duration.toFixed(0)} ms`
  );
}

function tokenDe(respuesta) {
  try {
    return respuesta.json('token') || '';
  } catch (error) {
    return '';
  }
}
