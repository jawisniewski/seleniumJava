package JBehaveDemo;


import static org.junit.Assert.*;
import org.jbehave.core.annotations.*;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
public class GoogleSteps{
	
	private WebDriverProvider driver;
	private WebElement q;
	private Wait<WebDriver> wait;
	
	public GoogleSteps(WebDriverProvider driver){
		super();
		this.driver = driver;
	}
	
	@Given("google test site")
	public void givenGoogleTestSite(){
		 driver.get().get("https://www.google.co.in");
		 wait = new WebDriverWait(driver.get(), 10);
	}
	
	@When("I send keys Mateusz Miotk")
	public void whenISendKeysMateuszMiotk(){
		 q = driver.get().findElement(By.name("q"));
		 q.sendKeys("Mateusz Miotk");
		 q.submit();
		 wait.until(ExpectedConditions.titleContains("Mateusz Miotk"));
	}
	
	@Then("title of page is equal Mateusz Miotk - Szukaj w google")
	public void thenTitleOfPageIsEqualMateuszMiotkSzukajWGoogle(){
		 assertEquals(driver.get().getTitle(), "Mateusz Miotk - Szukaj w Google"); 
		 driver.get().close();
	}
}