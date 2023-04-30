Feature: El Api proporciona el servicio de agregar un estado dado el id del pedido y un nuevo estado

  Scenario: Yo soy un usuario autenticado en el servidor
    Quiero agregar un nuevo estado a un pedido enviando su id
    Given Yo soy un usuario que se encuentra autenticado en el sistema
    And En el servidor existe un pedido con id "123"
    When Hago el llamado del servicio de la Api agregar estado y le envio el estado
    Then Recibo un estado 200