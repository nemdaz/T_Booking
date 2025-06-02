package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import po.BuscarHotelPage;
import po.EscogeHabitacionPage;
import po.ListaHotelesPage;
import po.ReservaHabitacionPage;
import po.TerminaReservaPage;
import utils.Utility;
import utils.UtilDelay;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import base.BaseAppium;
import base.Contexto;
import dto.ReservaDTO;

public class ReservaStepsDefinition implements En {
	
	final BaseAppium apiumBase = new BaseAppium();
	final BuscarHotelPage buscarHotelPage = new BuscarHotelPage();
	final EscogeHabitacionPage escogeHabitacionPage = new EscogeHabitacionPage();
	final ReservaHabitacionPage reservaPage = new ReservaHabitacionPage();
	final TerminaReservaPage reservaFinPage = new TerminaReservaPage();

	public ReservaStepsDefinition() {
		
		Before(()->{
			//apiumBase.startService();
		});
		
		// SCRENARIO
		Given("Iniciamos la aplicación en la patalla de búsqueda.", () -> {
			apiumBase.startDriver();
		});
		
		When("ingresamos el pais o region - {string} - donde hospedarnos.", (String destino) -> {
			UtilDelay.coolDelay(2 * 1000);
			
			Contexto.reservaObj.setDestino(destino);
			buscarHotelPage.destino = destino;
			buscarHotelPage.ingresaDestino();
			
			UtilDelay.coolDelay(1 * 1000);
			buscarHotelPage.seleccionaOpcionesDestino(2);
		});
		
		And("para el rango de fechas {string} y {string}", (String checkin, String checkout) -> {
			UtilDelay.coolDelay(1 * 1000);
			
			Contexto.reservaObj.setFechaIngreso(checkin);
			Contexto.reservaObj.setFechaSalida(checkout);
			
			buscarHotelPage.fecIngreso = checkin;
			buscarHotelPage.fecSalida = checkout;
			buscarHotelPage.seleccionaFechas();
		});
		
		And("que disponga de {int} habitacion para {int} adultos", (Integer cantHabitacion, Integer cantAdultos) -> {
			UtilDelay.coolDelay(1 * 1000);
			
			Contexto.reservaObj.setCantidadAdultos(cantAdultos);
			Contexto.reservaObj.setCantidadHabitaciones(cantHabitacion);
			
			buscarHotelPage.cantHabitacion = cantHabitacion;
			buscarHotelPage.cantAdultos = cantAdultos;
			buscarHotelPage.seleccionaCantidades();
		});
		
		And("que permita niño de edad:", (DataTable tNinos) -> {
			UtilDelay.coolDelay(2 * 1000);
			Contexto.reservaObj.setCantidadNinosEdad(tNinos.asList(Integer.class));
			
			buscarHotelPage.ninosEdad = tNinos.asList(Integer.class);
			buscarHotelPage.seleccionaCantidadNinos();
		});
		
		And("hacemos click en el boton {string}", (String btnBuscar) -> {
			UtilDelay.coolDelay(2 * 1000);
			
			buscarHotelPage.buscamosHoteles();
		});
		
		Then("se muestra la lista de hoteles con al menos {int} resultados cumplen los criterios.", (Integer minCant) -> {
			UtilDelay.coolDelay(2 * 1000);
			
			List<String> resultado = Contexto.listaHoteles.listaResultadoHoteles();
			System.out.println("Valor de no hoteles: " + Contexto.listaHoteles.cantNoHoteles);
			assertTrue( minCant <= resultado.size());
		});
		
		// SCENARIO
		Given("Estamos en la lista de resulado, seleccionamos el resultado {int} de la lista.", (Integer position)->{
			UtilDelay.coolDelay(2 * 1000);
			
			System.out.println("Valor de no hoteles (GIVEN): " + Contexto.listaHoteles.cantNoHoteles);
			Contexto.listaHoteles.seleccionaPos = position;
			Contexto.listaHoteles.seleccionaHotel();
		});
		
		When("nos muestra el detalle del hotel", ()->{
			UtilDelay.coolDelay(1 * 1000);
			
			boolean vistaOk = Contexto.listaHoteles.muestraDetalleHotel();
			assertTrue(vistaOk);
		});
		
		And("seleccionamos alguno de los botones para ver las habitaciones, botones:", (DataTable posibleBotones)->{
			UtilDelay.coolDelay(1 * 1000);
			
			Contexto.listaHoteles.muestraHabitacionesHotel(posibleBotones.asList());
		});
		
		Then("se muestra las habitaciones disponibles y sus precios", ()->{
			UtilDelay.coolDelay(2 * 1000);
			boolean muestra = escogeHabitacionPage.muestraHabitaciones();
			assertTrue(muestra);
		});
		
		And("podemos seleccionar la habitación {int} de la lista para Información", (Integer position)->{
			UtilDelay.coolDelay(2 * 1000);
			escogeHabitacionPage.posicionHabitacion = position;
			Contexto.reservaObj.setCostoPrevio(escogeHabitacionPage.seleccionaHabitacion());
		});
		
		And("en la Información de la habitación se muestra el mismo precio de lista", ()->{
			UtilDelay.coolDelay(5 * 1000);
			Double p = escogeHabitacionPage.muestraInformacionHabitacion();
			assertEquals(Contexto.reservaObj.getCostoPrevio(), p);
			Contexto.reservaObj.setCostoPrevio(p);
		});
		
		And("la sección Reserva muestra el mismo precio que en la Información.", ()->{
			UtilDelay.coolDelay(1 * 1000);
			Double p = escogeHabitacionPage.muestraInformacionReserva();
			assertEquals(Contexto.reservaObj.getCostoPrevio(), p);
			Contexto.reservaObj.setCostoPrevio(p);
		});
		
		// SCENARIO
		Given("Previamente se verifico el detalle de la habitación, presionamos boton {string}", (String btnTexto)->{
			UtilDelay.coolDelay(1 * 1000);
			System.out.println("Costo previo (GIVEN) : " + Contexto.reservaObj.getCostoPrevio());
			reservaPage.iniciamosReserva(btnTexto);
		});
		
		When("Ingresamos nuestros datos en el formulario:", (DataTable datosReserva)->{
			UtilDelay.coolDelay(5 * 1000);
			
			List<String> datos = datosReserva.asList();
			reservaPage.cliNombres = datos.get(0);
			reservaPage.cliApellidos = datos.get(1);
			reservaPage.cliCorreoE = datos.get(2);
			reservaPage.cliPaisRegion = datos.get(3);
			reservaPage.cliNumTelf = datos.get(4);
			reservaPage.cliProposito = datos.get(5);
			
			reservaPage.ingresamosDatosReserva();
			
			Double precioActual = reservaPage.muestraInformacionReserva();
			assertEquals(Contexto.reservaObj.costoPrevio, precioActual);
			Contexto.reservaObj.setCostoPrevio(precioActual);
			
		});
		
		And("Se habilita el boton {string}, continuamos", (String btnTexto)->{
			UtilDelay.coolDelay(1 * 1000);

			reservaPage.comprobamosDetalleReserva(btnTexto);
		});
		
		And("comprobamos el resumen de la reserva y presionamos el boton {string}", (String btnTexto)->{
			UtilDelay.coolDelay(1 * 1000);
			
			reservaPage.comprobamosResumenReserva(btnTexto);
		});
		
		Then("se nos pide ingresar los datos de tarjeta:", (DataTable datosTarjeta)->{
			UtilDelay.coolDelay(4 * 1000);
			
			List<String> datCard = datosTarjeta.asList();
			reservaFinPage.cardNumber = datCard.get(0);
			reservaFinPage.cardPropietario = datCard.get(1);
			reservaFinPage.cardExpira = datCard.get(2);
			reservaFinPage.cardCVC = datCard.get(3);
			
			reservaFinPage.ingresamosDatosTarjeta();
		});
		
		And("al reservar con {string} nos confirma la reserva", (String btnTexto)->{
			UtilDelay.coolDelay(1 * 1000);
			//Verificamos montos			
			
		});
		
		
		After(()->{
			// Acciones finales para cada scenario
		});
		
		// END SERVICE OR DRIVER
		//UtilDelay.coolDelay(50 * 1000);
		//apiumBase.shutDown();
	}
}
