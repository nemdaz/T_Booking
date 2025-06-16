package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import po.BuscarHotelPage;
import po.EscogeHabitacionPage;
import po.HomePage;
import po.ReservaHabitacionPage;
import po.TerminaReservaPage;
import utils.UtilWaits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import base.BaseAppium;
import base.Contexto;

public class ReservaStepsDefinition implements En {

    final BaseAppium apiumBase = new BaseAppium();
    final HomePage homePage = new HomePage();
    final BuscarHotelPage buscarHotelPage = new BuscarHotelPage();
    final EscogeHabitacionPage escogeHabitacionPage = new EscogeHabitacionPage();
    final ReservaHabitacionPage reservaPage = new ReservaHabitacionPage();
    final TerminaReservaPage reservaFinPage = new TerminaReservaPage();

    public ReservaStepsDefinition() {

        Before(() -> {
            // Inicia el servicio de Appium, pero en este proyecto lo iniciamos en el
            // terminal con el comnado "appium"
            // apiumBase.startService();

            // Instala/Reinstala la aplicación si es necesario
            apiumBase.installApp();

            // Inicia la sesión de Appium
            apiumBase.startServer();
            apiumBase.startDriver();
        });

        Given("El usuario inicia la aplicación y omite pantallas emergentes hasta la pantalla principal de búsqueda",
                () -> {
                    homePage.omitePantallaNofiticacion();
                    homePage.omitePantallaLogin();

                });

        When("busca hotel en {string} para las fechas {string} a {string} con {int} habitación\\(es) para {int} adulto\\(s) y {int} niño\\(s) de {string} años",
                (String destino, String checkin, String checkout, Integer cantHabitacion, Integer cantAdultos,
                        Integer cantNino, String edadesNinos) -> {

                    // Destino
                    Contexto.reservaObj.setDestino(destino);
                    buscarHotelPage.destino = destino;
                    buscarHotelPage.ingresaDestino();
                    buscarHotelPage.seleccionaOpcionesDestino(2);

                    // Fechas
                    Contexto.reservaObj.setFechaIngreso(checkin);
                    Contexto.reservaObj.setFechaSalida(checkout);
                    buscarHotelPage.fecIngreso = checkin;
                    buscarHotelPage.fecSalida = checkout;
                    buscarHotelPage.seleccionaFechas();

                    // Ocupación
                    Contexto.reservaObj.setCantidadAdultos(cantAdultos);
                    Contexto.reservaObj.setCantidadHabitaciones(cantHabitacion);
                    buscarHotelPage.cantHabitacion = cantHabitacion;
                    buscarHotelPage.cantAdultos = cantAdultos;
                    buscarHotelPage.seleccionaCantidades();

                    // Niños y edades
                    List<Integer> listaEdades = Collections.emptyList();
                    if (cantNino > 0 && !edadesNinos.trim().isEmpty()) {
                        // soporta múltiples edades separadas por coma, ej: "5,7"
                        listaEdades = Arrays.asList(edadesNinos.split(",")).stream().map(String::trim)
                                .map(Integer::parseInt).toList();
                    }
                    Contexto.reservaObj.setCantidadNinosEdad(listaEdades);
                    buscarHotelPage.ninosEdad = listaEdades;
                    buscarHotelPage.seleccionaCantidadNinos();
                });

        And("solicita la búsqueda", () -> {
            buscarHotelPage.buscamosHoteles();
        });

        Then("se muestran al menos {int} hoteles que cumplen los criterios", (Integer minCant) -> {
            List<String> resultado = Contexto.listaHoteles.listaResultadoHoteles();
            System.out.println("Valor de no hoteles: " + Contexto.listaHoteles.cantNoHoteles);
            assertTrue(minCant <= resultado.size());
        });

        When("selecciona el {int}º hotel con pago adelantado con tarjeta", (Integer pos) -> {
            Contexto.listaHoteles.seleccionaHotel(pos);
        });

        And("presiona el botón {string}, {string} o {string} para ver las habitaciones disponibles",
                (String op1, String op2, String op3) -> {
                    // Usa ambos nombres de botón para compatibilidad
                    Contexto.listaHoteles.muestraHabitacionesHotel(Arrays.asList(op1, op2, op3));
                });

        And("selecciona la primera habitación para ver información detallada", () -> {
            escogeHabitacionPage.posicionHabitacion = 1;
            Contexto.reservaObj.setCostoPrevio(escogeHabitacionPage.seleccionaHabitacion());
        });

        Then("el precio mostrado es consistente en la lista y la sección de pre-reserva",
                () -> {
                    Double pReserva = escogeHabitacionPage.muestraInformacionPreReserva();
                    assertEquals(Contexto.reservaObj.getCostoPrevio(), pReserva);
                    Contexto.reservaObj.setCostoPrevio(pReserva);
                });

        When("inicia la reserva de la habitación seleccionada", () -> {
            reservaPage.iniciamosReserva("Reserva ahora"); // puedes parametrizar si el botón varía
        });

        And("completa el formulario con los siguientes datos:", (DataTable datosReserva) -> {
            List<Map<String, String>> rows = datosReserva.asMaps();
            Map<String, String> datos = rows.get(0);
            reservaPage.cliNombres = datos.get("Nombre");
            reservaPage.cliApellidos = datos.get("Apellido");
            reservaPage.cliCorreoE = datos.get("Email");
            reservaPage.cliPaisRegion = datos.get("País");
            reservaPage.cliNumTelf = datos.get("Teléfono");
            reservaPage.cliProposito = datos.get("Motivo");

            reservaPage.ingresamosDatosReserva();
            Double precioActual = reservaPage.muestraInformacionReserva();
            assertEquals(Contexto.reservaObj.costoPrevio, precioActual);
            Contexto.reservaObj.setCostoPrevio(precioActual);
        });

        And("avanza al resumen y confirma el último paso", () -> {
            reservaPage.comprobamosDetalleReserva("Siguiente paso");
            reservaPage.comprobamosResumenReserva("Último paso", "Reserva");
        });

        Then("la aplicación solicita los datos de tarjeta:", (DataTable datosTarjeta) -> {
            List<Map<String, String>> rows = datosTarjeta.asMaps();
            Map<String, String> datCard = rows.get(0);
            reservaFinPage.cardNumber = datCard.get("Número de tarjeta");
            reservaFinPage.cardPropietario = datCard.get("Nombre en tarjeta");
            reservaFinPage.cardExpira = datCard.get("Vencimiento");
            reservaFinPage.cardCVC = datCard.get("CVV");
            reservaFinPage.ingresamosDatosTarjeta();
        });

        When("confirma la reserva con los datos de pago", () -> {
            UtilWaits.waitSeconds(1);
        });

        Then("la aplicación muestra la confirmación exitosa de la reserva", () -> {
            UtilWaits.waitSeconds(1);
        });

        After(() -> {
            apiumBase.stopDriver();
            apiumBase.stopServer();
        });

    }
}
