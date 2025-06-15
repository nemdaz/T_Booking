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

		// WebElement nodeHab =
		// adriver.findElement(AppiumBy.id("com.booking:id/rooms_recycler_view"));

		// Wait: Esperamos que se cargue la lista de habitaciones
		UtilWaits.waitUntilFound(adriver, AppiumBy.id("com.booking:id/room_list_item_compose"));

		List<WebElement> habitaciones = adriver.findElementsUntilFound(AppiumBy.id("com.booking:id/room_list_item_compose"));
		WebElement habitacion = habitaciones.get(this.posicionHabitacion - 1);
		String xpathHabPrice = "//android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View/android.widget.TextView";
		WebElement ePrice = habitacion.findElement(AppiumBy.id(xpathHabPrice));
		String strPrice = ePrice.getText();

		String xpathBtnSeleccionar = "//android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]/android.widget.Button";
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

	public Double muestraInformacionReserva() {

		WebElement nodeReserva = adriver.findElement(AppiumBy.id("com.booking:id/book_now_layout"));
		WebElement priceHab = nodeReserva.findElement(AppiumBy.id("com.booking:id/info_title"));

		String strPrice = priceHab.getText();
		Double dPrice = Utils.numberFromString(strPrice);

		System.out.printf("RSV : Precio en Text: %s\n", strPrice);
		System.out.printf("RSV : Precio en Doub: %s\n", dPrice.toString());

		return dPrice;
	}
}
