package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class UtilLogs {

    public static void printPageSource(AppiumDriver driver) {
        System.out.println("\n>BEGIN---------------------\n");
        System.out.println(driver.getPageSource().replaceAll("\n",""));
        System.out.println("\n>END-----------------------\n");
    }

    public static void printContext(AndroidDriver driver) {
        System.out.println("\n> Contexto: " + driver.getContext() + "\n");
    }
}
