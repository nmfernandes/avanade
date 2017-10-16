package pageObjects;

import helpers.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page class of the Home page
 * Extends PageObject class
 */
public class HomePage extends PageObject{
    private static final String TITLE = "HOME_TITLE";
    private static final String EXPLORE_CAREERS_BUTTON = "Explore Careers button";
    @FindBy(xpath = "//*[@title='Explore careers']")
    private WebElement exploreCareersBtn;

    public HomePage(String session, String browser) throws Exception {
        super(session, browser);

        String urlPortal = Utils.getData("Endpoints", "AVANADE");

        try {
            driver.get(urlPortal);
            driver.navigate().to("javascript:document.getElementById('invalidcert_continue').click()");
        } catch (Exception nothing) {
            System.out.println(nothing.toString());
        }

        verifyPageOpenIsExpected(TITLE);
    }


    /**
     * Click on Explore Careers button
     * @throws Exception
     */
    public void clickOnExploreCareersBtn() throws Exception {
        waitToBeClickable(exploreCareersBtn, EXPLORE_CAREERS_BUTTON);
        exploreCareersBtn.click();
    }

}
