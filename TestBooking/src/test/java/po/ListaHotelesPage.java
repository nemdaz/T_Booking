package po;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import utils.UtilWaits;

public class ListaHotelesPage extends BasePage {
	public int seleccionaPos;
	public int cantNoHoteles;

	public ListaHotelesPage() {
		super();
	}

	public List<String> listaResultadoHoteles() {

		// Vars
		String xpathContResults = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/androidx.compose.ui.platform.ComposeView";

		// Wait: Esperar que se muestre la lista de hoteles
		UtilWaits.waitUntilFound(adriver, AppiumBy.xpath(xpathContResults));

		// Action: Identificar el contenedor de resultados y obtener la lista de hoteles
		String xpathContenedorResultados = "//android.view.View[@resource-id=\"sr_list\"]";
		String xpathListaHoteles = "//android.view.View[@content-desc]";
		WebElement containerResultados = adriver.findElement(AppiumBy.xpath(xpathContenedorResultados));
		List<WebElement> listaHoteles = containerResultados.findElements(AppiumBy.xpath(xpathListaHoteles));
		List<String> resultadoHoteles = new ArrayList<>();

		this.cantNoHoteles = 0;
		for (WebElement containerHotel : listaHoteles) {
			try {
				String hotelText = containerHotel.getAttribute("content-desc");
				String hotelName = hotelText.split("\n")[0];
				resultadoHoteles.add(hotelName);
			} catch (NoSuchElementException ex) {
				System.out.println("Grupo que no es hotel, no se agrega a la lista ...");
				this.cantNoHoteles++;
			}
		}

		for (String string : resultadoHoteles) {
			System.out.printf("Hotel en lista: %s\n", string);
		}
		System.out.printf("otros omitidos: %s\n", this.cantNoHoteles);

		return resultadoHoteles;
	}

	public void seleccionaHotel() {

		// Action: Identificar el contenedor de resultados y obtener la lista de hoteles
		String xpathContenedorResultados = "//android.view.View[@resource-id=\"sr_list\"]";
		String xpathListaHoteles = "//android.view.View[@content-desc]";
		WebElement containerResultados = adriver.findElement(AppiumBy.xpath(xpathContenedorResultados));
		List<WebElement> listaHoteles = containerResultados.findElements(AppiumBy.xpath(xpathListaHoteles));

		int finalPosition = (this.seleccionaPos + this.cantNoHoteles) - 1;
		System.out.printf("Selecciona hotel, posicion final (%s + %s - 1) : %s\n", this.seleccionaPos,
				this.cantNoHoteles, finalPosition);

		WebElement hotel = listaHoteles.get(finalPosition);
		hotel.click();

	}

	public boolean muestraDetalleHotel() {
		try {
			List<WebElement> vistaDetalle = adriver.findElements(AppiumBy.id("com.booking:id/listLayout"));
			if (vistaDetalle.size() > 0)
				return true;
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	public void muestraHabitacionesHotel(List<String> nombreBotonesParaMostrar) {
		WebElement boton = adriver.findElement(AppiumBy.id("com.booking:id/select_room_cta"));

		for (String txtBtn : nombreBotonesParaMostrar) {
			if (boton.getText().contains(txtBtn) || txtBtn.contains(boton.getText())) {
				boton.click();
				break;
			}
		}
	}

}
