Feature: Pruebas para el controlador de tokens de autenticación

  Scenario: Generar un token de autenticación válido
    Given que tengo credenciales de usuario válidas
    When envío una solicitud POST al endpoint de generacion con las credenciales de usuario
    Then la respuesta debe contener un token de autenticación válido

  Scenario: Obtener el sujeto de un token de autenticación válido
    Given que tengo un token de autenticación válido
    When envío una solicitud GET al endpoint sujeto con el token de autenticación
    Then la respuesta debe contener el sujeto del token de autenticación