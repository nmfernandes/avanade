package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page class of the Careers page
 * Extends PageObject class
 */
public class CareersPage extends PageObject{
    private static final String TITLE = "CAREERS_TITLE";
    private static final String EXPLORE_AVANADE_CAREERS = "Explore Avanade Careers";
    @FindBy(xpath = "//*[contains(text(), 'Explore Avanade Careers')]")
    private WebElement exploreCareersBtn;

    public CareersPage(String session, String browser) throws Exception {
        super(session, browser);
        verifyPageOpenIsExpected(TITLE);
    }

    /**
     * Click on Explore Careers Button
     * @throws Exception
     */
    public void clickOnExploreCareersBtn() throws Exception {
        waitToBeClickable(exploreCareersBtn, EXPLORE_AVANADE_CAREERS);
        exploreCareersBtn.click();
    }

}
