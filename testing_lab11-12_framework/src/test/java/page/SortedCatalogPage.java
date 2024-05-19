package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SortedCatalogPage extends AbstractPage {

//    @FindBy(xpath = "//div[@class='row shop_wrapper']/div[@class='col-lg-4 col-md-4 col-sm-6 col-12']/descendant::div[@class='product_tp']/descendant::span[@class='product_tp__value' and text()='\n                                                250 г.\n                                              ']/ancestor::label/span[@class='product_price']")
//    private List<WebElement> listOfElementsContainingPriceOfSortedGoods;

    public SortedCatalogPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public SortedCatalogPage openPage() {
        driver.get(driver.getCurrentUrl());
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(CustomConditions.jQueryAJAXCompleted());
        return this;
    }

    public boolean checkIfSortByPriceCorrect(String sortType) {
        List<Integer> priceList = new ArrayList<>();

        List<WebElement> listOfElementsContainingPriceOfSortedGoods = new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='row shop_wrapper']/div[@class='col-lg-4 col-md-4 col-sm-6 col-12']/descendant::div[@class='product_tp']/descendant::span[@class='product_tp__value' and text()='\n                                                250 г.\n                                              ']/ancestor::label/span[@class='product_price']")));

        for (WebElement elementContainingPrice : listOfElementsContainingPriceOfSortedGoods) {
            String currencyAndPriceStr = elementContainingPrice.getText();
            String priceStr = currencyAndPriceStr.substring(0, currencyAndPriceStr.indexOf("руб.") - 1);
            try {
                priceList.add(Integer.valueOf(priceStr));
            } catch (Exception e) {
                return false;
            }
        }

        switch (sortType.toLowerCase()) {
            case "asc": // по возрастанию
                for (int i = 0; i < priceList.size() - 1; i++) {
                    int currentPrice = priceList.get(i);
                    int nextPrice = priceList.get(i + 1);
                    if(currentPrice > nextPrice)
                        return false;
                }
                break;
            default:    // desc будет по умолчанию; по убыванию
                for (int i = 0; i < priceList.size() - 1; i++) {
                    int currentPrice = priceList.get(i);
                    int nextPrice = priceList.get(i + 1);
                    if(currentPrice < nextPrice)
                        return false;
                }
                break;
        }
        return true;
    }

    public boolean checkIfSortByNameCorrect(String sortType) {
        List<WebElement> listOfElementsContainingNamesOfSortedGoods = new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='row shop_wrapper']/div[@class='col-lg-4 col-md-4 col-sm-6 col-12']/descendant::h4[@class='product_name']/descendant::a")));

        List<String> namesList = new ArrayList<>();

        for (WebElement elementContainingName : listOfElementsContainingNamesOfSortedGoods) {
             namesList.add(elementContainingName.getText().trim());
        }

        switch (sortType.toLowerCase()) {
            case "z-a": // по возрастанию
                if (!namesList.isEmpty()) {
                    Iterator<String> it = namesList.iterator();
                    String prev = it.next();
                    while (it.hasNext()) {
                        String next = it.next();
                        if (prev.compareTo(next) < 0) {
                            return false;
                        }
                        prev = next;
                    }
                }
                break;
            default:    // a-z будет по умолчанию
                if (!namesList.isEmpty()) {
                    Iterator<String> it = namesList.iterator();
                    String prev = it.next();
                    while (it.hasNext()) {
                        String next = it.next();
                        if (prev.compareTo(next) > 0) {
                            return false;
                        }
                        prev = next;
                    }
                }
        }
        return true;
    }
}