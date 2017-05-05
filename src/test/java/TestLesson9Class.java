import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kostyukevich.anna on 03.05.2017.
 */
public class TestLesson9Class {
    private WebDriver driver;
    //private WebDriverWait wait;

    @Before
    public void start()
    {
        driver = new ChromeDriver();
        //wait = new WebDriverWait(driver, 10);
    }

    // Открытие страницы.
    private void CreateDriver(String url)
    {
        driver.navigate().to(url);
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();
    }

    enum Pages{CountriesPage, ZonePage}

    // Нахождение ссылки на страну по ее идентификатору.
    private WebElement FindCountryLinkById(String id, WebDriver driver, Pages page)
    {
        List<WebElement> countries = driver.findElements(By.cssSelector(".row"));
        String curId = "";
        for (WebElement country:countries) {
            if (page.compareTo(Pages.CountriesPage) == 0)
                curId = country.findElement(By.cssSelector("td:nth-child(3)")).getText();
            else if (page.compareTo(Pages.ZonePage) == 0)
                curId = country.findElement(By.cssSelector("td:nth-child(2)")).getText();

            if (curId.compareTo(id) == 0)
                return country.findElement(By.cssSelector("a"));
        }
        return null;
    }

    @Test
    public void TestLesson9CountriesOrder()
    {
        CreateDriver("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<WebElement> countries = driver.findElements(By.cssSelector(".row"));

        String countryName;
        String countryNamePrev = "";
        WebElement countryLink;
        String zone;
        boolean sortCountry = true;
        ArrayList<String> checkZoneCountries = new ArrayList<>();

        for (WebElement country:countries) {
            countryLink = country.findElement(By.cssSelector("a"));
            countryName = countryLink.getText();
            if(countryNamePrev.compareTo("") == 0)
                countryNamePrev = countryName;
            else {
                if (countryNamePrev.compareTo(countryName) <= 0)
                    countryNamePrev = countryName;
                else{
                    sortCountry = false;
                    break;
                }
            }

            // Проверка количества зон в стране.
            zone = country.findElement(By.cssSelector("td:nth-child(6)")).getText();
            if (zone.compareTo("0") != 0)
                checkZoneCountries.add(country.findElement(By.cssSelector("td:nth-child(3)")).getText());
        }

        List<WebElement> zones;
        String zoneName;
        String zoneNamePrev = "";
        boolean sortZones = true;

        for (String countryId:checkZoneCountries) {
            // По Id страны находим ссылку, открываем страницу.
            FindCountryLinkById(countryId, driver, Pages.CountriesPage).click();

            zones = driver.findElements(By.cssSelector("#table-zones tr"));

            int zonesCount = zones.size();

            for (int z = 0; z < zonesCount - 1; z++ ) {
                if (z == 0) continue;
                zoneName = zones.get(z).findElement(By.cssSelector("td:nth-child(3)")).getText();
                if(zoneNamePrev.compareToIgnoreCase("") == 0)
                    zoneNamePrev = zoneName;
                else {
                    if (zoneNamePrev.compareTo(zoneName) <= 0)
                        zoneNamePrev = zoneName;
                    else{
                        sortZones = false;
                        break;
                    }
                }
            }
            driver.navigate().back();
            zoneNamePrev = "";
        }

        // Проверка сортировки стран.
        Assert.assertEquals(sortCountry, true);

        // Проверка сортировки зон.
        Assert.assertEquals(sortZones, true);

        driver.close();
    }

    @Test
    public void TestLesson9ZonesOrder()
    {
        CreateDriver("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        List<WebElement> countries = driver.findElements(By.cssSelector(".row td:nth-child(2)"));   //Идентификаторы стран
        ArrayList<String> countriesId = new ArrayList<>();
        for (WebElement country:countries)
            countriesId.add(country.getText());

        List<WebElement> zones;
        String zoneName;
        String zoneNamePrev = "";
        boolean sortZones = true;

        for (String id:countriesId){
            // По Id страны находим ссылку, открываем страницу.
            FindCountryLinkById(id, driver, Pages.ZonePage).click();

            zones = driver.findElements(By.cssSelector("#table-zones tr"));
            int zonesCount = zones.size();

            for (int z = 0; z < zonesCount - 1; z++ ) {
                if (z == 0) continue;

                WebElement td = zones.get(z).findElement(By.cssSelector("td:nth-child(3)"));
                zoneName = td.findElement(By.cssSelector("option[selected=selected]")).getText();

                if(zoneNamePrev.compareTo("") == 0)
                    zoneNamePrev = zoneName;
                else {
                    if (zoneNamePrev.compareTo(zoneName) <= 0)
                        zoneNamePrev = zoneName;
                    else{
                        sortZones = false;
                        break;
                    }
                }
            }
            driver.navigate().back();
            zoneNamePrev = "";
        }
        // Проверка сортировки зон.
        Assert.assertEquals(sortZones, true);
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}