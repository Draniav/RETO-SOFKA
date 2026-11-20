@petstore @usuarios
Feature: Gestión de usuarios en la API de PetStore
  Como consumidor de la API de PetStore
  Quiero crear, consultar, actualizar y eliminar usuarios
  Para administrar las cuentas de la tienda

  Background:
    * url baseUrl
    * def generarUsuario = read(rutaBase + 'data/generar-usuario.js')
    * def actualizarNombreYCorreo = read(rutaBase + 'data/actualizar-nombre-y-correo.js')
    * def usuarioSchema = read(rutaBase + 'schemas/usuario-schema.json')
    * def respuestaApiSchema = read(rutaBase + 'schemas/respuesta-api-schema.json')
    * def crearUsuario = rutaBase + 'helpers/crear-usuario.feature'
    * def actualizarUsuario = rutaBase + 'helpers/actualizar-usuario.feature'
    * def eliminarUsuario = rutaBase + 'helpers/eliminar-usuario.feature'

    * def usuario = generarUsuario()
    # Cada escenario deja el ambiente limpio, pase o falle
    * configure afterScenario = function(){ karate.call(eliminarUsuario, { username: usuario.username }) }

  @crear
  Scenario: Crear un usuario
    Given path 'user'
    And request usuario
    When method post
    Then status 200
    And match response == respuestaApiSchema
    And match response.code == 200
    And match response.message == usuario.id + ''

  @consultar
  Scenario: Buscar el usuario creado
    * call read(crearUsuario) { usuario: '#(usuario)' }

    Given path 'user', usuario.username
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response == usuarioSchema
    And match response == usuario

  @actualizar
  Scenario: Actualizar el nombre y el correo del usuario
    * call read(crearUsuario) { usuario: '#(usuario)' }
    * def usuarioActualizado = actualizarNombreYCorreo(usuario)

    Given path 'user', usuario.username
    And request usuarioActualizado
    When method put
    Then status 200
    And match response == respuestaApiSchema
    And match response.message == usuario.id + ''

  @consultar_actualizado
  Scenario: Buscar el usuario actualizado
    * call read(crearUsuario) { usuario: '#(usuario)' }
    * def usuarioActualizado = actualizarNombreYCorreo(usuario)
    * call read(actualizarUsuario) { usuarioActualizado: '#(usuarioActualizado)' }

    Given path 'user', usuario.username
    And retry until responseStatus == 200 && response.firstName == usuarioActualizado.firstName
    When method get
    Then status 200
    And match response == usuarioSchema
    And match response.firstName == usuarioActualizado.firstName
    And match response.email == usuarioActualizado.email
    And match response.lastName == usuario.lastName

  @eliminar
  Scenario: Eliminar el usuario
    * call read(crearUsuario) { usuario: '#(usuario)' }

    Given path 'user', usuario.username
    And retry until responseStatus == 200
    When method delete
    Then status 200
    And match response == respuestaApiSchema
    And match response.message == usuario.username

    # El usuario eliminado ya no debe poder consultarse
    Given path 'user', usuario.username
    And retry until responseStatus == 404
    When method get
    Then status 404
    And match response.message == 'User not found'

  @e2e
  Scenario: Ciclo de vida completo de un usuario: crear, consultar, actualizar, consultar y eliminar
    # 1. Crear
    Given path 'user'
    And request usuario
    When method post
    Then status 200
    And match response.message == usuario.id + ''

    # 2. Buscar el usuario creado
    Given path 'user', usuario.username
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response == usuario

    # 3. Actualizar nombre y correo
    * def usuarioActualizado = actualizarNombreYCorreo(usuario)
    Given path 'user', usuario.username
    And request usuarioActualizado
    When method put
    Then status 200
    And match response.message == usuario.id + ''

    # 4. Buscar el usuario actualizado
    Given path 'user', usuario.username
    And retry until responseStatus == 200 && response.firstName == usuarioActualizado.firstName
    When method get
    Then status 200
    And match response == usuarioActualizado

    # 5. Eliminar el usuario
    Given path 'user', usuario.username
    And retry until responseStatus == 200
    When method delete
    Then status 200
    And match response.message == usuario.username
