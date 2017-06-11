import com.gargoylesoftware.htmlunit.javascript.host.Console;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hebo on 15.05.2017.
 */
public class TestJava {



    private static WebDriver driver;

    @BeforeClass
    public static void setUp() throws Exception {
        //Od wersji selenium 3.0 samo FirefoxDriver nie wystarcza
        //Należy dodać sterownik geckodriver
        //Do pobrania tutaj: https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void loginInCorrect() {
        driver.get("http://127.0.0.1:3000/login");

        WebElement login = driver.findElement(By.id("login"));
        login.sendKeys("admin");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123qe");
        WebElement btn = driver.findElement(By.id("submit"));
        btn.click();
        assertEquals("Błedny login lub haslo", driver.findElement(By.id("notice")).getText());
    }
    @Test
    public void loginCorrect() {
        driver.get("http://127.0.0.1:3000/login");

        WebElement login = driver.findElement(By.id("login"));
        login.sendKeys("admin");
      WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123qwe");
        WebElement btn = driver.findElement(By.id("submit"));
                btn.click();
                assertEquals("Hello!", driver.findElement(By.id("logged")).getText());
    }

//        @Test
//        public  void test2() {
//            driver.get("https://google.pl/");
//
//            List<WebElement> listElements = driver.findElements(By.xpath("//a[contains(@href,'www.google.pl')]"));
//            String[] links = new String[listElements.size()];
//            System.out.println("Liczba linkow: " + listElements.size());
//            for (int i = 0; i < listElements.size(); i++) {
//                links[i] = listElements.get(i).getAttribute("href");
//                System.out.println(listElements.get(i).getAttribute("href"));
//            }
//// navigate to each Link on the webpage
//            for (int i = 0; i < listElements.size(); i++) {
//                driver.navigate().to(links[i]);
//
//            }
//        }
//            @Test
//            public  void test3(){
//                driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_select_form");
//
//                WebElement Element = driver.findElement(By.tagName("form"));
//               List<WebElement> ele =  Element.findElements(By.tagName("input"));
//               for (int i =0 ; i<ele.size(); i ++) {
//                  // ele.get(i).getAttribute("name");
//                   System.out.println(ele.get(i).getAttribute("name"));
//               }}
//                @Test
//                public  void test4(){
//                    driver.get("https://developer.mozilla.org/en-US/docs/Web/HTML/Element/select");
//
//                    Select se = new Select(driver.findElement(By.name("select")));
//                    List<WebElement> l = se.getOptions();
//                    l.size();
//                    System.out.println(l.size());
//        }
//
//


    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
