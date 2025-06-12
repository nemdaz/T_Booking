package base;

import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseAppium {

	public static AppiumDriverLocalService aservice;
	public static AndroidDriver adriver;
	protected DesiredCapabilities cap;
	protected String IP;
	protected Integer PORT;

	public BaseAppium() {
		IP = "127.0.0.1";
		PORT = 4723;
		//
		cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("appium:automationName", "UiAutomator2");
		cap.setCapability("appium:udid", "ZY22GM3QXD"); // Check your device ID with 'adb devices'
		cap.setCapability("appium:deviceName", "SmartphoneTest");
		cap.setCapability("appium:platformVersion", "14.0.0");
		cap.setCapability("appium:appPackage", "com.booking");
		cap.setCapability("appium:appActivity", "com.booking.startup.HomeActivity");
		cap.setCapability("appium:ignoreHiddenApiPolicyError", true);
		// -- Enable Keyboard
		cap.setCapability("appium:unicodeKeyboard", false); // If false enable show keyboard
		cap.setCapability("appium:resetKeyboard", false); // If false enable show keyboard
	}

	// START SERVER
	public void startService() {

		try {
			// Service
			AppiumServiceBuilder abuilder = new AppiumServiceBuilder()// .withCapabilities(cap)
					.withIPAddress(IP).usingPort(PORT);
			aservice = abuilder.build();
			aservice.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("No se puede inciar el servicio");
		}
	}

	// START SERVICE
	public void startDriver() {

		try {
			// Driver
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
