@ignore
Feature: Helper - crear un usuario y esperar a que esté disponible para consulta

  Scenario: Crear usuario de precondición
    Given url baseUrl
    And path 'user'
    And request usuario
    When method post
    Then status 200

    # La API es eventualmente consistente: se espera a que la consulta ya lo encuentre
    Given path 'user', usuario.username
    And retry until responseStatus == 200
    When method get
