Feature: Realizar reserva de habitación en Booking.com.
  La reserva se reaiza para la ciudad Cusco.

  Scenario: Buscamos hotel.
    Given Iniciamos la aplicación en la patalla de búsqueda.
    When ingresamos el pais o region - "Cusco" - donde hospedarnos.
    And para el rango de fechas "15/02/2023" y "28/02/2023" 
    And que disponga de 1 habitacion para 2 adultos
    And que permita niño de edad:
    	| 5 |
    And hacemos click en el boton "Buscar"
    Then se muestra la lista de hoteles con al menos 2 resultados cumplen los criterios.
    
  Scenario: Seleccionamos hotel y escojemos habitacion.
  	Given Estamos en la lista de resulado, seleccionamos el resultado 2 de la lista.
  	When nos muestra el detalle del hotel
  	And seleccionamos alguno de los botones para ver las habitaciones, botones:
  		|"Ver tus opciones"|
  		|"Elige habitación"|
  	Then se muestra las habitaciones disponibles y sus precios
  	And podemos seleccionar la habitación 1 de la lista para Información
  	And en la Información de la habitación se muestra el mismo precio de lista
  	And la sección Reserva muestra el mismo precio que en la Información.
  	
  Scenario: Reservar y pagar
  	Given Previamente se verifico el detalle de la habitación, presionamos boton "Reserva ahora"
  	When Ingresamos nuestros datos en el formulario:
  		|Juan|
  		|Cardenas|
  		|jcard@test.info|
  		|Perú|
  		|987654321|
  		|Ocio|
  	And Se habilita el boton "Siguiente paso", continuamos
  	And comprobamos el resumen de la reserva y presionamos el boton "Último paso"
  	Then se nos pide ingresar los datos de tarjeta:
  		|4555788765443333|
  		|Maria Rosa Garcia Garcia|
  		|02/25|
  		|000|
  	And al reservar con "Reservar ahora" nos confirma la reserva
  	
