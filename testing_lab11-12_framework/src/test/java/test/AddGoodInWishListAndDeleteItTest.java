package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.GoodPage;
import page.WishListPage;

public class AddGoodInWishListAndDeleteItTest extends CommonConditions{
    private GoodPage goodPage;
    private WishListPage wishListPage;
    // ссылка на страницу добавляемого товара - единственный id
    // к ней же можно получить доступ из корзины, при нажатии на изображение
    String addedGoodPageUrl;

    @Test
    public void goodExistsInBasketAndDeleteItTest()
            throws InterruptedException {
        goodPage = new GoodPage(driver);
        goodPage.openPage();

        addedGoodPageUrl = goodPage.addGoodInWishList();
        wishListPage = new WishListPage(driver);
        wishListPage.openPage();

        Assert.assertTrue(wishListPage.checkIfGoodExistsInWishList(addedGoodPageUrl));
        Thread.sleep(3000);

        wishListPage.deleteGoodFromWishList(addedGoodPageUrl);
        driver.navigate().refresh();
        Assert.assertFalse(wishListPage.checkIfGoodExistsInWishList(addedGoodPageUrl));
        Thread.sleep(3000);
    }
}