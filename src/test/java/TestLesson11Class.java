import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.UUID;

/**
 * Created by kostyukevich.anna on 05.05.2017.
 */
public class TestLesson11Class {
    private WebDriver driver;

    @Before         // Запуск драйвера.
    public void start()
    {
        driver = new ChromeDriver();
    }

    @Test
    public void TestLesson11() {
        driver.navigate().to("http://localhost/litecart");

        // Открываю страницу регистрации нового пользователя.
        driver.findElement(By.cssSelector("#box-account-login a")).click();

        // Заполняю поля регистрации нового пользователя.
        String firstName = "Ivan";
        driver.findElement((By.cssSelector("input[name=firstname]"))).sendKeys(firstName);

        String lastName = "Ivanov";
        driver.findElement((By.cssSelector("input[name=lastname]"))).sendKeys(lastName);

        driver.findElement((By.cssSelector("input[name=address1]"))).sendKeys("New York, Red street, 10");
        driver.findElement((By.cssSelector("input[name=postcode]"))).sendKeys("12345");
        driver.findElement((By.cssSelector("input[name=city]"))).sendKeys("New York");

        driver.findElement((By.cssSelector("span.select2-selection__arrow"))).click();
        driver.findElement((By.cssSelector("input.select2-search__field"))).sendKeys("United States" + Keys.ENTER);

        // Штат любой.
        //driver.findElement((By.cssSelector("select[name=zone_code]"))).click();

        // Email.
        String email = UUID.randomUUID().toString() + "@mmm.ru";
        driver.findElement((By.cssSelector("input[name=email]"))).sendKeys(email);
        // Телефон.
        driver.findElement((By.cssSelector("input[name=phone]"))).sendKeys("1234567890");
        // Пароль.
        String password = "!qazxsw2";
        driver.findElement((By.cssSelector("input[name=password]"))).sendKeys(password);
        driver.findElement((By.cssSelector("input[name=confirmed_password]"))).sendKeys(password);
        // Нажимаю кнопку "Создать аккаунт".
        driver.findElement((By.cssSelector("button[name=create_account]"))).click();
        Assert.assertEquals("Your customer account has been created.", GetNoticeSuccessText());

        // Выполняю logout.
        Logout();
        Assert.assertEquals("You are now logged out.", GetNoticeSuccessText());

        // Выполняю снова login.
        driver.findElement((By.cssSelector("input[name=email]"))).sendKeys(email);
        driver.findElement((By.cssSelector("input[name=password]"))).sendKeys(password);
        driver.findElement((By.cssSelector("button[name=login]"))).click();
        Assert.assertEquals("You are now logged in as " + firstName + " " + lastName +".", GetNoticeSuccessText());

        // Выполняю logout.
        Logout();
        Assert.assertEquals("You are now logged out.", GetNoticeSuccessText());
    }

    // Выполняю logout.
    private void Logout(){
        driver.findElement((By.cssSelector("#box-account ul.list-vertical li:nth-child(4) a"))).click();
    }

    // Получение текста всплывающего сообщения.
    private String GetNoticeSuccessText(){
        return driver.findElement((By.cssSelector("div.success"))).getText();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}