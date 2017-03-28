import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestClass {
    @Test
    public void TestMethod() {
        WebDriver driver = new ChromeDriver();
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.navigate().to("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        driver.close();
    }
}
