package utils;

import java.time.Duration;

import org.openqa.selenium.By;
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

	public static void waitSeconds(int seconds) {

		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
