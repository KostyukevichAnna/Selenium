import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by kostyukevich.anna on 02.05.2017.
 */
public class TestLesson7Class {
    private WebDriver driver;
    //private WebDriverWait wait;

    @Before
    public void start()
    {
        driver = new ChromeDriver();
        //wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void TestLesson7() {
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        WebElement menu = driver.findElement(By.cssSelector("#box-apps-menu"));
        List<WebElement> liLinks = menu.findElements(By.cssSelector("li"));
        int menuSize = liLinks.size();
        int j = 0, k;
        WebElement h1;

        for(int i = 0; i < menuSize; i++)
        {
            if(i == 0)
                k = i;
            else{
                menu = driver.findElement(By.cssSelector("#box-apps-menu"));
                liLinks = menu.findElements(By.cssSelector("li"));
                k = i + j;      // Индекс пункта меню с учетом открытых предыдущих пунктов подменю.
            }
            liLinks.get(k).findElement(By.cssSelector("a")).click();
            h1 = driver.findElement(By.cssSelector("#content h1"));

            // Проверка наличия заголовка на странице.
            Assert.assertNotNull(h1);

            // Находим элементы подменю.
            WebElement subMenu;
            try{
                subMenu = driver.findElement(By.cssSelector("ul .docs"));
                if(subMenu != null) {
                    List<WebElement> subMenuLinks = subMenu.findElements(By.cssSelector("li"));
                    int subMenuSize = subMenuLinks.size();
                    for (j = 0; j < subMenuSize; j++) {
                        if (j != 0) {
                            subMenu = driver.findElement(By.cssSelector("ul .docs"));
                            subMenuLinks = subMenu.findElements(By.cssSelector("li"));
                        }
                        subMenuLinks.get(j).findElement(By.cssSelector("a")).click();
                        h1 = driver.findElement(By.cssSelector("#content h1"));

                        // Проверка наличия заголовка на странице.
                        Assert.assertNotNull(h1);
                    }
                }
            }
            catch(NoSuchElementException ex){
                j = 0;
            }
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}