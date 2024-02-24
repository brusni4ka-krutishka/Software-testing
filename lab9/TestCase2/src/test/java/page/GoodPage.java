package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import waits.CustomConditions;

import java.time.Duration;

public class GoodPage {
    WebDriver driver;
    private final String GOOD_PAGE_URL =
            "https://everydaycoffee.by/catalog/kupazhi/mo-lo-ko-fe/";

    @FindBy(xpath="//button[@class='button product__cart-btn']")
    private WebElement addInBasketButton;

    public GoodPage(WebDriver driver){
        this.driver = driver;
    }

    public void openPage(){
        driver.get(GOOD_PAGE_URL);
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3000), this);
    }

    public String addGoodInBasket(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", addInBasketButton);
        return GOOD_PAGE_URL;
    }
}
