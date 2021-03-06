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

import static org.junit.Assert.assertEquals;

/**
 * Created by Hebo on 12.06.2017.
 */
public class LoginInCorrectTest {
    private WebDriverProvider driver;
    private WebElement q;
    private Wait<WebDriver> wait;

    public LoginInCorrectTest(WebDriverProvider driver){
        super();
        this.driver = driver;
    }

    @Given("niepoprawny login")
    public void givenLogins(){
        driver.get().get("https://firmatransportowa.herokuapp.com/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

        driver.get().findElement(By.id("login")).sendKeys("admin");

        driver.get().findElement(By.id("password")).sendKeys("123qe");
        ;

    }

    @When("klikne na przycisk loguj")
    public void whenPresButton(){
        WebElement btn = driver.get().findElement(By.id("submit"));
        btn.submit();
    }

    @Then("nie zostane zalogowany")
    public void thenNotLogged(){
        assertEquals("Bledny login lub haslo", driver.get().findElement(By.id("notice")).getText());
    }
}
