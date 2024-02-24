import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloWebDriver {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://seleniumhq.org");

        // получение элемента (кнопка открытия окна поиска)
        WebElement searchBtn = driver.findElement(By.xpath("//*[@id=\'docsearch\']/button"));
        // нажать на элемент
        searchBtn.click();

        // получение элемента (поле поиска)
        WebElement searchInput = driver.findElement(By.id("docsearch-input"));
        // ввести строку
        searchInput.sendKeys("selenium java");
        // далее мы хотим найти введенную строку
        // могли бы нажать на кнопку поиска, но таковой нет
        // поиск осуществляется нажатием клавиши Enter
        searchInput.sendKeys(Keys.ENTER);

        Thread.sleep(3000);
        driver.quit();
    }
}
