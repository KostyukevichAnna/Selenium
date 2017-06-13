import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

/**
 * Created by kostyukevich.anna on 13.06.2017.
 */
public class TestLesson14Class {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start()
    {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    // Ожидание появления нового окна.
    private ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows){
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver input) {
                Set<String> handles = input.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next():null;
            }
        };
    }

    @Test
    public void TestLesson7() {
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Открываем страницу Countries.
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        WebElement countryRow = driver.findElement(By.cssSelector("form[name='countries_form'] tr.row"));
        // Выбираем страну.
        countryRow.findElement(By.cssSelector("td:nth-child(5) a")).click();

        // Находим список элементов, которые ведут на внешние страницы и открываются в новом окне.
        List<WebElement> elements = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
        String originalWindow, newWindow;
        for (WebElement element:elements) {
            // Запоминаем идентификатор текущего окна.
            originalWindow = driver.getWindowHandle();
            // Запоминаем идентификаторы уже открытых окон.
            Set<String> existingWindows = driver.getWindowHandles();
            // Открываем ссылку, которая откроется в новом окне.
            element.click();
            // Ждем появления нового окна с новым идентификатором.
            newWindow = wait.until(anyWindowOtherThan(existingWindows));
            // Переключаемся в новое окно.
            driver.switchTo().window(newWindow);
            // Закрываем старое окно.
            driver.close();
            // Возвращаемся в старое окно.
            driver.switchTo().window(originalWindow);
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
