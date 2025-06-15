package wrapper;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import utils.UtilLogs;

public class AndroidDriverX extends AndroidDriver {

    int maxAttempts = 10; // Default maximum attempts to find an element
    int secondsPerTry = 2; // Default seconds to wait per try

    public AndroidDriverX(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    public void setConfigRetry(int maxAttempts, int secondsPerTry) {
        this.maxAttempts = maxAttempts;
        this.secondsPerTry = secondsPerTry;
    }

    public List<WebElement> findElementsUntilFound(By locator) {
        for (int i = 0; i < this.maxAttempts; i++) {
            try {
                getPageSource(); // fuerza actualización del DOM, util solo para selectores basados en DOM
                System.out.println("\n> Locator: " + locator.toString() + "\n");
                List<WebElement> elements = findElements(locator);
                if (!elements.isEmpty()) {
                    return elements;
                }
                Thread.sleep(this.secondsPerTry * 1000L); // espera entre intentos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                // opcional: log de errores
            }
        }
        throw new NoSuchElementException("Elements not found after " + this.maxAttempts + " attempts.");
    }

    public WebElement findElementUntilFound(By locator) {
        List<WebElement> elements = findElementsUntilFound(locator); // Llama al método para obtener los elementos
        WebElement element = elements.get(0);
        if (element.isDisplayed()) {
            return element; // éxito
        }
        throw new NoSuchElementException("Element not found after " + this.maxAttempts + " attempts.");
    }

}
