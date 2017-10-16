package helpers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExpectedConditionParentWebElementChildBy implements ExpectedCondition {
    private By childLocator;
    private WebElement parent;

    public ExpectedConditionParentWebElementChildBy(WebElement parent, By childLocator) {
        this.childLocator = childLocator;
        this.parent = parent;
    }

    @Override
    public Object apply(Object o) {
        return parent.findElement(childLocator);
    }
}
