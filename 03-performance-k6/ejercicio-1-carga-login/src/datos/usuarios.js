import { SharedArray } from 'k6/data';
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
// open() resuelve rutas relativas a este módulo: src/datos -> ../../data
const RUTA_CSV = '../../data/usuarios.csv';

// SharedArray carga el CSV una sola vez y lo comparte entre todos los VUs (ahorra memoria)
const usuarios = new SharedArray('usuarios de login', () =>
  papaparse.parse(open(RUTA_CSV), { header: true, skipEmptyLines: true }).data
);

/**
 * Reparte los usuarios en round-robin según el número global de iteración,
 * para que todas las credenciales se usen de forma equilibrada.
 */
export function usuarioParaIteracion(numeroDeIteracion) {
  return usuarios[numeroDeIteracion % usuarios.length];
}

export function cantidadDeUsuarios() {
  return usuarios.length;
}
