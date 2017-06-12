import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
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

        System.setProperty("webdriver.edge.driver", "resources/MicrosoftWebDriver.exe");

        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void loginCorrect() throws Exception {
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qwe");
        assertTrue(pageLogin.assertLogged("Hello!"));

    }
    @Test
    public void loginInCorrect() throws Exception {
        pageLogin = new PageObject(driver);
        pageLogin.login("admin", "123qe");
        assertEquals("Bledny login lub haslo", driver.findElement(By.id("notice")).getText());
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






    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
