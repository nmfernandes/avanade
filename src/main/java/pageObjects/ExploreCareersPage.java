package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by nnnn1 on 15/10/2017.
 */
public class ExploreCareersPage extends PageObject {
    public static final String TITLE = "EXPLORE_CAREERS_TITLE";
    public static final String COUNTRIES_DROPDOWN = "Countries dropdown";
    public static final String ROLES_DROPDOWN = "Roles dropdown";
    public static final String RESULTS_COUNT = "Results count";
    public static final String RESULTS_LIST = "Results list";
    WebElement searchboxfilter;
    WebElement countries;
    WebElement jobsearchclick;
    @FindBy(css = "div[class='search-meta-container']")
    WebElement resultCount;
    WebElement list3;
    WebElement list4;
    @FindBy(css = "div[class='search-results ng-scope']")
    WebElement resultList;



    public ExploreCareersPage(String session, String browser) throws Exception {
        super(session, browser);
        verifyPageOpenIsExpected(TITLE);
    }

    public void writeSearchBoxFilter(String text) {
        searchboxfilter.sendKeys(text);
    }

    public static String getText(WebElement element) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return jQuery(arguments[0]).text();", element);
    }

    public void selectCountry(String text) throws Exception {

        waitToBeVisible(list3, COUNTRIES_DROPDOWN);
        list3.click();
        WebElement item = findByTextInContainerGivenParentElement(text, list3, COUNTRIES_DROPDOWN);
        item.click();
    }

    public void selectRole(String text) throws Exception {
        waitToBeVisible(list4, ROLES_DROPDOWN);
        list4.click();
        WebElement item = findByTextInContainerGivenParentElement(text, list4, ROLES_DROPDOWN);
        item.click();
    }


    public void clickJobSearchBtn() {
        jobsearchclick.click();
    }

    public int getNumberResults() throws Exception {
        waitToBeVisible(resultCount, RESULTS_COUNT);
        String text = resultCount.getText();
        String[] splitted = text.split(" ");
        return Integer.parseInt(splitted[0]);
    }

    public void clickFirstResult() throws Exception {
        waitToBeVisible(resultList, RESULTS_LIST);
        WebElement link = resultList.findElement(By.xpath(".//td/a[2]"));
        link.click();
    }

}
