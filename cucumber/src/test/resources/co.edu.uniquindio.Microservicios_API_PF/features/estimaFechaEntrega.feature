Feature: El Api proporciona el servicio de estimar fecha de entrega enviando el id del pedido

  Scenario: Yo soy un usuario validado en el servidor
    Quiero conocer la fecha estima de entrega de un pedido enviando el id del pedido
    Given Soy un usuario que me encuentro autenticado en el sistema
    And Ya existe un pedido en el servidor con id "123"
    When Realizo el llamado al servicio de la Api estimar fecha entrega y le envio el id "123"
    Then Me llega el estado 200
    And Me llega la fecha de llegada del pedido