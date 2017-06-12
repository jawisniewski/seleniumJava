import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hebo on 12.06.2017.
 */
public class ChromeTest {


    public PageObject pageLogin;

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() throws Exception {
        //Od wersji selenium 3.0 samo FirefoxDriver nie wystarcza
        //Należy dodać sterownik geckodriver
        //Do pobrania tutaj: https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void loginInCorrect() throws Exception {
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qe");
        assertEquals("Bledny login lub haslo", driver.findElement(By.id("notice")).getText());
    }
    @Test
    public void loginCorrect() throws Exception {
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        assertTrue(pageLogin.assertLogged("Hello!"));

    }
    @Test
    public void searchTest() throws Exception {

        driver.get("https://firmatransportowa.herokuapp.com/runs");

        WebElement login = driver.findElement(By.id("search"));
        login.sendKeys("pol-eng");

        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();
        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> tr = tbody.findElements(By.tagName("tr"));

        assertEquals(2,tr.size());
    }
    @Test
    public void searchInCorrectTest() throws Exception {

        driver.get("https://firmatransportowa.herokuapp.com/runs");

        WebElement login = driver.findElement(By.id("search"));
        login.sendKeys("Anglia");

        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();
        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> tr = tbody.findElements(By.tagName("tr"));

        assertEquals(0,tr.size());
    }
    @Test
    public void searchEmptyTest() throws Exception{

        driver.get("https://firmatransportowa.herokuapp.com/runs");

        WebElement login = driver.findElement(By.id("search"));
        login.sendKeys("");

        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();
        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> tr = tbody.findElements(By.tagName("tr"));

        assertNotEquals(0,tr.size());
    }
    @Test
    public void CreateRunTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        Integer size1 = pageLogin.assertTrSize("pol-fr");

        driver.get("https://firmatransportowa.herokuapp.com/runs/new");

        List< WebElement> formcontrol = driver.findElements(By.className("form-control"));
        formcontrol.get(0).sendKeys("pol-fr");
        formcontrol.get(1).sendKeys("2000");
        formcontrol.get(2).sendKeys("2000");

        Select car =  new Select (driver.findElement(By.id("run_cars_id")));
        car.selectByIndex(1);
        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();
        Integer size2 = pageLogin.assertTrSize("pol-fr");

        assertNotEquals(size1, size2);
    }
    @Test
    public void EditRunTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");

        pageLogin.EditRow(0);

        List< WebElement> formcontrol = driver.findElements(By.className("form-control"));
        formcontrol.get(0).clear();
        formcontrol.get(0).sendKeys("MAN TGX");



        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();
        assertEquals("Car was successfully updated.",driver.findElement(By.id("notice")).getText());


    }
    @Test (expected=IndexOutOfBoundsException.class)
    public void EditFailRunTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");

        pageLogin.EditRow(30);

        List< WebElement> formcontrol = driver.findElements(By.className("form-control"));
        formcontrol.get(0).clear();
        formcontrol.get(0).sendKeys("MAN TGX");



        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();
        assertEquals("Car was successfully updated.",driver.findElement(By.id("notice")).getText());


    }
    @Test
    public void DeleteCarTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");

        pageLogin.DeleteRow(5);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        assertEquals("Car was successfully destroyed.",driver.findElement(By.id("notice")).getText());


    }
    @Test
    public void DeleteNotAcceptCarTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");

        pageLogin.DeleteRow(0);
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        assertTrue(driver.findElement(By.id("notice")).getText().equals(""));


    }
    @Test
    public void FormCreateTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        driver.get("https://firmatransportowa.herokuapp.com/runs/new");

        List< WebElement> formcontrol = driver.findElements(By.className("form-control"));
        formcontrol.get(0).sendKeys("pol-fr");
        formcontrol.get(1).sendKeys("2000");
        formcontrol.get(2).sendKeys("2000");

        Select car =  new Select (driver.findElement(By.id("run_cars_id")));
        car.selectByIndex(1);
        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();

        assertFalse(driver.getCurrentUrl().equals("https://firmatransportowa.herokuapp.com/runs/new"));
    }
    @Test
    public void FormFailCreateTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        driver.get("https://firmatransportowa.herokuapp.com/runs/new");

        List< WebElement> formcontrol = driver.findElements(By.className("form-control"));
        formcontrol.get(0).sendKeys("");
        formcontrol.get(1).sendKeys("2000");
        formcontrol.get(2).sendKeys("2000");

        Select car =  new Select (driver.findElement(By.id("run_cars_id")));
        car.selectByIndex(1);
        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();

        assertTrue(driver.findElement(By.id("error_explanation")).findElement(By.tagName("li")).getText().equals("Name can't be blank"));
    }



    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
