package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;

public class GenerateTempEmailPage {
    WebDriver driver;
    private final String GENERATE_TEMP_EMAIL_PAGE_URL =
            "https://yopmail.com/ru/email-generator";

    @FindBy(xpath = "//button[@id='cprnd']")
    WebElement copyEmailButton;

    @FindBy(xpath = "//button/span[text()='Проверить почту']")
    WebElement checkEmailButton;

    public GenerateTempEmailPage(WebDriver driver){
        this.driver = driver;
    }

    public GenerateTempEmailPage open(){
        driver.get(GENERATE_TEMP_EMAIL_PAGE_URL);
        driver.manage().window().maximize();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3000), this);
        return this;
    }

    public GenerateTempEmailPage copyEmailButtonClick(){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(CustomConditions.isButtonEnabled(copyEmailButton));
        copyEmailButton.click();
        return this;
    }

    public void checkEmailButtonClick() {
        checkEmailButton.click();
    }
}
