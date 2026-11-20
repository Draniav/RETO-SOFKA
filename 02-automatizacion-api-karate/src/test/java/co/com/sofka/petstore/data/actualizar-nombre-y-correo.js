function actualizarNombreYCorreo(usuario) {
  // Solo cambian nombre y correo; el resto del recurso se conserva (PUT reemplaza el recurso completo)
  return karate.merge(usuario, {
    firstName: 'Laura',
    email: 'laura.' + usuario.username + '@actualizado-sofka.test'
  });
}
