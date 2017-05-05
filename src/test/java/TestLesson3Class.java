import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;

/**
 * Created by kostyukevich.anna on 02.05.2017.
 */
public class TestLesson3Class {

    private WebDriver driver;

    @Before
    public void start()
    {
        driver = new ChromeDriver();
    }

    @Test
    public void TestLesson3() {
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
