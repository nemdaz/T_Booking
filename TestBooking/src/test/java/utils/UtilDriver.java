package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseAppium;
import io.appium.java_client.AppiumBy;

public class UtilDriver extends BaseAppium {
	public static void waitUntilVisible(String selectorId, Integer seconds) {
		WebDriverWait wdriver = new WebDriverWait((WebDriver) adriver, Duration.ofSeconds(seconds));
		wdriver.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(selectorId)));
	}
}
