package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;

public class MainPage {
    WebDriver driver;
    private final String MAIN_PAGE_URL =
            "https://everydaycoffee.by/";

    @FindBy(xpath="//input[@name='SENDER_SUBSCRIBE_EMAIL']")
    private WebElement emailInput;

    @FindBy(id = "bx_subscribe_btn_sljzMT")
    private WebElement subscribeButton;

    @FindBy(xpath = "//div[@id='sender_subscribe_component']/descendant::div[text()='Вам выслано письмо. Для подтверждения перейдите по ссылке из письма']")
    private WebElement popupAfterSubscribe;

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public MainPage open(){
        driver.get(MAIN_PAGE_URL);
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3000), this);
        return this;
    }

    public MainPage pasteEmailAddressInField(){
        // вставка в поле значения из буфера обмена
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            emailInput.sendKeys((CharSequence) clipboard.getData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public MainPage clickSubscribe(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", subscribeButton);
        return this;
    }

    public boolean checkIfResponsePopupIsAboutEmailWasSend(){
        try{
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(popupAfterSubscribe));
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
