import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by kostyukevich.anna on 05.05.2017.
 */
public class TestLesson12Class {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before         // Запуск драйвера.
    public void start()
    {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 2/*seconds*/);
    }

    // Выполнение логина в админку учебного приложения.
    private void LogIn() {
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();
    }

    @Test
    public void TestLesson12() throws InterruptedException {
        driver.navigate().to("http://localhost/litecart/admin");
        LogIn();

        // Открываю меню "Каталог".
        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2) a")).click();
        // Нажимаю на кнопку "Добавить новый продукт".
        driver.findElement(By.cssSelector("#content div a:nth-child(2)")).click();

        // Изменяю статус продукта на включен.
        driver.findElement(By.cssSelector("input[name='status'][value='1']")).click();

        // Добавляю имя продукта.
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd_hh:mm:ss");
        String productName = "Hedgehog_" + formatForDateNow.format(date);
        driver.findElement(By.name("name[en]")).sendKeys(productName);

        // Добавляю код продукта.
        driver.findElement(By.name("code")).sendKeys("h_" + formatForDateNow.format(date));

        // Выбираю группу продукта.
        driver.findElement(By.cssSelector("input[name='product_groups[]'][value='1-3']")).click();

        // Заполняю количество.
        WebElement quantity = driver.findElement(By.name("quantity"));
        quantity.clear();
        quantity.sendKeys("30");

        // Заполняю статус продажи.
        WebElement soldOutStatus = driver.findElement(By.name("sold_out_status_id"));
        soldOutStatus.click();
        soldOutStatus.findElement(By.cssSelector("option[value='2']")).click();

        // Добавляю изображение.
        //driver.findElement(By.cssSelector("input[name='new_images[]']")).click();

        // Добавляю дату начала действия продукта.
        formatForDateNow = new SimpleDateFormat("ddMMyyyy");
        driver.findElement(By.name("date_valid_from")).sendKeys(formatForDateNow.format(date));
        // Добавляю дату окончания действия продукта.
        formatForDateNow = new SimpleDateFormat("ddMM2018");
        driver.findElement(By.name("date_valid_to")).sendKeys(formatForDateNow.format(date));

        // Открываю вкладку Информация.
        WebElement tabs = wait.until(presenceOfElementLocated(By.cssSelector(".tabs li:nth-child(2)")));
        tabs.click();
        // Заполняю поле Manufacturer.
        WebElement manufacturer = driver.findElement(By.name("manufacturer_id"));
        manufacturer.click();
        manufacturer.findElement(By.cssSelector("option[value='1']")).click();

        // Заполняю короткое описание.
        String description = "A small animal with needles on the body.";
        driver.findElement(By.name("short_description[en]")).sendKeys(description);
        // Заполняю полное описание.
        driver.findElement(By.className("trumbowyg-editor")).sendKeys(description);

        // Открываю вкладку Prices.
        tabs = wait.until(presenceOfElementLocated(By.cssSelector(".tabs li:nth-child(4)")));
        tabs.click();
        // Заполняю закупочную цену товара.
        WebElement purchasePrice  = driver.findElement(By.name("purchase_price"));
        purchasePrice.clear();
        purchasePrice.sendKeys("5");
        // Заполняю поле валюты для закупочной цены.
        WebElement currency = driver.findElement(By.name("purchase_price_currency_code"));
        currency.click();
        currency.findElement(By.cssSelector("option[value='USD']")).click();
        // Заполняю поле цены.
        WebElement price  = driver.findElement(By.name("prices[USD]"));
        price.clear();
        price.sendKeys("20.0000");

        driver.findElement(By.name("save")).click();

        // Проверяю добавление продукта. Открываю меню "Каталог".
        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2) a")).click();
        List<WebElement> catalog = driver.findElements(By.cssSelector(".dataTable tr.row"));
        WebElement product = catalog.get(catalog.size() - 1);
        String productNameCheck = product.findElement(By.cssSelector("td:nth-child(3) a")).getText();
        // Проверка наличия только что добавленного продукта.
        Assert.assertEquals(productName, productNameCheck);
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
