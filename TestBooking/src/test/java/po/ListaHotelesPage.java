package po;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumBy;
import utils.UtilLogs;
import utils.UtilWaits;

public class ListaHotelesPage extends BasePage {
	public int cantNoHoteles;

	public ListaHotelesPage() {
		super();
	}

	public List<String> listaResultadoHoteles() {
		UtilWaits.waitSeconds(2);

		// Vars
		String xpathContResults = "//android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView";
		// String selContResult = "new
		// UiSelector().className(\"androidx.compose.ui.platform.ComposeView\")";

		// Wait: Esperar que se muestre la lista de hoteles
		UtilWaits.waitUntilFound(adriver, AppiumBy.xpath(xpathContResults));
		// UtilWaits.waitUntilFound(adriver,
		// AppiumBy.androidUIAutomator(selContResult));

		// Action: Identificar el contenedor de resultados y obtener la lista de hoteles
		String xpathContenedorResultados = "//android.view.View[@resource-id=\"sr_list\"]";
		String xpathListaHoteles = "//android.view.View[@content-desc]";
		WebElement containerResultados = adriver.findElementUntilFound(AppiumBy.xpath(xpathContenedorResultados));
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

	public void seleccionaHotel(Integer successAssert) {

		System.out.printf("> Selecciona hotel: [%s]\n", successAssert);
		Integer countAttemps = 0;
		Integer countAsserts = 0;
		Integer maxAttemps = 10;

		WebElement successHotel = null;

		while (countAttemps <= maxAttemps) {
			System.out.println("\n> Intento " + (countAttemps) + " de " + maxAttemps + "\n");
			UtilWaits.waitSeconds(1);

			// Action: Identificar el contenedor de resultados y obtener la lista de hoteles
			String xpathContenedorResultados = "//android.view.View[@resource-id=\"sr_list\"]";
			String xpathListaHoteles = "./android.view.View/android.view.View[@content-desc]";

			WebElement containerResultados = adriver.findElementUntilFound(AppiumBy.xpath(xpathContenedorResultados));
			List<WebElement> listaHoteles = containerResultados.findElements(AppiumBy.xpath(xpathListaHoteles));

			//System.out.println("\n> Page source del intento: \n");
			//UtilLogs.printPageSource(adriver);

			for (WebElement hotel : listaHoteles) {

				String hotelText = hotel.getAttribute("content-desc");
				System.out.printf("\n> Hotel interado: %s\n", hotelText.replaceAll("\\n", ". "));
				if (!hotelText.contains("Sin pago por adelantado")) {
					System.out.printf("\n> Hotel coincidente: %s\n", hotelText.replaceAll("\\n", ". "));
					countAsserts++;
					if (countAsserts.equals(successAssert)) {
						successHotel = hotel;
						System.out.printf("> > Hotel encontrado.\n");
						break;
					}
				}
			}
			if (successHotel != null) {
				break;
			} else {
				// Scroll
				System.out.println("> Hotel no encontrado, intentamos scroll ...");
				String uiEventScroll = "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()";
				adriver.findElement(AppiumBy.androidUIAutomator(uiEventScroll));
			}
			countAttemps++;
		}

		System.out.println("> Hotel seleccionado: " + successHotel.getAttribute("content-desc"));
		successHotel.click();

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
		UtilWaits.waitSeconds(1);
		WebElement boton = adriver.findElementUntilFound(AppiumBy.id("com.booking:id/select_room_cta"));

		for (String txtBtn : nombreBotonesParaMostrar) {
			if (boton.getText().contains(txtBtn) || txtBtn.contains(boton.getText())) {
				boton.click();
				break;
			}
		}
	}

}
