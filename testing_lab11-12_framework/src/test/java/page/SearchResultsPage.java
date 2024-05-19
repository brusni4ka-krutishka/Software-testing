package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage extends AbstractPage{

    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//div[@class='container']/descendant::div[@class='col-lg-4 col-md-4 col-sm-6 col-12']")
    private List<WebElement> listOfSearchResults;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public SearchResultsPage openPage() {
        driver.get(driver.getCurrentUrl());
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public boolean checkSearchResultsNotEmpty(){
        if(listOfSearchResults.size() > 0){
            logger.info("Search result on first page = [" + listOfSearchResults.size() + " goods]");
            return true;
        }
        else{
            logger.info("Search result on first page = [0 goods]");
            return false;
        }
    }
}
