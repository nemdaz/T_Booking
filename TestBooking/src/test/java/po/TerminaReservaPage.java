package po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.AppiumBy;
import utils.UtilWaits;

public class TerminaReservaPage extends BasePage {
	public String cardNumber;
	public String cardPropietario;
	public String cardExpira;
	public String cardCVC;

	public TerminaReservaPage(String cardNumber, String cardPropietario, String cardExpira, String cardCVC) {
		super();
		this.cardNumber = cardNumber;
		this.cardPropietario = cardPropietario;
		this.cardExpira = cardExpira;
		this.cardCVC = cardCVC;
	}

	public TerminaReservaPage() {
	}

	public void ingresamosDatosTarjeta() {
		UtilWaits.waitSeconds(4);

		System.out.println("Ingresamos los datos de la Tarjeta");

		// Numero
		String selectorNum = "//android.widget.EditText[@resource-id=\"com.booking:id/new_credit_card_number_edit\"]";
		WebElement numeroEle = adriver.findElementUntilFound(AppiumBy.xpath(selectorNum));
		numeroEle.click(); // Open keyboard
		new Actions(adriver).sendKeys(this.cardNumber).perform();

		// Propietario
		String selectorProp = "//android.widget.EditText[@resource-id=\"com.booking:id/new_credit_card_holder_edit\"]";
		WebElement propietarioEle = adriver.findElement(AppiumBy.xpath(selectorProp));
		propietarioEle.click(); // Open keyboard
		propietarioEle.clear();
		new Actions(adriver).sendKeys(this.cardPropietario).perform();

		// Fecha Caducidad
		String selectorCaduc = "//android.widget.LinearLayout[@resource-id=\"com.booking:id/new_credit_card_expiry_date_input_layout\"]";
		WebElement fechaCaducaEle = adriver.findElement(AppiumBy.xpath(selectorCaduc));
		fechaCaducaEle.click(); // Open keyboard
		new Actions(adriver).sendKeys(this.cardExpira).perform();

		// CVC
		String selectorCVC = "//android.widget.EditText[@resource-id=\"com.booking:id/new_credit_card_cvc_edit_text\"]";
		WebElement cvcEle = adriver.findElementUntilFound(AppiumBy.xpath(selectorCVC));
		cvcEle.click(); // Open keyboard
		new Actions(adriver).sendKeys(this.cardCVC).perform();

	}

	public void terminamosReserva(String btnTexto) {
		String selectorCont = "//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.booking:id/bp_bottom_bar_compose_view\"]/android.view.View/android.view.View/android.view.View[2]";
		WebElement infoContainer = adriver.findElement(AppiumBy.xpath(selectorCont));
		String selectorNext = "//android.widget.TextView[contains(@text, \"%s\")]".formatted(btnTexto);
		WebElement nextAction = infoContainer.findElement(AppiumBy.xpath(selectorNext));
		if (nextAction != null)
			nextAction.click();
	}

}
