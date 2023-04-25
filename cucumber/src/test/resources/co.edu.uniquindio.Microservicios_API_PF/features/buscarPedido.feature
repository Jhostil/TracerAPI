Feature: La API proporciona el servicio para buscar un envio dado si identificador

  Scenario: Yo como usuario con sesión activa en el sistema
  Quiero buscar un envio ingresando su identificador
  Para encontrar su información detallada
    Given Soy un usuario que estoy autenticado en el sistema
    And  existe un pedido con el identificador "123"
    When invoco el servicio de busqueda de envios ingresando el id "123"
    Then obtengo un status code 200
    And la información del envio