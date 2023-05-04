Feature: La API proporciona el servicio para buscar la información
  de la transportadora encargada de entregar un pedido

  Scenario: Yo como usuario con sesión activa en el sistema
  Quiero buscar la informacion de la empresa que transporta un pedido
  Para encontrar su información detallada
    Given Soy un usuario  autenticado en el sistema
    And  existe un pedido con el id "123"
    When invoco el servicio de busqueda de la empresa transportadora ingresando el id "123"
    Then obtengo un codigo http 200
    And la información de la transportadora