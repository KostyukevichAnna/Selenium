import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by kostyukevich.anna on 02.05.2017.
 */
public class TestLesson4Class {
    @Test
    public void TestLesson4ChromeDriver() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();
        driver.close();
    }

    @Test
    public void TestLesson4FirefoxDriver() {
        WebDriver driver = new FirefoxDriver();
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();
        driver.close();
    }

    @Test
    public void TestLesson4IEDriver() {
        WebDriver driver = new InternetExplorerDriver();
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();
        driver.close();
    }
}
