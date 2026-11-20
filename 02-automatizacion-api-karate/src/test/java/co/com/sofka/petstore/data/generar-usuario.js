function generarUsuario() {
  // Datos únicos por ejecución para que las pruebas sean independientes y repetibles
  var sufijo = java.util.UUID.randomUUID().toString().substring(0, 8);
  var id = Math.floor(Math.random() * 900000000) + 100000000;
  return {
    id: id,
    username: 'qa_sofka_' + sufijo,
    firstName: 'Ana',
    lastName: 'Gomez',
    email: 'ana.gomez.' + sufijo + '@pruebas-sofka.test',
    password: 'Clave#2026',
    phone: '3001234567',
    userStatus: 1
  };
}
