package test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.MainPage;

public class MainPageBannerTest extends CommonConditions{
    private MainPage mainPage;

    @BeforeMethod
    private void openMainPage(){
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    @Test
    public void mainPageBannerAutoShiftTest() throws InterruptedException {
        String imgUrl_1 = mainPage.getBannerImgUrl();
        // 5 сек ждем смены картинки в слайдере
        Thread.sleep(5000);
        String imgUrl_2 = mainPage.getBannerImgUrl();

        Assert.assertFalse(imgUrl_1.equals(imgUrl_2));   // провалится
    }

    @Test
    public void mainPageBannerManualShiftTest() throws InterruptedException {
        mainPage.changeOfferInSlider(0);
        String imgUrl_1 = mainPage.getBannerImgUrl();
        Thread.sleep(1000);
        mainPage.changeOfferInSlider(1);
        String imgUrl_2 = mainPage.getBannerImgUrl();
        Thread.sleep(1000);

        Assert.assertFalse(imgUrl_1.equals(imgUrl_2));
    }

    @Test
    public void mainPageBannerOffersClickabilityTest() throws InterruptedException {
        mainPage.changeOfferInSlider(0)
                .clickDetailsButton();
        String pageUrl_1 = driver.getCurrentUrl();
        Thread.sleep(1000);
        // возвращаемся к предыдущей странице
        driver. navigate().back();

        mainPage.changeOfferInSlider(1)
                .clickDetailsButton();
        String pageUrl_2 = driver.getCurrentUrl();
        Thread.sleep(1000);

        Assert.assertFalse(pageUrl_1.equals(pageUrl_2));
    }
}
