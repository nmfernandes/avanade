package pageObjects;

import helpers.Drivers;
import helpers.ExpectedConditionParentWebElementChildBy;
import helpers.MessageFileToUser;
import helpers.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class PageObject {
    public static final int DEFAULT_WAIT_TIMEOUT = 10;
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
        } catch (Exception ex) {
            throw new IllegalStateException(String.format(MessageFileToUser.WRONG_PAGE, expectedTitle, driver.getTitle()));
        }
    }

    public WebElement findByTextInContainerGivenParentElement(String text, WebElement parent, String parentDesc) throws Exception {

        try {
            return (WebElement) wait.until(new ExpectedConditionParentWebElementChildBy(parent,
                    By.xpath(".//*[contains(text(),'" + text + "')]")));
        } catch (Exception ex) {

            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_PRESENT_CONTAINER, text, parentDesc));
        }
    }

    public List<WebElement> findListElementsWithTimeout(By by, String identifier) throws Exception {

        try {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ex) {
            } finally {
                return driver.findElements(by);
            }
        } catch (Exception e) {

            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_VISIBLE, identifier));
        }
    }

    public WebElement findByText(String key) throws Exception {
        String text = Utils.getResourceBundle().getString(key);
        String xpathQuery = "//*[contains(text(), '" + text + "')]";
        if (key.indexOf("'") > 0) {
            // In case the string has an '
            // XPath 1.0 doesn't suppport \'
            xpathQuery = "//*[contains(text(), \"" + text + "\")]";
        }
        WebElement w = findListElementsWithTimeout(By.xpath(xpathQuery), text).stream()
                .filter(WebElement::isDisplayed).findFirst().orElse(null);
        if (w == null) {
            throw new Exception(String.format(MessageFileToUser.TEXT_NOT_PRESENT, text));
        }
        return w;
    }

    protected WebElement waitToBeClickable(WebElement element, String identifier) throws Exception {

        try {
            return new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_CLICKABLE, identifier));
        }
    }

    protected WebElement waitToBeVisible(WebElement element, String identifier) throws Exception {

        try {
            return new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_VISIBLE, identifier));
        }
    }
}