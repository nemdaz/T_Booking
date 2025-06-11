package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import po.BuscarHotelPage;
import po.EscogeHabitacionPage;
import po.ReservaHabitacionPage;
import po.TerminaReservaPage;
import utils.UtilDelay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import base.BaseAppium;
import base.Contexto;

public class ReservaStepsDefinition implements En {

    final BaseAppium apiumBase = new BaseAppium();
    final BuscarHotelPage buscarHotelPage = new BuscarHotelPage();
    final EscogeHabitacionPage escogeHabitacionPage = new EscogeHabitacionPage();
    final ReservaHabitacionPage reservaPage = new ReservaHabitacionPage();
    final TerminaReservaPage reservaFinPage = new TerminaReservaPage();

    public ReservaStepsDefinition() {

        Before(() -> {
            // apiumBase.startService();
        });

        // Adaptado al nuevo feature:
        Given("El usuario inicia la aplicación y omite pantallas emergentes hasta la pantalla principal de búsqueda", () -> {
            apiumBase.startDriver();
            // Aquí podrías añadir lógica para omitir notificaciones/login si aplica.
        });

		When("busca hotel en {string} para las fechas {string} a {string} con {int} habitación\\(es) para {int} adulto\\(s) y {int} niño\\(s) de {string} años",
            (String destino, String checkin, String checkout, Integer cantHabitacion, Integer cantAdultos, Integer cantNino, String edadesNinos) -> {

            UtilDelay.coolDelay(2000);

            // Destino
            Contexto.reservaObj.setDestino(destino);
            buscarHotelPage.destino = destino;
            buscarHotelPage.ingresaDestino();
            UtilDelay.coolDelay(1000);
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
                listaEdades = Arrays.asList(edadesNinos.split(",")).stream().map(String::trim).map(Integer::parseInt).toList();
            }
            Contexto.reservaObj.setCantidadNinosEdad(listaEdades);
            buscarHotelPage.ninosEdad = listaEdades;
            buscarHotelPage.seleccionaCantidadNinos();
        });

        And("solicita la búsqueda", () -> {
            UtilDelay.coolDelay(2000);
            buscarHotelPage.buscamosHoteles();
        });

        Then("se muestran al menos {int} hoteles que cumplen los criterios", (Integer minCant) -> {
            UtilDelay.coolDelay(2000);
            List<String> resultado = Contexto.listaHoteles.listaResultadoHoteles();
            System.out.println("Valor de no hoteles: " + Contexto.listaHoteles.cantNoHoteles);
            assertTrue(minCant <= resultado.size());
        });

        When("selecciona el segundo hotel de la lista", () -> {
            UtilDelay.coolDelay(2000);
            Contexto.listaHoteles.seleccionaPos = 2;
            Contexto.listaHoteles.seleccionaHotel();
        });

        And("presiona el botón {string} o {string} para ver las habitaciones disponibles", (String op1, String op2) -> {
            UtilDelay.coolDelay(1000);
            // Usa ambos nombres de botón para compatibilidad
            Contexto.listaHoteles.muestraHabitacionesHotel(Arrays.asList(op1, op2));
        });

        And("selecciona la primera habitación para ver información detallada", () -> {
            UtilDelay.coolDelay(2000);
            escogeHabitacionPage.posicionHabitacion = 1;
            Contexto.reservaObj.setCostoPrevio(escogeHabitacionPage.seleccionaHabitacion());
        });

        Then("el precio mostrado es consistente en la lista, la información de la habitación y la sección de reserva", () -> {
            UtilDelay.coolDelay(5000);
            Double pInfo = escogeHabitacionPage.muestraInformacionHabitacion();
            assertEquals(Contexto.reservaObj.getCostoPrevio(), pInfo);
            Contexto.reservaObj.setCostoPrevio(pInfo);

            UtilDelay.coolDelay(1000);
            Double pReserva = escogeHabitacionPage.muestraInformacionReserva();
            assertEquals(Contexto.reservaObj.getCostoPrevio(), pReserva);
            Contexto.reservaObj.setCostoPrevio(pReserva);
        });

        When("inicia la reserva de la habitación seleccionada", () -> {
            UtilDelay.coolDelay(1000);
            reservaPage.iniciamosReserva("Reserva ahora"); // puedes parametrizar si el botón varía
        });

        And("completa el formulario con los siguientes datos:", (DataTable datosReserva) -> {
            UtilDelay.coolDelay(5000);
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

        And("avanza al resumen y confirma el último paso", () -> {
            UtilDelay.coolDelay(1000);
            reservaPage.comprobamosDetalleReserva("Siguiente paso");
            UtilDelay.coolDelay(1000);
            reservaPage.comprobamosResumenReserva("Último paso");
        });

        Then("la aplicación solicita los datos de tarjeta:", (DataTable datosTarjeta) -> {
            UtilDelay.coolDelay(4000);
            List<String> datCard = datosTarjeta.asList();
            reservaFinPage.cardNumber = datCard.get(0);
            reservaFinPage.cardPropietario = datCard.get(1);
            reservaFinPage.cardExpira = datCard.get(2);
            reservaFinPage.cardCVC = datCard.get(3);

            reservaFinPage.ingresamosDatosTarjeta();
        });

        When("confirma la reserva con los datos de pago", () -> {
            UtilDelay.coolDelay(1000);
            // Aquí podrías incluir el click final, si aplica
        });

        Then("la aplicación muestra la confirmación exitosa de la reserva", () -> {
            UtilDelay.coolDelay(1000);
            // Aquí puedes añadir la validación final, si tienes método.
        });

        After(() -> {
            // Acciones finales para cada scenario, si las tienes implementadas.
        });

    }
}
