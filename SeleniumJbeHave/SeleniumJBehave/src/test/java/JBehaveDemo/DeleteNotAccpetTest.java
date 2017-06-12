package JBehaveDemo;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hebo on 12.06.2017.
 */
public class DeleteNotAccpetTest {
    private WebDriverProvider driver;
    private WebElement q;
    private Wait<WebDriver> wait;

    public DeleteNotAccpetTest(WebDriverProvider driver){
        super();
        this.driver = driver;

    }

    @Given("Auto do usuniecia")
    public void givenCarToDeletes(){
        driver.get().get("https://firmatransportowa.herokuapp.com/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

        driver.get().findElement(By.id("login")).sendKeys("admin");

        driver.get().findElement(By.id("password")).sendKeys("123qwe");
        ;
        WebElement btn = driver.get().findElement(By.id("submit"));
        btn.submit();

    }

    @When("Akceptuje usuniecie")
    public void wheniDontAcceptDelete(){
        List<WebElement> elements = driver.get().findElements(By.className("btn-danger"));
        elements.get(0).click();
        Alert alert = driver.get().switchTo().alert();
        alert.dismiss();
    }

    @Then("listaAutZostanieZmiejszona")
    public void thenCarNotDeleted(){

        assertTrue(driver.get().findElement(By.id("notice")).getText().equals(""));

    }
}
