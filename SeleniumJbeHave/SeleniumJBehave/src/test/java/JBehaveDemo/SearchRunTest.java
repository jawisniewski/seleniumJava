package JBehaveDemo;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Hebo on 12.06.2017.
 */
public class SearchRunTest {
    private WebDriverProvider driver;
    private WebElement q;
    private Wait<WebDriver> wait;
    List<WebElement> tr;
    public SearchRunTest(WebDriverProvider driver){
        super();
        this.driver = driver;
    }

    @Given("Szukam trasy $search")
    public void givensearch(String search){
        driver.get().get("https://firmatransportowa.herokuapp.com/runs");

        WebElement login = driver.get().findElement(By.id("search"));
        login.sendKeys("pol-eng");

        WebElement form = driver.get().findElement(By.tagName("form"));
        form.submit();
        WebElement tbody = driver.get().findElement(By.tagName("tbody"));
        List<WebElement> tr = tbody.findElements(By.tagName("tr"));


    }

    @When("klikne na szukaj wynikow")
    public void whenClick(){
        WebElement form = driver.get().findElement(By.tagName("form"));
        form.submit();
        WebElement tbody = driver.get().findElement(By.tagName("tbody"));
        tr = tbody.findElements(By.tagName("tr"));

    }

    @Then("lista wynikow bedzie zawierac rekordy")
    public void thenListaNotEmpty(){
        assertNotEquals(0,tr.size());
    }
}
