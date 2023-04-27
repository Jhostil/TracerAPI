Feature: Convertir fecha de entrega a la zona horaria del cliente

  Scenario: Convertir fecha de entrega
    Given que el usuario tiene un pedido con el id "12357"
    When el usuario convierte la fecha de entrega a la zona horaria de "America/Bogota"
    Then el usuario obtiene la nueva fecha de entrega "2023-04-20T15:30"
    And una respuesta http 200