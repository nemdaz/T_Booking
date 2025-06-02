package po;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import base.BaseAppium;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.Utility;

public class ReservaHabitacionPage extends BaseAppium {

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
		System.out.println("Iniciamos proceso de reserva");
		
		List<WebElement> botones = adriver.findElements(AppiumBy.className("android.widget.Button"));
		for (WebElement btn : botones) {
			System.out.print("\nBotones: " + btn.getText());
			if(btn.getText().contains(btnAction) || btnAction.contains(btn.getText())) {
				System.out.print(" ... Click");
				btn.click();
				break;
			}
		}
	}
	
	public void ingresamosDatosReserva() {
		System.out.println("Ingresamos los datos de reserva");
		//Nombre
		WebElement nombreEle = adriver.findElement(AppiumBy.id("com.booking:id/bstage1_contact_firstname_value"));
		WebElement nombreIpt = nombreEle.findElement(AppiumBy.id("com.booking:id/bui_input_container_content"));
		//nombreIpt.sendKeys(this.cliNombres); // No funciona, input seguros por codigo del APK
		nombreIpt.click(); //Open keyboard
		new Actions(adriver).sendKeys(this.cliNombres).perform();
		//if(adriver.isKeyboardShown()) adriver.hideKeyboard();
		
		//Apellidos
		WebElement apellidoEle = adriver.findElement(AppiumBy.id("com.booking:id/bstage1_contact_lastname_value"));
		WebElement apellidoIpt = apellidoEle.findElement(AppiumBy.id("com.booking:id/bui_input_container_content"));
		apellidoIpt.click();
		new Actions(adriver).sendKeys(this.cliApellidos).perform();
		//adriver.hideKeyboard();
		
		//EMail
		WebElement emailEle = adriver.findElement(AppiumBy.id("com.booking:id/bstage1_contact_email_value"));
		WebElement emailIpt = emailEle.findElement(AppiumBy.id("com.booking:id/bui_input_container_content"));
		emailIpt.click();
		new Actions(adriver).sendKeys(this.cliCorreoE).perform();
		//adriver.hideKeyboard();
		
		//Pais-Region
		WebElement paregEle = adriver.findElement(AppiumBy.id("com.booking:id/bstage1_contact_country_value"));
		WebElement paregIpt = paregEle.findElement(AppiumBy.id("com.booking:id/bui_input_container_content"));
		//paregIpt.click();
		//paregIpt.clear();
		/*
		int cntback = paregIpt.getText().length();
		for (int i = 0; i < cntback; i++) {
			System.out.println("Key back ...");
			adriver.pressKey(new KeyEvent());
		}*/
		//new Actions(adriver).sendKeys(this.cliPaisRegion).perform();
		//adriver.hideKeyboard();
		
		//Telefono
		WebElement telefoEle = adriver.findElement(AppiumBy.id("com.booking:id/bstage1_contact_telephone_value"));
		WebElement telefoIpt = telefoEle.findElement(AppiumBy.id("com.booking:id/bui_input_container_content"));
		telefoIpt.click();
		new Actions(adriver).sendKeys(this.cliNumTelf).perform();
		//adriver.hideKeyboard();
		
		//Motivo
		List<WebElement> propoGroupEle = adriver.findElements(AppiumBy.id("com.booking:id/business_purpose_container"));
		for (WebElement propoElement : propoGroupEle) {
			System.out.printf("Motivo [%s]: Click en radio button coincidente ... ", this.cliProposito);
			if(this.cliProposito.contains(propoElement.getText()) || propoElement.getText().contains(this.cliProposito)) {
				if(propoElement.getAttribute("checked") != "true") {
					propoElement.click();
					System.out.print("Click\n");
				}
				break;
			}
		}		
		
	}
	
	public Double muestraInformacionReserva() {
		
		WebElement nodeReserva = adriver.findElement(AppiumBy.id("com.booking:id/informative_click_to_action_container"));
		WebElement priceHab = nodeReserva.findElement(AppiumBy.id("com.booking:id/title"));
		
		String strPrice = priceHab.getText();
		List<Double> nums = Utility.numbersFromString(strPrice);
		Double dPrice = Utility.numbersFromString(strPrice).get(0);
		if(nums.size() > 1) {
			dPrice = Utility.numbersFromString(strPrice).get(1);
		}
		
		System.out.printf("Reserva Hab. RSV : Precio en Text: %s\n", strPrice);
		System.out.printf("Reserva Hab. RSV : Precio en Doub: %s\n", dPrice.toString());	
		
		return dPrice;
	}
	
	public void comprobamosDetalleReserva(String btnTexto) {
		WebElement infoContainer = adriver.findElement(AppiumBy.id("com.booking:id/informative_click_to_action_container"));
		WebElement nextAction = infoContainer.findElement(AppiumBy.id("com.booking:id/action_button"));
		if(nextAction.getText().trim().contains(btnTexto) || btnTexto.contains(nextAction.getText().trim())) {
			nextAction.click();
		}
	}
	
	public void comprobamosResumenReserva(String btnTexto) {
		WebElement infoContainer = adriver.findElement(AppiumBy.id("com.booking:id/informative_click_to_action_container"));
		WebElement nextAction = infoContainer.findElement(AppiumBy.id("com.booking:id/action_button"));
		if(nextAction.getText().trim().contains(btnTexto) || btnTexto.contains(nextAction.getText().trim())) {
			nextAction.click();
		}
	}

}
