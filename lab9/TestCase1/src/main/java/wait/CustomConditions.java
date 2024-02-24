package wait;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {
    public static ExpectedCondition<Boolean> jQueryAJAXCompleted(){
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return (Boolean) ((JavascriptExecutor)
                        webDriver).executeScript("return window.jQuery" +
                        "!=null && (jQuery.active === 0);");
            }
        };
    }
    public static ExpectedCondition<Boolean> isButtonEnabled(WebElement button) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return button.isEnabled();
            }
        };
    }

}
