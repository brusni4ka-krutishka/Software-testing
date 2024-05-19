package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.reporters.jq.Main;
import wait.CustomConditions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends AbstractPage {
    private final String MAIN_PAGE_URL =
            "https://everydaycoffee.by/";

    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//input[@name='SENDER_SUBSCRIBE_EMAIL']")
    private WebElement emailInput;

    @FindBy(id = "bx_subscribe_btn_sljzMT")
    private WebElement subscribeButton;

    @FindBy(xpath = "//div[@id='sender_subscribe_component']/descendant::div[text()='Вам выслано письмо. Для подтверждения перейдите по ссылке из письма']")
    private WebElement popupAfterSubscribe;

    @FindBy(xpath = "//div[@class='owl-stage']/div[@class='owl-item active']/a")
    private WebElement sliderA;

    @FindBy(xpath = "//div[@class='owl-dots']/descendant::span")
    private List<WebElement> listOfDotsToChangeSliderOffer;

    @FindBy(xpath = "//div[@class='owl-item active']/descendant::div[@class='btn']")
    private WebElement detailsButton;

    @FindBy(id = "title-search-input")
    private WebElement searchField;

    @FindBy(xpath = "//div[@id='search']/descendant::button")
    private WebElement searchButton;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3000), this);
    }

    @Override
    public MainPage openPage() {
        driver.get(MAIN_PAGE_URL);
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public MainPage pasteEmailAddressInField(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public MainPage clickSubscribe() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", subscribeButton);
        return this;
    }

    public boolean checkIfResponsePopupIsAboutEmailWasSend() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(popupAfterSubscribe));
            logger.info("Popup on main page after subscription is saying we've subscribed successfully: " +
                    "[true]");
        } catch (Exception e) {
            logger.info("Popup on main page after subscription is saying we've subscribed successfully: " +
                    "[false]");
            return false;
        }
        return true;
    }

    public String getBannerImgUrl() {
        return sliderA.getAttribute("data-bgimg");
    }

    public MainPage changeOfferInSlider(int offerNumber) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        if (offerNumber < 0) {
            executor.executeScript("arguments[0].click();",
                    listOfDotsToChangeSliderOffer.get(0));
        } else if (offerNumber > listOfDotsToChangeSliderOffer.size()) {
            executor.executeScript("arguments[0].click();",
                    listOfDotsToChangeSliderOffer.get(listOfDotsToChangeSliderOffer.size() - 1));
        } else {
            executor.executeScript("arguments[0].click();",
                    listOfDotsToChangeSliderOffer.get(offerNumber));
        }
        return this;
    }

    public MainPage clickDetailsButton(){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(detailsButton));
        detailsButton.click();
        return this;
    }

    public MainPage enterSearchStringInSearchField(String searchString){
        searchField.sendKeys(searchString);
        return this;
    }

    public SearchResultsPage clickSearchButton(){
        searchButton.click();
        return new SearchResultsPage(driver);
    }
}
