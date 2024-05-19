package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;

public class GoodPage extends AbstractPage{
    private final String GOOD_PAGE_URL =
            "https://everydaycoffee.by/catalog/kupazhi/mo-lo-ko-fe/";

    @FindBy(xpath="//button[@class='button product__cart-btn']")
    private WebElement addInBasketButton;

    @FindBy(xpath = "//li[@class='wishlist']/a")
    private WebElement addInWishListButton;

    public GoodPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3000), this);
    }

    public GoodPage openPage(){
        driver.get(GOOD_PAGE_URL);
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public String addGoodInBasket(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", addInBasketButton);
        return GOOD_PAGE_URL;
    }

    public String addGoodInWishList(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", addInWishListButton);
        return GOOD_PAGE_URL;
    }
}
