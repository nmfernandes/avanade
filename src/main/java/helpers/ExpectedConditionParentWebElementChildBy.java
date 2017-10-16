package helpers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExpectedConditionParentWebElementChildBy implements ExpectedCondition {
    By childLocator;
    WebElement parent;

    public ExpectedConditionParentWebElementChildBy(WebElement parent, By childLocator) {
        this.childLocator = childLocator;
        this.parent = parent;
    }

    @Override
    public Object apply(Object o) {
        WebElement result = parent.findElement(childLocator);

        return result;
    }
}
