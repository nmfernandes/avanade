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

/**
 * Contains methods that can be used in any Page class
 */
public class PageObject {
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    static WebDriver driver;
    private static WebDriverWait wait;

    PageObject(String session, String browser) {
        try {
            driver = Drivers.createOrLoadDriver(session, browser);

            /**
             * The PageFactory Class is an extension to the Page Object design pattern.
             * It is used to initialize the elements of the Page Object or instantiate the Page Objects itself.
             */
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        constructorSupport();
    }

    private void constructorSupport() {
        wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT);
    }

    /**
     * Verify if the correct page was open
     * @param title
     */
    void verifyPageOpenIsExpected(String title) {
        String expectedTitle = Utils.getResourceBundle().getString(title);
        try {
            wait.until(ExpectedConditions.titleIs(expectedTitle));
        } catch (Exception ex) {
            throw new IllegalStateException(String.format(MessageFileToUser.WRONG_PAGE, expectedTitle,
                    driver.getTitle()));
        }
    }

    /**
     * Given an element, search for a given text in the child nodes of that element
     * @param text
     * @param parent
     * @param parentDesc
     * @return
     * @throws Exception
     */
    WebElement findByTextInContainerGivenParentElement(String text, WebElement parent, String parentDesc) throws Exception {

        try {
            return (WebElement) wait.until(new ExpectedConditionParentWebElementChildBy(parent,
                    By.xpath(".//*[contains(text(),'" + text + "')]")));
        } catch (Exception ex) {

            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_PRESENT_CONTAINER, text, parentDesc));
        }
    }

    /**
     * Given a By parameter, wait that the element is visible
     * @param by
     * @param identifier
     * @return
     * @throws Exception
     */
    private List<WebElement> findListElementsWithTimeout(By by, String identifier) throws Exception {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return driver.findElements(by);
        } catch (Exception e) {
            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_VISIBLE, identifier));
        }
    }

    /**
     * Find a given text
     * @param key
     * @return
     * @throws Exception
     */
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

    /**
     * Wait that an element is clickable
     * @param element
     * @param identifier
     * @return
     * @throws Exception
     */
    WebElement waitToBeClickable(WebElement element, String identifier) throws Exception {

        try {
            return new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_CLICKABLE, identifier));
        }
    }

    /**
     * Wait that an element is visible
     * @param element
     * @param identifier
     * @return
     * @throws Exception
     */
    WebElement waitToBeVisible(WebElement element, String identifier) throws Exception {

        try {
            return new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            throw new Exception(String.format(MessageFileToUser.ELEMENT_NOT_VISIBLE, identifier));
        }
    }
}