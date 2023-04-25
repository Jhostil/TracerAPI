Feature: La API proporciona el servicio para mostrar la trayectoria de un envio

  Scenario: Yo como usuario con sesión activa en el sistema
  quiero ver la trayectoria de un envio ingresando su identificado
    Given Soy un usuario que estoy con sesion activa en el sistema
    And  hay un pedido con el identificador "123"
    When invoco el servicio que permite ver la trayectoria de un pedido ingresando el id "123"
    Then obtengo un codigo de estado 200
    And un arreglo con las ubicaciones del pedido