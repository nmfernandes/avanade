package pageObjects;

import helpers.Drivers;
import helpers.MessageFileToUser;
import helpers.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PageObject {
    public static final int DEFAULT_WAIT_TIMEOUT = 30;
    public static WebDriver driver;
    static WebDriverWait wait;


    public PageObject() {
        try {
            this.driver = Drivers.loadDriver();
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        constructorSupport();
    }

    public PageObject(String session, String browser) {
        try {
            this.driver = Drivers.createOrLoadDriver(session, browser);
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        constructorSupport();
    }

    public void constructorSupport() {

        wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT);
    }

    public void verifyPageOpenIsExpected(String title) {
        String expectedTitle = Utils.getResourceBundle().getString(title);
        try {
            wait.until(ExpectedConditions.titleIs(expectedTitle));
        }catch(Exception ex) {
            throw new IllegalStateException(String.format(MessageFileToUser.WRONG_PAGE, expectedTitle,driver.getTitle()));
        }
    }
}