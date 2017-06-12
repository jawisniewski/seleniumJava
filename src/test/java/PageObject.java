import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageObject {

    public WebDriver driver;
    private final Wait<WebDriver> wait;
    public  List<WebElement> tr;
    public PageObject(WebDriver driver){
        this.driver = driver;
        driver.get("https://firmatransportowa.herokuapp.com/login");
        wait = new WebDriverWait(driver,10);
    }

    public void login(String login, String password) throws Exception{

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

        driver.findElement(By.id("login")).sendKeys(login);

        driver.findElement(By.id("password")).sendKeys(password);
     ;
        WebElement btn = driver.findElement(By.id("submit"));
        btn.submit();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logged")));

    }

public  int assertTrSize (String name) throws  Exception {


    driver.get("https://firmatransportowa.herokuapp.com/runs");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));
    WebElement login = driver.findElement(By.id("search"));
    login.sendKeys(name);
    driver.findElement(By.tagName("form")).submit();
    WebElement tbody = driver.findElement(By.tagName("tbody"));
    tr = tbody.findElements(By.tagName("tr"));
        return tr.size();
}
public  void EditRow(Integer index) throws Exception{


   List<WebElement> elements = driver.findElements(By.className("btn-warning"));
   elements.get(index).click();
}
    public  void DeleteRow(Integer index) throws Exception{


        List<WebElement> elements = driver.findElements(By.className("btn-danger"));
        elements.get(index).click();
    }
    public boolean assertLogged(String title) throws Exception{
        Boolean result = driver.findElement(By.id("logged")).getText().contains(title);

        return(result);
    }

}
