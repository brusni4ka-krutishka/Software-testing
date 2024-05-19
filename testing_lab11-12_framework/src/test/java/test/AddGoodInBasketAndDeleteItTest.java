package test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.BasketPage;
import page.GoodPage;

public class AddGoodInBasketAndDeleteItTest extends CommonConditions{
    private GoodPage goodPage;
    private BasketPage basketPage;
    // ссылка на страницу добавляемого товара - единственный id
    // к ней же можно получить доступ из корзины, при нажатии на изображение
    String addedGoodPageUrl;

    @Test
    public void goodExistsInBasketAndDeleteItTest()
            throws InterruptedException {
        goodPage = new GoodPage(driver);
        goodPage.openPage();

        addedGoodPageUrl = goodPage.addGoodInBasket();
        basketPage = new BasketPage(driver);
        basketPage.openPage();

        Assert.assertTrue(basketPage.checkIfGoodExistsInBasket(addedGoodPageUrl));
        Thread.sleep(3000);

        basketPage.deleteGoodFromBasket(addedGoodPageUrl);
        Assert.assertFalse(basketPage.checkIfGoodExistsInBasket(addedGoodPageUrl));
        Thread.sleep(3000);
    }
}
