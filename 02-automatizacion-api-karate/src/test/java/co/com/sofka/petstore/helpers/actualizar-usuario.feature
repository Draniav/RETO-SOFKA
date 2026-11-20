@ignore
Feature: Helper - actualizar un usuario y esperar a que el cambio sea visible

  Scenario: Actualizar usuario de precondición
    Given url baseUrl
    And path 'user', usuarioActualizado.username
    And request usuarioActualizado
    When method put
    Then status 200

    Given path 'user', usuarioActualizado.username
    And retry until responseStatus == 200 && response.firstName == usuarioActualizado.firstName
    When method get
