package demo;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.*;;

public class TestBookingBasic {

	public static void main(String[] args) throws Exception {

		startApp();

	}

	public static void startApp() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		// Obligatorio para Appium 2 - Nombre del Driver
		cap.setCapability("automationName", "UiAutomator2");
		//
		cap.setCapability("udid", "a58c4cdf");
		cap.setCapability("deviceName", "Xiaomi");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "13.0.0");
		cap.setCapability("appPackage", "com.booking");
		cap.setCapability("appActivity", "com.booking.appindex.presentation.activity.SearchActivity");
		// cap.setCapability("appActivity", "com.booking.login.LoginActivity");
		// cap.setCapability("appPackage", "com.xiaomi.scanner");
		// cap.setCapability("appActivity", "com.xiaomi.scanner.app.ScanActivity");
		cap.setCapability("ignoreHiddenApiPolicyError", true);

		// Appium 1.XX
		// AppiumDriver adriver = new AppiumDriver(new
		// URL("http://127.0.0.1:4723/wd/hub"), cap);
		// Appium 2.XX
		AppiumDriver adriver = new AppiumDriver(new URL("http://127.0.0.1:4723"), cap);

		System.out.println("God, will have to wait for a minute now.");
		UtilDelay.coolDelay(5000);
		System.out.println("God, waited for ... now.");

		try {
			System.out.println("TRY");
			WebElement destiny_lbl = adriver
					.findElement(AppiumBy.id("com.booking:id/facet_search_box_basic_field_label"));
			destiny_lbl.click();
			WebElement destiny_txt = adriver.findElement(
					AppiumBy.id("com.booking:id/facet_with_bui_free_search_booking_header_toolbar_content"));
			destiny_txt.sendKeys("cusco");
			// adriver.getPageSource();
		} finally {
			UtilDelay.coolDelay(15000);
			System.out.println("FINALLY");
			adriver.quit();
		}

	}

}
