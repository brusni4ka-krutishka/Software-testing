package pageobject_model.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import waits.CustomConditions;

import java.time.Duration;
import java.util.List;

public class SelemiumHQHomePage {

    private static final String HOMEPAGE_URL = "http://seleniumhq.org";
    private WebDriver driver;

    @FindBy(id="docsearch-input")
    @CacheLookup
    private List<WebElement> searchInputs;

    public SelemiumHQHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SelemiumHQHomePage openPage() {
        driver.get(HOMEPAGE_URL);
        // 10 секунд будем ожидать прогрузки AJAX скриптов на старнице
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public int countAmountOfFoundSearchInputs(){
        return searchInputs.size();
    }
}
