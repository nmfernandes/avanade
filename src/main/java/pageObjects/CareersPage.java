package pageObjects;

import helpers.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by nnnn1 on 15/10/2017.
 */
public class CareersPage extends PageObject{
    public static final String TITLE = "CAREERS_TITLE";
    @FindBy(xpath = "//*[contains(text(), 'Explore Avanade Careers')]")
    WebElement exploreCareersBtn;

    public CareersPage(String session, String browser) throws Exception {
        super(session, browser);
        verifyPageOpenIsExpected(TITLE);
    }

    public void clickOnExploreCareersBtn(){
        exploreCareersBtn.click();
    }

}
