package po;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.AppiumBy;
import utils.UtilWaits;
import utils.Utils;

public class ReservaHabitacionPage extends BasePage {

	public String cliNombres;
	public String cliApellidos;
	public String cliCorreoE;
	public String cliPaisRegion;
	public String cliNumTelf;
	public String cliProposito;

	public ReservaHabitacionPage(String cliNombres, String cliApellidos, String cliCorreoE, String cliPaisRegion,
			String cliNumTelf, String cliProposito) {
		super();
		this.cliNombres = cliNombres;
		this.cliApellidos = cliApellidos;
		this.cliCorreoE = cliCorreoE;
		this.cliPaisRegion = cliPaisRegion;
		this.cliNumTelf = cliNumTelf;
		this.cliProposito = cliProposito;
	}

	public ReservaHabitacionPage() {

	}

	public void iniciamosReserva(String btnAction) {
		UtilWaits.waitSeconds(1);
		System.out.println("Iniciamos proceso de reserva");

		String selectorReserva = "new UiSelector().resourceId(\"com.booking:id/room_list_action_bar_compose\")";
		WebElement nodoReserva = adriver.findElementUntilFound(AppiumBy.androidUIAutomator(selectorReserva));
		List<WebElement> botones = nodoReserva.findElements(AppiumBy.className("android.widget.Button"));
		botones.get(0).click();
	}

	public void ingresamosDatosReserva() {
		System.out.println("Ingresamos los datos de reserva");

		UtilWaits.waitSeconds(1);

		// Esperamos que se cargue el formulario de reserva
		String selectorFormulario = "new UiSelector().resourceId(\"com.booking:id/bp_content\")";
		WebElement formulario = adriver.findElementUntilFound(AppiumBy.androidUIAutomator(selectorFormulario));

		// Nombre
		String selectorNombre = "//android.widget.EditText[.//android.widget.TextView[contains(@text, 'Nombre')]]";
		WebElement nombreIpt = formulario.findElement(AppiumBy.xpath(selectorNombre));
		nombreIpt.click(); // Open keyboard
		UtilWaits.waitSeconds(1);
		new Actions(adriver).sendKeys(this.cliNombres).perform();
		// if(adriver.isKeyboardShown()) adriver.hideKeyboard();

		// Apellidos
		String selectorApellido = "//android.widget.EditText[.//android.widget.TextView[contains(@text, 'Apellido')]]";
		WebElement apellidoIpt = formulario.findElement(AppiumBy.xpath(selectorApellido));
		apellidoIpt.click();
		UtilWaits.waitSeconds(1);
		new Actions(adriver).sendKeys(this.cliApellidos).perform();
		// adriver.hideKeyboard();

		// EMail
		String selectorEmail = "//android.widget.EditText[.//android.widget.TextView[contains(@text, 'E-mail')]]";
		WebElement emailIpt = formulario.findElement(AppiumBy.xpath(selectorEmail));
		emailIpt.click();
		UtilWaits.waitSeconds(1);
		new Actions(adriver).sendKeys(this.cliCorreoE).perform();
		// adriver.hideKeyboard();

		// Pais/Region
		// Ya está seleccionado por defecto, no se cambia

		// Telefono
		String selectorTelefono = "//android.widget.EditText[.//android.widget.TextView[contains(@text, 'Teléfono')]]";
		WebElement telefoIpt = formulario.findElement(AppiumBy.xpath(selectorTelefono));
		telefoIpt.click();
		UtilWaits.waitSeconds(1);
		new Actions(adriver).sendKeys(this.cliNumTelf).perform();
		// adriver.hideKeyboard();
		if (adriver.isKeyboardShown())
			adriver.hideKeyboard();

		// Scroll
		String uiEventScroll = "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()";
		adriver.findElement(AppiumBy.androidUIAutomator(uiEventScroll));
		UtilWaits.waitSeconds(1);

		// Motivo
		String xpathContMotivo = "//android.view.View[android.widget.TextView[contains(@text, 'el motivo')]]/android.view.View";
		List<WebElement> propoGroupEle = adriver.findElementsUntilFound(AppiumBy.xpath(xpathContMotivo));

		for (WebElement propoElement : propoGroupEle) {

			System.out.printf("> Motivo [%s] ", this.cliProposito);
			String xpathOpt = "//android.widget.TextView";
			WebElement option = propoElement.findElement(AppiumBy.xpath(xpathOpt));
			System.out.printf("> Opción encontrada: %s ", option.getText());
			if (option.getText().contains(this.cliProposito)) {
				option.click();
				System.out.printf("> Click()\n");
				break;
			}
		}

	}

	public Double muestraInformacionReserva() {
		UtilWaits.waitSeconds(1);

		String selectorReserva = "//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.booking:id/bp_content\"]/android.view.View/android.view.View/android.view.View[2]";
		WebElement nodeReserva = adriver.findElementUntilFound(AppiumBy.xpath(selectorReserva));
		String selectorPrecio = "//android.view.View/android.widget.TextView[contains(@content-desc, \"Precio actual\")]";
		WebElement priceHab = nodeReserva.findElement(AppiumBy.xpath(selectorPrecio));

		String strPrice = priceHab.getText();
		List<Double> nums = Utils.numbersFromString(strPrice);
		Double dPrice = Utils.numbersFromString(strPrice).get(0);
		if (nums.size() > 1) {
			dPrice = Utils.numbersFromString(strPrice).get(1);
		}

		System.out.printf("Reserva Hab. RSV : Precio en Text: %s\n", strPrice);
		System.out.printf("Reserva Hab. RSV : Precio en Doub: %s\n", dPrice.toString());

		return dPrice;
	}

	public void comprobamosDetalleReserva(String btnTexto) {
		UtilWaits.waitSeconds(1);

		String selectorContainer = "//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.booking:id/bp_content\"]/android.view.View/android.view.View/android.view.View[2]";
		WebElement infoContainer = adriver.findElement(AppiumBy.xpath(selectorContainer));
		String selectorNext = "//android.view.View/android.widget.TextView[contains(@text, \"%s\")]"
				.formatted(btnTexto);
		WebElement nextAction = infoContainer.findElement(AppiumBy.xpath(selectorNext));
		if (nextAction != null)
			nextAction.click();
	}

	public void comprobamosResumenReserva(String... btnTexto) {
		UtilWaits.waitSeconds(1);

		String selectorCont = "//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.booking:id/bp_bottom_bar_compose_view\"]/android.view.View/android.view.View/android.view.View[2]";
		WebElement infoContainer = adriver.findElement(AppiumBy.xpath(selectorCont));
		// String selectorNext = "//android.widget.TextView[contains(@text,
		// \"%s\")]".formatted(btnTexto);
		String selectorNext = "//android.widget.TextView[@text and string-length(@text) > 0]";
		List<WebElement> nextActions = infoContainer.findElements(AppiumBy.xpath(selectorNext));
		for (WebElement nextAction : nextActions) {
			String actionText = nextAction.getText();
			for (String btn : btnTexto) {
				if (actionText.contains(btn)) {
					System.out.printf("> Click en botón: %s\n", btn);
					nextAction.click();
					return;
				}
			}
		}
	}

}
