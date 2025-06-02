package po;

import java.util.List;

import org.openqa.selenium.WebElement;

import base.BaseAppium;
import io.appium.java_client.AppiumBy;
import utils.Utility;

public class EscogeHabitacionPage extends BaseAppium {
	
	public int posicionHabitacion;
	
	public EscogeHabitacionPage() {
		
	}

	public boolean muestraHabitaciones() {
		WebElement nodeHab = adriver.findElement(AppiumBy.id("com.booking:id/rooms_recycler_view"));
		List<WebElement> habitaciones = nodeHab.findElements(AppiumBy.id("com.booking:id/room_list_card_wrapper_container"));
		
		System.out.println("Lista de habitaciones:");
		for (WebElement habitacion : habitaciones) {
			WebElement name = habitacion.findElement(AppiumBy.id("com.booking:id/rooms_item_title"));
			System.out.printf("Nombre Habitación: %s\n", name.getText());
		}
		
		if(habitaciones.size() > 0) return true;
		return false;
	}
	
	public Double seleccionaHabitacion() {
		WebElement nodeHab = adriver.findElement(AppiumBy.id("com.booking:id/rooms_recycler_view"));
		List<WebElement> habitaciones = nodeHab.findElements(AppiumBy.id("com.booking:id/room_list_card_wrapper_container"));
		WebElement habitacion = habitaciones.get(this.posicionHabitacion - 1);
		WebElement ePrice = habitacion.findElement(AppiumBy.id("com.booking:id/price_view_price"));
		String strPrice = ePrice.getText();

		habitacion.findElement(AppiumBy.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[1]")).click();
		
		Double dPrice = Utility.numberFromString(strPrice);
		
		System.out.printf("LST : Precio en Text: %s\n", strPrice);
		System.out.printf("LST : Precio en Doub: %s\n", dPrice.toString());	
		
		return dPrice;
	}
	
	public Double muestraInformacionHabitacion() {
		
		WebElement nodeInfoHab = adriver.findElement(AppiumBy.id("com.booking:id/room_recycler_view"));
		WebElement priceHab = nodeInfoHab.findElement(AppiumBy.id("com.booking:id/price_view_price"));
		
		String strPrice = priceHab.getText();
		Double dPrice = Utility.numberFromString(strPrice);
		
		System.out.printf("DET : Precio en Text: %s\n", strPrice);
		System.out.printf("DET : Precio en Doub: %s\n", dPrice.toString());	
		
		return dPrice;
	}
	
	public Double muestraInformacionReserva() {
		
		WebElement nodeReserva = adriver.findElement(AppiumBy.id("com.booking:id/book_now_layout"));
		WebElement priceHab = nodeReserva.findElement(AppiumBy.id("com.booking:id/info_title"));
		
		String strPrice = priceHab.getText();
		Double dPrice = Utility.numberFromString(strPrice);
		
		System.out.printf("RSV : Precio en Text: %s\n", strPrice);
		System.out.printf("RSV : Precio en Doub: %s\n", dPrice.toString());	
		
		return dPrice;
	}
}
