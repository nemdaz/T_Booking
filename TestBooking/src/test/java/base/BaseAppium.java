package base;

import java.net.URL;
import java.util.Locale;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseAppium {

	public static AppiumDriverLocalService aservice;
	//public static AppiumDriver adriver;
	public static AndroidDriver adriver;
	protected DesiredCapabilities cap;
	protected String IP;
	protected Integer PORT;

	public BaseAppium() {		
		IP = "127.0.0.1";
		PORT = 4723;
		//
		cap = new DesiredCapabilities();
		cap.setCapability("automationName", "UiAutomator2");
		cap.setCapability("udid", "a58c4cdf");
		//cap.setCapability("udid", "HA1M6XDH"); 
		cap.setCapability("deviceName", "Xiaomi");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "13.0.0");
		cap.setCapability("appPackage", "com.booking");
		cap.setCapability("appActivity", "com.booking.appindex.presentation.activity.SearchActivity");
		cap.setCapability("ignoreHiddenApiPolicyError", true);
		// -- Enable Keyboard
		cap.setCapability("unicodeKeyboard", false); //If false enable show keyboard
		cap.setCapability("resetKeyboard", false); //If false enable show keyboard
	}
	
	//START SERVER
	public void startService() {

		try {
			// Service
			AppiumServiceBuilder abuilder = new AppiumServiceBuilder()//.withCapabilities(cap)
					.withIPAddress(IP).usingPort(PORT);
			aservice = abuilder.build();
			aservice.start();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("No se puede inciar el servicio");
		}
	}

	//START SERVICE
	public void startDriver() {

		try {		
			// Driver
			//adriver = new AppiumDriver(new URL("http://" + IP + ":" + PORT), cap);
			adriver = new AndroidDriver(new URL("http://" + IP + ":" + PORT), cap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("No nos podemos conectar al servicio");
		}

	}

	public void shutDown() {
		if (adriver != null)
			adriver.quit();
	}

}
