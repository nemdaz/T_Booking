package po;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import utils.UtilWaits;

public class HomePage extends BasePage {
    public HomePage() {
        super();
    }

    public void omitePantallaNofiticacion() {
        UtilWaits.waitUntilFound(adriver, AppiumBy.id("com.booking:id/action_bar_root"));
        WebElement btnNotNow = adriver.findElementUntilFound(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[4]/android.widget.Button"));
        if (btnNotNow.isDisplayed()) {
            btnNotNow.click();
        }
    }

    // Nota: Para booking.com la pantalla de login no es obligatoria al principio.
    public void omitePantallaLogin() {
        UtilWaits.waitUntilFound(adriver, AppiumBy.id("com.booking:id/decor_content_parent"));
        WebElement btnCloseX = adriver.findElementUntilFound(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"Desplazarse hacia arriba\"]"));
        if (btnCloseX.isDisplayed()) {
            btnCloseX.click();
        }
    }
}
