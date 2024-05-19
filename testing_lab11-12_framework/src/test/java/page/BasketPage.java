package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;
import java.util.List;

public class BasketPage extends AbstractPage{
    private final String BASKET_PAGE_URL = "https://everydaycoffee.by/cart/";
    @FindBy(xpath = "//table[@id='basket-item-table']/tbody/tr")
    private List<WebElement> listOfGoodsInBasket;
    public BasketPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BasketPage openPage() {
        driver.get(BASKET_PAGE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public Boolean checkIfGoodExistsInBasket(String addedGoodPageUrl) {
        String urlPart = getAddedGoodPageNeededPartFromUrl(addedGoodPageUrl);

        // listOfGoodsInBasket - список tr (строк) таблицы, т.е. добавленных в корзину товаров
        for (WebElement good : listOfGoodsInBasket) {
            if (good.findElements(By.xpath("/descendant::a[@href='" + urlPart
                    + "' and @class='basket-item-image-link']")).size() > 0) {
                // если в tr найдена ссылка (a), ссылающаяся (href) на страницу добавленного товара, возвращаем true
                return true;
            }
        }
        return false;
    }

    public void deleteGoodFromBasket(String addedGoodPageUrl) {
        String urlPart = getAddedGoodPageNeededPartFromUrl(addedGoodPageUrl);

        // чтобы крестик (кнопка удаления) появился, нужно навести курсор на товар
        Actions actions = new Actions(driver);
        WebElement good = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//table[@id='basket-item-table']/descendant::a[@href='" + urlPart + "']/ancestor::tr")));
        actions.moveToElement(good).perform();

        // теперь можем удалить товар из корзины
        WebElement deleteGoodFromBasketBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//table[@id='basket-item-table']/" +
                                "descendant::a[@href='" + urlPart + "']/ancestor::tr" +
                                "/descendant::span[@class='basket-item-actions-remove']")));

        deleteGoodFromBasketBtn.click();
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