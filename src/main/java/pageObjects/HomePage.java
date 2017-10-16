package pageObjects;

import helpers.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by nnnn1 on 15/10/2017.
 */
public class HomePage extends PageObject{
    public static final String TITLE = "HOME_TITLE";
    public static final String EXPLORE_CAREERS_BUTTON = "Explore Careers button";
    @FindBy(xpath = "//*[@title='Explore careers']")
    WebElement exploreCareersBtn;

    public HomePage(String session, String browser) throws Exception {
        super(session, browser);

        String urlPortal = Utils.getData("Endpoints", "AVANADE");

        try {
            driver.get(urlPortal);
            driver.navigate().to("javascript:document.getElementById('invalidcert_continue').click()");
        } catch (Exception nothing) {
            System.out.println(nothing.toString());
        } finally {
        }

        verifyPageOpenIsExpected(TITLE);
    }

    public void clickOnExploreCareersBtn() throws Exception {
        waitToBeClickable(exploreCareersBtn, EXPLORE_CAREERS_BUTTON);
        exploreCareersBtn.click();
    }

}
