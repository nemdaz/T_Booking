package po;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import utils.UtilWaits;
import utils.Utils;

public class EscogeHabitacionPage extends BasePage {

	public int posicionHabitacion;

	public EscogeHabitacionPage() {

	}

	public boolean muestraHabitaciones() {
		WebElement nodeHab = adriver.findElement(AppiumBy.id("com.booking:id/rooms_recycler_view"));
		List<WebElement> habitaciones = nodeHab
				.findElements(AppiumBy.id("com.booking:id/room_list_card_wrapper_container"));

		System.out.println("Lista de habitaciones:");
		for (WebElement habitacion : habitaciones) {
			WebElement name = habitacion.findElement(AppiumBy.id("com.booking:id/rooms_item_title"));
			System.out.printf("Nombre Habitación: %s\n", name.getText());
		}

		if (habitaciones.size() > 0)
			return true;
		return false;
	}

	public Double seleccionaHabitacion() {
		UtilWaits.waitSeconds(1);
		// Wait: Esperamos que se cargue la lista de habitaciones
		UtilWaits.waitUntilFound(adriver, AppiumBy.id("com.booking:id/room_list_item_compose"));

		List<WebElement> habitaciones = adriver.findElementsUntilFound(AppiumBy.id("com.booking:id/room_list_item_compose"));
		WebElement habitacion = habitaciones.get(this.posicionHabitacion - 1);

		String xpathHabPrice = "//android.widget.TextView[contains(@content-desc, 'Precio actual')]";
		WebElement ePrice = habitacion.findElement(AppiumBy.xpath(xpathHabPrice));
		String strPrice = ePrice.getText();
		System.out.println("> - Precio de la habitación: " + strPrice);

		//Quitamos la seleccion automatica de la primera habitación si hubiese
		try{
			//String quitarBtn = "//android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[4]/android.widget.Button";
			String quitarBtn = "//android.view.View[contains(@content-desc, 'Eliminar')]";
			habitacion.findElement(AppiumBy.xpath(quitarBtn)).click();
		}catch (Exception e) {
			
		}

		//String xpathBtnSeleccionar = "//android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View/android.view.View[3]/android.widget.Button";
		//habitacion.findElement(AppiumBy.xpath(xpathBtnSeleccionar)).click();
		String xpathBtnSeleccionar = "//android.widget.TextView[contains(@text, 'Seleccionar')]";
		habitacion.findElement(AppiumBy.xpath(xpathBtnSeleccionar)).click();

		Double dPrice = Utils.numberFromString(strPrice);

		System.out.printf("LST : Precio en Text: %s\n", strPrice);
		System.out.printf("LST : Precio en Doub: %s\n", dPrice.toString());

		return dPrice;
	}

	public Double muestraInformacionHabitacion() {

		WebElement nodeInfoHab = adriver.findElement(AppiumBy.id("com.booking:id/room_recycler_view"));
		WebElement priceHab = nodeInfoHab.findElement(AppiumBy.id("com.booking:id/price_view_price"));

		String strPrice = priceHab.getText();
		Double dPrice = Utils.numberFromString(strPrice);

		System.out.printf("DET : Precio en Text: %s\n", strPrice);
		System.out.printf("DET : Precio en Doub: %s\n", dPrice.toString());

		return dPrice;
	}

	public Double muestraInformacionPreReserva() {
		UtilWaits.waitSeconds(1);
		
		String xpathNodoReserva = "//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.booking:id/room_list_action_bar_compose\"]";
		UtilWaits.waitUntilFound(adriver, AppiumBy.xpath(xpathNodoReserva));

		String xpathPrice = xpathNodoReserva + "/android.view.View/android.view.View/android.view.View/android.widget.TextView[1]";
		WebElement priceHab = adriver.findElementUntilFound(AppiumBy.xpath(xpathPrice));

		String strPrice = priceHab.getText();
		Double dPrice = Utils.numberFromString(strPrice);

		System.out.printf("RSV : Precio en Text: %s\n", strPrice);
		System.out.printf("RSV : Precio en Doub: %s\n", dPrice.toString());

		return dPrice;
	}
}
