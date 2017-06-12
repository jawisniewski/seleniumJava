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
public class LoginCorrectTest {
    private WebDriverProvider driver;
    private WebElement q;
    private Wait<WebDriver> wait;

    public LoginCorrectTest(WebDriverProvider driver){
        super();
        this.driver = driver;
    }

    @Given("poprawny login")
    public void givenLogin(){
        driver.get().get("https://firmatransportowa.herokuapp.com/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

        driver.get().findElement(By.id("login")).sendKeys("admin");

        driver.get().findElement(By.id("password")).sendKeys("123qwe");
        ;

    }

    @When("klikne na przycisk")
    public void whenLogin(){
        WebElement btn = driver.get().findElement(By.id("submit"));
        btn.submit();
    }

    @Then("zostane zalogowany")
    public void thenLogged(){
        driver.get().findElement(By.id("logged")).getText().contains("Hello!");
    }
}
