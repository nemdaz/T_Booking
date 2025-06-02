package po;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseAppium;
import io.appium.java_client.AppiumBy;
import utils.Utility;
import utils.UtilDelay;
import utils.UtilDriver;

public class BuscarHotelPage extends BaseAppium {
	public String destino;
	public String fecIngreso;
	public String fecSalida;
	public int cantHabitacion;
	public int cantAdultos;
	public List<Integer> ninosEdad;

	public BuscarHotelPage(String destino, String fecIngreso, String fecSalida, int cantHabitacion, int cantAdultos,
			List<Integer> ninosEdad) {
		super();
		this.destino = destino;
		this.fecIngreso = fecIngreso;
		this.fecSalida = fecSalida;
		this.cantHabitacion = cantHabitacion;
		this.cantAdultos = cantAdultos;
		this.ninosEdad = ninosEdad;
	}

	public BuscarHotelPage(String destino) {
		super();
		this.destino = destino;
	}

	public BuscarHotelPage(String fecIngreso, String fecSalida) {
		super();
		this.fecIngreso = fecIngreso;
		this.fecSalida = fecSalida;
	}

	public BuscarHotelPage(int cantHabitacion, int cantAdultos) {
		super();
		this.cantHabitacion = cantHabitacion;
		this.cantAdultos = cantAdultos;
	}
	
	public BuscarHotelPage(List<Integer> ninosEdad) {
		super();
		this.ninosEdad = ninosEdad;
	}
	
	public BuscarHotelPage() {
		super();
	}

	public void ingresaDestino() {
		// Wait
		UtilDriver.waitUntilVisible("com.booking:id/facet_search_box_cta", 3);

		// Action
		WebElement desLbl = adriver
				.findElement(AppiumBy.id("com.booking:id/facet_search_box_accommodation_destination"));
		desLbl.click();
		WebElement desTxt = adriver
				.findElement(AppiumBy.id("com.booking:id/facet_with_bui_free_search_booking_header_toolbar_content"));
		desTxt.sendKeys(this.destino);
	}

	public void seleccionaOpcionesDestino(int index) {
		// Action
		WebElement optDestinosContainer = adriver
				.findElement(AppiumBy.id("com.booking:id/facet_disambiguation_content"));
		List<WebElement> optDestinos = optDestinosContainer.findElements(AppiumBy.xpath("//android.view.ViewGroup"));
		optDestinos.get(index).click();
	}

	public void seleccionaFechas() {
		String iF = "dd/MM/yyyy";
		String sF = "dd MMMM yyyy";
		Date dateCheckin = Utility.dateFromString(this.fecIngreso, iF);
		String checkin = Utility.dateChangeFormat(this.fecIngreso, iF, sF);
		String checkout = Utility.dateChangeFormat(this.fecSalida, iF, sF);
		
		long difIni = Utility.dateDiferenceDays(Utility.dateFromString(this.fecIngreso, iF), new Date());
		if( difIni < 0) {
			dateCheckin = Utility.dateAddDays(dateCheckin, Math.abs(difIni));
			System.out.printf("La fecha de Ingreso es menor a la actual, se cambia ... %s\n", dateCheckin);
			checkin = Utility.dateToString(dateCheckin, sF);
		}
		
		// Action
		UtilDriver.waitUntilVisible("com.booking:id/calendar_month_list", 5);
		
		WebElement calendarioContainer = adriver.findElement(AppiumBy.id("com.booking:id/calendar_month_list"));
		
		WebElement fechaMesI = calendarioContainer
				.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"" + checkin + "\"]"));		
		WebElement fechaMesF = calendarioContainer
				.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"" + checkout + "\"]"));
		
		fechaMesI.click();		
		fechaMesF.click();

		UtilDelay.coolDelay(1 * 1000);
		WebElement btnConfirmaFechas = adriver.findElement(AppiumBy.id("com.booking:id/facet_date_picker_confirm"));
		btnConfirmaFechas.click();
	}

	public void seleccionaCantidades() {
		// Action
		WebElement desLbl = adriver.findElement(AppiumBy.id("com.booking:id/facet_search_box_accommodation_occupancy"));
		desLbl.click();

		// Action: Habitacion
		UtilDelay.coolDelay(1*1000);
		WebElement containerHab = adriver.findElement(AppiumBy.id("com.booking:id/group_config_rooms_count"));
		WebElement cantHab = containerHab.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
		WebElement cantHabAdd = containerHab.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_add_button"));
		WebElement cantHabRem = containerHab.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_remove_button"));

		System.out.println("Cantidad Hab: " + cantHab.getText());

		int _cantHabitacion = Integer.parseInt(cantHab.getText());

		int intentoClickH = 1;
		while (_cantHabitacion != this.cantHabitacion && intentoClickH < 20) {
			if (_cantHabitacion < this.cantHabitacion) {
				cantHabAdd.click();
				System.out.println("Cantidad : Add" + cantHab.getText());
			}
			if (_cantHabitacion > this.cantHabitacion) {
				cantHabRem.click();
				System.out.println("Cantidad : Rem" + cantHab.getText());
			}
			cantHab = containerHab.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
			_cantHabitacion = Integer.parseInt(cantHab.getText());
			intentoClickH++;
		}
		// Action: Adultos
		WebElement containerAdult = adriver.findElement(AppiumBy.id("com.booking:id/group_config_adults_count"));
		WebElement cantAdu = containerAdult.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
		WebElement cantAduAdd = containerAdult.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_add_button"));
		WebElement cantAduRem = containerAdult.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_remove_button"));

		System.out.println("Cantidad Adu: " + cantAdu.getText());

		int _cantAdultos = Integer.parseInt(cantAdu.getText());

		int intentoClickA = 1;
		while (_cantAdultos != this.cantAdultos && intentoClickA < 20) {
			if (_cantAdultos < this.cantAdultos) {
				cantAduAdd.click();
				System.out.println("Cantidad : Add" + cantAdu.getText());
			}
			if (_cantAdultos > this.cantAdultos) {
				cantAduRem.click();
				System.out.println("Cantidad : Rem" + cantAdu.getText());
			}
			cantAdu = containerAdult.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
			_cantAdultos = Integer.parseInt(cantAdu.getText());
			intentoClickA++;
		}

	}
	
	public void seleccionaCantidadNinos() {
		// Action: Ninos
		WebElement containerNinos = adriver.findElement(AppiumBy.id("com.booking:id/group_config_children_count"));
		WebElement cantNinos = containerNinos.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
		WebElement cantNinosAdd = containerNinos.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_add_button"));
		WebElement cantNinosRem = containerNinos.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_remove_button"));
		
		System.out.println("Cantidad Ninos: " + cantNinos.getText());

		int _cantNinos = Integer.parseInt(cantNinos.getText());

		int intentoClickN = 1;
		while (_cantNinos != this.ninosEdad.size() && intentoClickN < 20) {
			if (_cantNinos < this.ninosEdad.size()) {
				cantNinosAdd.click();
				//System.out.println("Cantidad : Add" + cantNinos.getText());
				this.seleccionaEdadNinos(this.ninosEdad.get(intentoClickN - 1));
			}
			if (_cantNinos > this.ninosEdad.size()) {
				cantNinosRem.click();
				System.out.println("Cantidad : Rem" + cantNinos.getText());
			}
			containerNinos = adriver.findElement(AppiumBy.id("com.booking:id/group_config_children_count"));
			cantNinos = containerNinos.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
			_cantNinos = Integer.parseInt(cantNinos.getText());
			intentoClickN++;
		}
		
		// APLICAR CANTIDADES
		UtilDelay.coolDelay(2000);
		WebElement aplicarCantidades = adriver.findElement(AppiumBy.id("com.booking:id/group_config_apply_button"));
		aplicarCantidades.click();		
	}
	
	public void seleccionaEdadNinos(int edad) {
		
		UtilDriver.waitUntilVisible("com.booking:id/age_picker_view", 5);
		WebElement agePanel = adriver.findElement(AppiumBy.id("android:id/parentPanel"));
		WebElement ageSelected = agePanel.findElement(AppiumBy.id("android:id/numberpicker_input"));
		WebElement ageDown = agePanel.findElement(AppiumBy.xpath("//android.widget.Button[2]"));
		WebElement ageOK = agePanel.findElement(AppiumBy.id("android:id/button1"));
		WebElement ageNO = agePanel.findElement(AppiumBy.id("android:id/button2"));
		
		int maxIntent = 1;
		while(!ageSelected.getText().contains(String.valueOf(edad)) && maxIntent < 20) {
			System.out.println("Escogiendo edad ...");
			ageDown.click();
			ageSelected = agePanel.findElement(AppiumBy.id("android:id/numberpicker_input"));
			maxIntent++;
			
		}
		
		if(ageSelected.getText().contains(String.valueOf(edad))) {
			ageOK.click();
			System.out.println("Edad OK");
		} else {
			ageNO.click();
			System.out.println("Edad CANCEL");
		}
	}
	
	public void buscamosHoteles() {
		WebElement btnBuscar = adriver.findElement(AppiumBy.id("com.booking:id/facet_search_box_cta"));
		btnBuscar.click();
		UtilDelay.coolDelay(5 * 1000);
	}

}
