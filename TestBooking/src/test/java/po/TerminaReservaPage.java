package po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import base.BaseAppium;
import io.appium.java_client.AppiumBy;

public class TerminaReservaPage extends BaseAppium {
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
		// TODO Auto-generated constructor stub
	}
	
	
	public void ingresamosDatosTarjeta() {
		System.out.println("Ingresamos los datos de la Tarjeta");
		//Numero
		WebElement numeroEle = adriver.findElement(AppiumBy.id("com.booking:id/new_credit_card_number_edit"));
		numeroEle.click(); //Open keyboard
		new Actions(adriver).sendKeys(this.cardNumber).perform();
		
		//Propietario
		WebElement propietarioEle = adriver.findElement(AppiumBy.id("com.booking:id/new_credit_card_holder_edit"));
		propietarioEle.click(); //Open keyboard
		propietarioEle.clear();
		new Actions(adriver).sendKeys(this.cardPropietario).perform();
		
		//Fecha Caducidad
		WebElement fechaCaducaEle = adriver.findElement(AppiumBy.id("com.booking:id/new_credit_card_expiry_date_edit"));
		fechaCaducaEle.click(); //Open keyboard
		new Actions(adriver).sendKeys(this.cardExpira).perform();
		
		//CVC
		WebElement cvcEle = adriver.findElement(AppiumBy.id("com.booking:id/new_credit_card_cvc_edit_text"));
		cvcEle.click(); //Open keyboard
		new Actions(adriver).sendKeys(this.cardCVC).perform();
		
	}
	
	public void terminamosReserva(String btnTexto) {
		WebElement infoContainer = adriver.findElement(AppiumBy.id("com.booking:id/informative_click_to_action_container"));
		WebElement nextAction = infoContainer.findElement(AppiumBy.id("com.booking:id/action_button"));
		if(nextAction.getText().trim().contains(btnTexto) || btnTexto.contains(nextAction.getText().trim())) {
			nextAction.click();
		}
	}

}
