import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by kostyukevich.anna on 04.05.2017.
 */
public class TestLesson10Class {

    private WebDriver driver;
    //private WebDriverWait wait;

    // Класс продукт.
    public class Product
    {
        private String name;
        private String regularPrice;        // Обычная цена.
        private String campaignPrice;       // Акционная цена.
        private String regularPriceColor;
        private String campaignPriceColor;
        private String regularPriceStyle;
        private String campaignPriceStyle;
        private Double regularPriceFontSize;
        private Double campaignPriceFontSize;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegularPrice() {
            return regularPrice;
        }

        public void setRegularPrice(String regularPrice) {
            this.regularPrice = regularPrice;
        }

        public String getCampaignPrice() {
            return campaignPrice;
        }

        public void setCampaignPrice(String campaignPrice) {
            this.campaignPrice = campaignPrice;
        }

        public String getRegularPriceColor() {
            return regularPriceColor;
        }

        public void setRegularPriceColor(String regularPriceColor) {
            this.regularPriceColor = regularPriceColor;
        }

        public String getCampaignPriceColor() {
            return campaignPriceColor;
        }

        public void setCampaignPriceColor(String campaignPriceColor) {
            this.campaignPriceColor = campaignPriceColor;
        }

        public String getRegularPriceStyle() {
            return regularPriceStyle;
        }

        public void setRegularPriceStyle(String regularPriceStyle) {
            this.regularPriceStyle = regularPriceStyle;
        }

        public String getCampaignPriceStyle() {
            return campaignPriceStyle;
        }

        public void setCampaignPriceStyle(String campaignPriceStyle) {
            this.campaignPriceStyle = campaignPriceStyle;
        }

        public Double getRegularPriceFontSize() {
            return regularPriceFontSize;
        }

        public void setRegularPriceFontSize(Double regularPriceFontSize) {
            this.regularPriceFontSize = regularPriceFontSize;
        }

        public Double getCampaignPriceFontSize() {
            return campaignPriceFontSize;
        }

        public void setCampaignPriceFontSize(Double campaignPriceFontSize) {
            this.campaignPriceFontSize = campaignPriceFontSize;
        }
    }

    // Заполнение полей экземпляра продукта, используя данные с главной страницы.
    private WebElement FillProductFromMainCard(Product product)
    {
        WebElement productElement = driver.findElement(By.cssSelector("#box-campaigns li:first-child"));
        product.name = productElement.findElement(By.cssSelector(".name")).getText();

        FillProductPriceFields(product, productElement);
        return productElement;
    }

    // Заполнение полей экземпляра продукта, используя данные со страницы продукта.
    private void FillProductFromProductCard(Product product)
    {
        WebElement productElement = driver.findElement(By.cssSelector("#box-product"));
        product.name = productElement.findElement(By.cssSelector("h1.title")).getText();

        FillProductPriceFields(product, productElement);
    }

    // Заполнение полей экземпляра продукта, связанные с ценой (цвет, стиль, размер шрифта).
    private void FillProductPriceFields(Product product, WebElement element)
    {
        WebElement regularPriceElement = element.findElement(By.cssSelector(".regular-price"));
        product.regularPrice = regularPriceElement.getText();
        product.regularPriceColor = regularPriceElement.getCssValue("color");
        product.regularPriceStyle = regularPriceElement.getCssValue("text-decoration");
        product.regularPriceFontSize = Double.parseDouble(regularPriceElement.getCssValue("font-size").split("px")[0]);

        WebElement campaignPriceElement = element.findElement(By.cssSelector(".campaign-price"));
        product.campaignPrice = campaignPriceElement.getText();
        product.campaignPriceColor = campaignPriceElement.getCssValue("color");
        product.campaignPriceStyle = campaignPriceElement.getCssValue("font-weight");
        product.campaignPriceFontSize = Double.parseDouble(campaignPriceElement.getCssValue("font-size").split("px")[0]);
    }

    @Before         // Запуск драйвера.
    public void start()
    {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void TestLesson10() {
        driver.navigate().to("http://localhost/litecart");

        // Создаю экземпляр для продукта на главной странице.
        Product product = new Product();
        WebElement productElement = FillProductFromMainCard(product);

        // Проверка цвета шрифта обычной цены (серый).
        Assert.assertEquals("rgba(119, 119, 119, 1)", product.regularPriceColor);   // Для Chrome.

        // Assert.assertEquals("rgb(119, 119, 119)", product.regularPriceColor);     // Для Firefox.

        // Проверка шрифта обычной цены (зачеркнутая).
        Assert.assertEquals("line-through solid rgb(119, 119, 119)", product.regularPriceStyle);      // Для Chrome.

        // Assert.assertEquals("line-through", product.regularPriceStyle);      // Для Firefox.

        // Проверка размера шрифта обычной цены.
        Assert.assertEquals("", 14.4, product.regularPriceFontSize, 0.0);

        // Проверка цвета шрифта акционной цены (красный).
        Assert.assertEquals("rgba(204, 0, 0, 1)", product.campaignPriceColor);    // Для Chrome.

        // Assert.assertEquals("rgb(204, 0, 0)", product.campaignPriceColor);    // Для Firefox.

        // Проверка шрифта акционной цены (жирный).
        Assert.assertEquals("bold", product.campaignPriceStyle);    // Для Chrome.

        // Assert.assertEquals("900", product.campaignPriceStyle);    // Для Firefox.

        // Проверка размера шрифта акционной цены.
        Assert.assertEquals("", 18.0, product.campaignPriceFontSize, 0.0);

        // Размер шрифта акционной цены больше, чем обычной.
        Assert.assertTrue(product.regularPriceFontSize < product.campaignPriceFontSize);


        // Открываю страницу с продуктом.
        productElement.findElement(By.cssSelector("a")).click();

        // Создаю экземпляр для продукта на странице продукта.
        Product productCard = new Product();
        FillProductFromProductCard(productCard);

        // Проверка цвета шрифта обычной цены (серый).
        Assert.assertEquals("rgba(102, 102, 102, 1)", productCard.regularPriceColor);       // Для Chrome.

        // Assert.assertEquals("rgb(102, 102, 102)", productCard.regularPriceColor);       // Для Firefox.

        // Проверка шрифта обычной цены (зачеркнутая).
        Assert.assertEquals("line-through solid rgb(102, 102, 102)", productCard.regularPriceStyle);    // Для Chrome.

        // Assert.assertEquals("line-through", productCard.regularPriceStyle);    // Для Firefox.

        // Проверка размера шрифта обычной цены.
        Assert.assertEquals("", 16.0, productCard.regularPriceFontSize, 0.0);

        // Проверка цвета шрифта акционной цены (красный).
        Assert.assertEquals("rgba(204, 0, 0, 1)", productCard.campaignPriceColor);      // Для Chrome.

        // Assert.assertEquals("rgb(204, 0, 0)", productCard.campaignPriceColor);      // Для Firefox.

        // Проверка шрифта акционной цены (жирный).
        Assert.assertEquals("bold", productCard.campaignPriceStyle);    // Для Chrome.

        // Assert.assertEquals("700", productCard.campaignPriceStyle);    // Для Firefox.

        // Проверка размера шрифта акционной цены.
        Assert.assertEquals("", 22.0, productCard.campaignPriceFontSize, 0.0);

        // Размер шрифта акционной цены больше, чем обычной.
        Assert.assertTrue(productCard.regularPriceFontSize < productCard.campaignPriceFontSize);

        // Проверка совпадения названия продукта на главной странице и странице продукта.
        Assert.assertEquals(product.name, productCard.name);

        // Проверка совпадения обычной цены продукта на главной странице и странице продукта.
        Assert.assertEquals(product.regularPrice, productCard.regularPrice);

        // Проверка совпадения акционной цены продукта на главной странице и странице продукта.
        Assert.assertEquals(product.campaignPrice, productCard.campaignPrice);
    }

    @After
    public void stop(){
        driver.close();
        driver = null;
    }
}
