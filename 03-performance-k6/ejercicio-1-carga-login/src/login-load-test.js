import exec from 'k6/execution';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/2.4.0/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.2/index.js';
import { opciones } from './config/opciones.js';
import { usuarioParaIteracion } from './datos/usuarios.js';
import { iniciarSesion } from './servicios/login.js';

export const options = opciones;

export default function () {
  const usuario = usuarioParaIteracion(exec.scenario.iterationInTest);
  iniciarSesion(usuario);
}

/**
 * Genera los reportes al finalizar: HTML navegable, JSON completo y el resumen en texto.
 * Las rutas son relativas a la carpeta desde donde se ejecuta k6 (raíz del ejercicio).
 */
export function handleSummary(data) {
  return {
    'reports/reporte-carga-login.html': htmlReport(data, { title: 'Prueba de carga - Login FakeStore' }),
    'reports/resumen-carga-login.json': JSON.stringify(data, null, 2),
    'reports/resumen-carga-login.txt': textSummary(data, { indent: ' ', enableColors: false }),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}
