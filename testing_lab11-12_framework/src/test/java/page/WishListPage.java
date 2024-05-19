package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;
import java.util.List;

public class WishListPage extends AbstractPage{
    private final String WISH_LIST_PAGE_URL = "https://everydaycoffee.by/profile/wishlist/";
    @FindBy(xpath = "//div[@class='container']")
    private List<WebElement> listOfGoodsInWishList;
    public WishListPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WishListPage openPage() {
        driver.get(WISH_LIST_PAGE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public Boolean checkIfGoodExistsInWishList(String addedGoodPageUrl) {
        String urlPart = getAddedGoodPageNeededPartFromUrl(addedGoodPageUrl);

        for (WebElement good : listOfGoodsInWishList) {
            if (good.findElements(By.xpath("/descendant::a[@href='" + urlPart + "' and @class='primary_img']")).size() > 0) {
                return true;
            }
        }
        return false;
    }

    public void deleteGoodFromWishList(String addedGoodPageUrl) {
        String urlPart = getAddedGoodPageNeededPartFromUrl(addedGoodPageUrl);

        WebElement deleteGoodFromWishListBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='container']" +
                                "/descendant::a[@href='" + urlPart + "' and @class='primary_img']/ancestor::div" +
                                "/descendant::li[@class='wishlist']/a")));

        deleteGoodFromWishListBtn.click();
    }

    private String getAddedGoodPageNeededPartFromUrl(String addedGoodPageUrl) {
        String addedGoodPageUrlSubstring = "";
        if (addedGoodPageUrl != null) {
            addedGoodPageUrlSubstring = addedGoodPageUrl
                    .substring(addedGoodPageUrl.indexOf("/catalog"));
        }
        return addedGoodPageUrlSubstring;
    }
}
