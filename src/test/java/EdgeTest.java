import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hebo on 11.06.2017.
 */
public class EdgeTest {
    public PageObject pageLogin;

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() throws Exception {
        //Od wersji selenium 3.0 samo FirefoxDriver nie wystarcza
        //Należy dodać sterownik geckodriver
        //Do pobrania tutaj: https://github.com/mozilla/geckodriver/releases

        System.setProperty("webdriver.opera.driver", "resources/operadriver.exe");

        driver = new OperaDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void loginInCorrect() throws Exception {
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qe");
        assertEquals("Błedny login lub haslo", driver.findElement(By.id("notice")).getText());
    }
    @Test
    public void loginCorrect() throws Exception {
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        assertTrue(pageLogin.assertLogged("Hello!"));

    }
    @Test
    public void searchTest() throws Exception {

        driver.get("http://127.0.0.1:3000/runs");

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

        driver.get("http://127.0.0.1:3000/runs");

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

        driver.get("http://127.0.0.1:3000/runs");

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

        driver.get("http://localhost:3000/runs/new");

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

        pageLogin.DeleteRow(5);
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        assertTrue(driver.findElement(By.id("notice")).getText().equals(""));


    }
    @Test
    public void FormCreateTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        driver.get("http://localhost:3000/runs/new");

        List< WebElement> formcontrol = driver.findElements(By.className("form-control"));
        formcontrol.get(0).sendKeys("pol-fr");
        formcontrol.get(1).sendKeys("2000");
        formcontrol.get(2).sendKeys("2000");

        Select car =  new Select (driver.findElement(By.id("run_cars_id")));
        car.selectByIndex(1);
        WebElement form = driver.findElement(By.tagName("form"));
        form.submit();

        assertFalse(driver.getCurrentUrl().equals("http://127.0.0.1:3000/runs/new"));
    }
    @Test
    public void FormFailCreateTest()  throws Exception{
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        driver.get("http://localhost:3000/runs/new");

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
//
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
