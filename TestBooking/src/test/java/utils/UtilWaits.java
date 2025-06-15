package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

public class UtilWaits {

	public static void waitUntilVisible(AppiumDriver driver, By locator, Integer seconds) {
		WebDriverWait wdriver = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wdriver.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitUntilPresenceVisible(AppiumDriver driver, By locator, Integer seconds) {
		WebDriverWait wdriver = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wdriver.until(ExpectedConditions.presenceOfElementLocated(locator));
		wdriver.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitUntilFound(AppiumDriver driver, By locator, int maxAttempts, int secondsPerTry) {
		for (int i = 0; i < maxAttempts; i++) {
			try {
				driver.getPageSource(); // fuerza a Appium a actualizar el DOM
				List<WebElement> elements = driver.findElements(locator);
				if (elements != null && !elements.isEmpty()) {
					WebElement el = elements.get(0);
					if (el.isDisplayed())
						return;
				}
			} catch (Exception e) {
				// opcional: loggea el intento fallido
			}
		}
		throw new NoSuchElementException("Elemento no encontrado tras " + maxAttempts + " intentos.");
	}

	public static void waitUntilFound(AppiumDriver driver, By locator) {
		waitUntilFound(driver, locator, 10, 2);
	}

	public static void waitSecondsImplicitly(AppiumDriver driver, Integer seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public static void waitSeconds(int seconds) {

		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
