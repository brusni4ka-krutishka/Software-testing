package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class CatalogPage extends AbstractPage{
    private final String CATALOG_PAGE_URL = "https://everydaycoffee.by/catalog/monosorta/filter/bitterness-to-7/apply/";

    @FindBy(xpath="//div[@class='nice-select select_option']")
    private WebElement openSortTypesDropdownListButton;

    @FindBy(xpath="//div[@class='nice-select select_option open']/ul[@class='list']/li")
    private List<WebElement> sortTypesDropdownList;

    private final String priceAsc = "PRICE_ASC";
    private final String priceDesc = "PRICE_DESC";
    private final String nameAz = "NAME_ASC";
    private final String nameZa = "NAME_DESC";

    public CatalogPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public CatalogPage openPage() {
        driver.get(CATALOG_PAGE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public SortedCatalogPage clickButtonToSortByPrice(String sortType){
        openSortTypesDropdownListButton.click();
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        switch (sortType.toLowerCase()){
            case "asc":
                for(WebElement type : sortTypesDropdownList){
                    if(Objects.equals(type.getAttribute("data-value"), priceAsc)){
                        executor.executeScript("arguments[0].click();", type);
                        break;
                    }
                }
                break;
            default:    // desc будет по умолчанию
                for(WebElement type : sortTypesDropdownList){
                    String str = type.getAttribute("data-value");
                    if(Objects.equals(type.getAttribute("data-value"), priceDesc)){
                        executor.executeScript("arguments[0].click();", type);
                        break;
                    }
                }
                break;
        }
        return new SortedCatalogPage(driver);
    }

    public SortedCatalogPage clickButtonToSortByName(String sortType){
        openSortTypesDropdownListButton.click();
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        switch (sortType.toLowerCase()){
            case "z-a":
                for(WebElement type : sortTypesDropdownList){
                    if(Objects.equals(type.getAttribute("data-value"), nameZa)){
                        executor.executeScript("arguments[0].click();", type);
                        break;
                    }
                }
                break;
            default:    // a-z будет по умолчанию
                for(WebElement type : sortTypesDropdownList){
                    String str = type.getAttribute("data-value");
                    if(Objects.equals(type.getAttribute("data-value"), nameAz)){
                        executor.executeScript("arguments[0].click();", type);
                        break;
                    }
                }
                break;
        }
        return new SortedCatalogPage(driver);
    }
}
