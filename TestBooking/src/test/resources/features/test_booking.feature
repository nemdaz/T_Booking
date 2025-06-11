Feature: Reserva de habitación exitosa en Booking.com
  Quiero realizar una reserva completa de habitación.

Scenario Outline: Reserva completa de habitación para diferentes ciudades y datos de reserva
    Given El usuario inicia la aplicación y omite pantallas emergentes hasta la pantalla principal de búsqueda

    When busca hotel en "<ciudad>" para las fechas "<fecha_inicio>" a "<fecha_fin>" con <cant_habi> habitación(es) para <cant_adul> adulto(s) y <cant_nino> niño(s) de "<edad_ninos>" años
    And solicita la búsqueda
    Then se muestran al menos 2 hoteles que cumplen los criterios

    When selecciona el segundo hotel de la lista
    And presiona el botón "Ver tus opciones" o "Elige habitación" para ver las habitaciones disponibles
    And selecciona la primera habitación para ver información detallada
    Then el precio mostrado es consistente en la lista, la información de la habitación y la sección de reserva

    When inicia la reserva de la habitación seleccionada
    And completa el formulario con los siguientes datos:
      | Nombre    | Apellido   | Email            | País | Teléfono    | Motivo     |
      | <nombre>  | <apellido> | <email>          | <pais>| <telefono> | <motivo>   |
    And avanza al resumen y confirma el último paso
    Then la aplicación solicita los datos de tarjeta:
      | Número de tarjeta    | Nombre en tarjeta          | Vencimiento | CVV |
      | 4555788765443333     | Maria Rosa Garcia Garcia   | 02/25       | 000 |
    When confirma la reserva con los datos de pago
    Then la aplicación muestra la confirmación exitosa de la reserva

    # Nota: Si no hay niños, deja 'edad_ninos' vacío
    Examples:
      | ciudad   | fecha_inicio | fecha_fin   | cant_habi | cant_adul | cant_nino | edad_ninos | nombre  | apellido | email              | pais  | telefono   | motivo     |
      | Cusco    | 15/02/2023   | 28/02/2023  | 1         | 2         | 1         | 5          | Juan    | Cardenas | jcard@test.info    | Perú  | 987654321  | Ocio       |