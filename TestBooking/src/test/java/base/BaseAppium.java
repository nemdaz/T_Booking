package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import wrapper.AndroidDriverX;

public class BaseAppium {

	public static AppiumDriverLocalService aservice;
	public static AndroidDriverX adriver;
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

	// INSTALL APPS
	public void installApp() {

		String pkg = "com.booking";
		String expectedVersion = "56.6";

		System.out.println("> Instalando/Reinstalando App Booking.com");

		String pathAssets = System.getProperty("user.dir") + "/../assets";
		String cmd_uninstall = "adb uninstall " + pkg;
		String cmd_install = "adb install-multiple -t {assets}/Booking.com-56.6.apk {assets}/config.arm64_v8a.apk {assets}/config.xxhdpi.apk";
		cmd_install = cmd_install.replace("{assets}", pathAssets);

		try {

			// Verificar si la app está instalada
			Process checkInstalled = Runtime.getRuntime().exec("adb shell pm list packages " + pkg);
			BufferedReader reader = new BufferedReader(new InputStreamReader(checkInstalled.getInputStream()));
			if (reader.readLine() == null) {
				System.out.println("> App no está instalada.");
			} else {
				// Verificar versión actual
				Process versionCheck = Runtime.getRuntime().exec("adb shell dumpsys package " + pkg);
				BufferedReader vReader = new BufferedReader(new InputStreamReader(versionCheck.getInputStream()));
				String line;

				while ((line = vReader.readLine()) != null) {
					if (line.contains("versionName=")) {
						System.out.println("> Versión actual de la app: " + line.trim());
						break;
					}
				}

				if (line != null && line.contains(expectedVersion)) {
					System.out.println("> App ya instalada con versión correcta (" + expectedVersion + ").");
					Runtime.getRuntime().exec("adb shell pm clear " + pkg).waitFor();
					System.out.println("> Se ha limpiado la caché de la app.");
					return;
				}
				System.out.println("> Desinstalando versión existente...");
				Runtime.getRuntime().exec(cmd_uninstall).waitFor();
				System.out.println("> Desinstalación existente de la APK completada.");
			}

			// Instalar la versión deseada
			System.out.println("> Instalando versión " + expectedVersion + "...");
			System.out.println("> cmd: " + cmd_install);
			int exitCode = Runtime.getRuntime().exec(cmd_install).waitFor();

			if (exitCode != 0) {
				throw new RuntimeException("Falló la instalación de la APK. Código de salida: " + exitCode);
			}

			System.out.println("> Instalación de la APK completada.");

		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Error durante la instalación de la APK: " + e.getMessage(), e);
		}
	}

	// START SERVICE
	public void startDriver() {

		try {
			// Driver
			adriver = new AndroidDriverX(new URL("http://" + IP + ":" + PORT), cap);

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
