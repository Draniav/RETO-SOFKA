@ignore
Feature: Helper - limpieza de datos: eliminar el usuario si todavía existe

  Scenario: Eliminar usuario de prueba
    Given url baseUrl
    And path 'user', username
    # 404 significa que ya no existe (por ejemplo, porque el escenario lo eliminó)
    And retry until responseStatus == 200 || responseStatus == 404
    When method delete
