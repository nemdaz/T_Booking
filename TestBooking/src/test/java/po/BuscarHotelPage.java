package po;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumBy;
import utils.UtilLogs;
import utils.UtilWaits;
import utils.Utils;

public class BuscarHotelPage extends BasePage {
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
		String xpathContentCompose = "//androidx.compose.ui.platform.ComposeView";
		String xpathSearchContainer = xpathContentCompose
				+ "/android.view.View/android.widget.ScrollView/android.view.View[1]";
		String xpathDestLbl = xpathSearchContainer
				+ "/android.view.View[@index='0' and @clickable='true' and @focusable='true']";

		// Wait
		UtilWaits.waitUntilVisible(adriver, AppiumBy.xpath(xpathSearchContainer), 3);

		// Action: Selecciona el campo de destino
		WebElement destLbl = adriver.findElement(AppiumBy.xpath(xpathDestLbl));
		destLbl.click();

		// Action: Ingresa el destino
		WebElement desTxt = adriver.findElement(AppiumBy.xpath("//android.widget.EditText"));
		desTxt.sendKeys(this.destino);
	}

	public void seleccionaOpcionesDestino(int index) {
		// Wait: esperamos que se cargue la lista de opciones
		UtilWaits.waitSeconds(2);

		String xpathOpts = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]";
		String xpathOptsElems = xpathOpts + "/android.view.View";

		// Wait
		UtilWaits.waitUntilFound(adriver, AppiumBy.xpath(xpathOpts));

		// Action
		List<WebElement> optDestinos = adriver.findElements(AppiumBy.xpath(xpathOptsElems));
		optDestinos.get(index).click();

	}

	public void seleccionaFechas() {
		String iF = "dd/MM/yyyy";
		// String sF = "dd MMMM yyyy";
		// String sF = "EEEE, d 'de' MMMM 'de' yyyy";
		String sF = "EEEE, d MMMM yyyy";
		Date dateCheckin = Utils.dateFromString(this.fecIngreso, iF);
		Date dateCheckout = Utils.dateFromString(this.fecSalida, iF);
		String checkin = Utils.dateChangeFormat(this.fecIngreso, iF, sF);
		String checkout = Utils.dateChangeFormat(this.fecSalida, iF, sF);

		long difIni = Utils.dateDiferenceDays(Utils.dateFromString(this.fecIngreso, iF), new Date());
		if (difIni < 0) {
			dateCheckin = Utils.dateAddDays(dateCheckin, Math.abs(difIni));
			System.out.printf("La fecha de Ingreso es menor a la actual, se cambia ... %s\n", dateCheckin);
			checkin = Utils.dateToString(dateCheckin, sF);
		}
		Map<String, String> dateMapCheckin = Utils.dateToMap(dateCheckin);
		Map<String, String> dateMapCheckout = Utils.dateToMap(dateCheckout);

		// Action: Identificamos el contenedor del calendario
		String selcCalendarSheet = "new UiSelector().resourceId(\"com.booking:id/design_bottom_sheet\")";
		UtilWaits.waitUntilFound(adriver, AppiumBy.androidUIAutomator(selcCalendarSheet));

		// Action: Identificamos y selecionamos los elementos fecha checkin y checkout
		String regexFI = ".*\\b%s\\b.*\\b(de )?%s\\b.*\\b%s\\b.*"
				.formatted(dateMapCheckin.get("d"), dateMapCheckin.get("MMMM"), dateMapCheckin.get("yyyy"));
		String regexFF = ".*\\b%s\\b.*\\b(de )?%s\\b.*\\b%s\\b.*"
				.formatted(dateMapCheckout.get("d"), dateMapCheckout.get("MMMM"), dateMapCheckout.get("yyyy"));
		String selecFechaMesI = "new UiSelector().descriptionMatches(\"%s\")".formatted(regexFI);
		String selecFechaMesF = "new UiSelector().descriptionMatches(\"%s\")".formatted(regexFF);

		WebElement fechaMesI = adriver.findElementUntilFound(AppiumBy.androidUIAutomator(selecFechaMesI));
		WebElement fechaMesF = adriver.findElementUntilFound(AppiumBy.androidUIAutomator(selecFechaMesF));

		fechaMesI.click();
		fechaMesF.click();

		UtilWaits.waitUntilFound(adriver, AppiumBy.id("com.booking:id/facet_date_picker_confirm"));
		WebElement btnConfirmaFechas = adriver.findElement(AppiumBy.id("com.booking:id/facet_date_picker_confirm"));
		btnConfirmaFechas.click();
	}

	public void seleccionaCantidades() {

		// Action: Selecciona que muestre el panel de cantidades
		String xpathCantLbl = "//android.widget.ScrollView//android.view.View[@index='0']/android.view.View[@index='2' and @clickable='true' and @focusable='true']";
		UtilWaits.waitUntilFound(adriver, AppiumBy.xpath(xpathCantLbl));
		WebElement cantLbl = adriver.findElement(AppiumBy.xpath(xpathCantLbl));
		cantLbl.click();

		// Wait: Esperamos que se cargue el panel de cantidades
		String selCantSheet = "new UiSelector().resourceId(\"com.booking:id/design_bottom_sheet\")";
		UtilWaits.waitUntilFound(adriver, AppiumBy.androidUIAutomator(selCantSheet));

		// Action: Identificamos elementos de cantidades de habitaciones y seleccionamos
		String selCantConfig = "new UiSelector().resourceId(\"com.booking:id/group_config_rooms_count\")";
		WebElement containerHab = adriver.findElementUntilFound(AppiumBy.androidUIAutomator(selCantConfig));
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
		// Action: Identificamos elementos de cantidades de adultos y seleccionamos
		WebElement containerAdult = adriver.findElement(AppiumBy.id("com.booking:id/group_config_adults_count"));
		WebElement cantAdu = containerAdult.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_value"));
		WebElement cantAduAdd = containerAdult.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_add_button"));
		WebElement cantAduRem = containerAdult
				.findElement(AppiumBy.id("com.booking:id/bui_input_stepper_remove_button"));

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

		String xpathContainerNinos = "com.booking:id/group_config_children_count";
		String xpathCantNinos = "com.booking:id/bui_input_stepper_value";
		String xpathAddNinos = "com.booking:id/bui_input_stepper_add_button";
		String xpathRemNinos = "com.booking:id/bui_input_stepper_remove_button";

		// Action: Ninos
		WebElement containerNinos = adriver.findElement(AppiumBy.id(xpathContainerNinos));
		WebElement cantNinos = containerNinos.findElement(AppiumBy.id(xpathCantNinos));
		WebElement addNinos = containerNinos.findElement(AppiumBy.id(xpathAddNinos));
		WebElement remNinos = containerNinos.findElement(AppiumBy.id(xpathRemNinos));

		System.out.println("Cantidad Ninos: " + cantNinos.getText());

		int icantNinos = Integer.parseInt(cantNinos.getText());

		int intentoClickN = 1;
		while (icantNinos != this.ninosEdad.size() && intentoClickN < 20) {
			if (icantNinos < this.ninosEdad.size()) {
				addNinos.click();
				// System.out.println("Cantidad : Add" + cantNinos.getText());
				this.seleccionaEdadNinos(this.ninosEdad.get(intentoClickN - 1));
			}
			if (icantNinos > this.ninosEdad.size()) {
				remNinos.click();
				System.out.println("Cantidad : Rem" + cantNinos.getText());
			}
			containerNinos = adriver.findElement(AppiumBy.id(xpathContainerNinos));
			cantNinos = containerNinos.findElement(AppiumBy.id(xpathCantNinos));
			icantNinos = Integer.parseInt(cantNinos.getText());
			intentoClickN++;
		}

		// APLICAR CANTIDADES
		UtilWaits.waitSeconds(2);
		WebElement aplicarCantidades = adriver.findElement(AppiumBy.id("com.booking:id/group_config_apply_button"));
		aplicarCantidades.click();
	}

	public void seleccionaEdadNinos(int edad) {
		String xpathSeccionChild = "com.booking:id/group_config_children_ages_section";
		String xpathPanelChild = "com.booking:id/parentPanel";

		// Wait: Esperamos que se muestre la sección de selección de edad de los niños
		UtilWaits.waitUntilVisible(adriver, AppiumBy.id(xpathSeccionChild), 3);

		// Action: Seleciona el campo de selección de edad de los niños, abre el popup
		// de selección
		WebElement openPopEdadNinos = adriver.findElement(AppiumBy.id("com.booking:id/bui_input_container_background"));
		openPopEdadNinos.click();

		// Wait: Esperamos que se muestre el panel de selección de edad de los niños
		UtilWaits.waitUntilVisible(adriver, AppiumBy.id(xpathPanelChild), 3);

		// Action: Identificamos los elementos del panel de selección de edad
		WebElement agePanel = adriver.findElement(AppiumBy.id(xpathPanelChild));
		WebElement ageUp = agePanel.findElement(AppiumBy.xpath("//android.widget.Button[1]"));
		WebElement ageSelected = agePanel.findElement(AppiumBy.id("android:id/numberpicker_input"));
		WebElement ageDown = agePanel.findElement(AppiumBy.xpath("//android.widget.Button[2]"));
		WebElement ageOK = agePanel.findElement(AppiumBy.id("android:id/button1"));
		WebElement ageNO = agePanel.findElement(AppiumBy.id("android:id/button2"));

		int maxIntent = 1;
		while (!ageSelected.getText().contains(String.valueOf(edad)) && maxIntent < 20) {
			System.out.println("Escogiendo edad ...");
			ageDown.click();
			ageSelected = agePanel.findElement(AppiumBy.id("android:id/numberpicker_input"));
			maxIntent++;
		}

		if (ageSelected.getText().contains(String.valueOf(edad))) {
			ageOK.click();
			System.out.println("Edad OK");
		} else {
			ageNO.click();
			System.out.println("Edad CANCEL");
		}
	}

	public void buscamosHoteles() {
		UtilWaits.waitSeconds(2);

		String xpathContentCompose = "//androidx.compose.ui.platform.ComposeView";
		String xpathSearchContainer = xpathContentCompose
				+ "/android.view.View/android.widget.ScrollView/android.view.View[1]";
		String xpathBtnSearch = xpathSearchContainer + "/android.view.View[4]/android.widget.Button";

		UtilWaits.waitUntilPresenceVisible(adriver, AppiumBy.xpath(xpathContentCompose), 2);
		UtilWaits.waitUntilPresenceVisible(adriver, AppiumBy.xpath(xpathSearchContainer), 2);

		WebElement btnBuscar = adriver.findElement(AppiumBy.xpath(xpathBtnSearch));
		btnBuscar.click();
	}

}
