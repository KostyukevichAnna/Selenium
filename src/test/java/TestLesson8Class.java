import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by kostyukevich.anna on 03.05.2017.
 */
public class TestLesson8Class {
    private WebDriver driver;
    //private WebDriverWait wait;

    @Before
    public void start()
    {
        driver = new ChromeDriver();
        //wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void TestLesson8()
    {
        driver.navigate().to("http://localhost/litecart");

        // Поиск продуктов.
        List<WebElement> products = driver.findElements(By.cssSelector(".product"));

        List<WebElement> stickers;
        String name;

        for (WebElement product:products) {
            name = product.findElement(By.cssSelector(".name")).getText();
            stickers = product.findElements(By.cssSelector(".sticker"));
            Assert.assertEquals("Продукт " + name, 1, stickers.size());
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
