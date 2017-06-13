import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by kostyukevich.anna on 02.06.2017.
 */
public class TestLesson13Class {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before         // Запуск драйвера.
    public void start()
    {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 2/*seconds*/);
    }

    // Проверка существования элемента.
    private boolean existsElement(String locator) {
        try {
            driver.findElement(By.cssSelector(locator));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    // Добавление товара в корзину.
    private void addProductToCart(String productQuantity) throws InterruptedException {
        // Выбираем первый товар из списка.
        driver.findElement(By.cssSelector(".product")).click();

        String selectLocator = "select[name='options[Size]']";
        if (existsElement(selectLocator)){
            driver.findElement(By.cssSelector(selectLocator)).click();
            driver.findElement(By.cssSelector("option[value='Small']")).click();
        }

        // Добавляем товар в корзину.
        driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("a.content .quantity"), productQuantity));

        // Возвращаемся на главную страницу.
        driver.findElement(By.cssSelector("#logotype-wrapper")).click();
    }

    // Удаляение товаров из корзины.
    private void deleteProductsFromCart() throws InterruptedException {
        // Нажимаем на кнопку Remove.
        List<WebElement> productElements = driver.findElements(By.cssSelector("button[name='remove_cart_item']"));
        productElements.get(0).click();
        wait.until(ExpectedConditions.invisibilityOf(productElements.get(0)));

        // Ждем, пока обновится таблица с продуктами.
        try{
            WebElement productsTable = driver.findElement(By.cssSelector("table.dataTable"));
            wait.until(ExpectedConditions.refreshed(input -> productsTable));
        }
        catch(Exception NotSuchElementException){};
    }

    @Test
    public void TestLesson13() throws InterruptedException {
        driver.navigate().to("http://localhost/litecart");

        int productQuantity = 3;
        // Добавляем 3 товара в корзину.
        for (int i = 1; i <= productQuantity; i++)
            addProductToCart(String.valueOf(i));

        // Заходим в корзину.
        driver.findElement(By.cssSelector("#cart")).click();

        // Удаляем товары из корзины.
        List<WebElement> productShortcutsElements = driver.findElements(By.cssSelector("li.shortcut"));
        int size = productShortcutsElements.size();
        while (size != 0) {
            productShortcutsElements.get(0).click();
            deleteProductsFromCart();

            productShortcutsElements = driver.findElements(By.cssSelector("li.shortcut"));
            size = productShortcutsElements.size();
        }
        deleteProductsFromCart();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}