package beeline.number.checker;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class TestBase extends ApplicationManager{

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
  }

  @AfterMethod
  public void tearDown() {
    wd.quit();
  }
  // AjaxWaiter
  public boolean waitForJSandJQueryToLoad() {

    WebDriverWait wait = new WebDriverWait(wd, 30);

    // wait for jQuery to load
    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
      public Object getDriver() {
        return wd;
      }

      @Override
      public Boolean apply(WebDriver driver) {
        try {
          return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active") == 0);
        } catch (Exception e) {
          // no jQuery present
          return true;
        }
      }
    };

    // wait for Javascript to load
    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
      public Object getDriver() {
        return wd;
      }


      @Override
      public Boolean apply(WebDriver driver) {
        return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState")
                .toString().equals("complete");
      }
    };

    return wait.until(jQueryLoad) && wait.until(jsLoad);
  }



  protected void showAllOptions() {
    wd.findElement(By.linkText("Показать еще")).click();
  }

  protected void userActions(String mobNo) {
    wd.findElement(By.id("totabs-xx")).click();
    wd.findElement(By.xpath("//div[@id='tabs-m']/div/div/div[1]/ul/li/span")).click();
    wd.findElement(By.xpath("//div[@id='tabs-m']//a[.='776']")).click();
    wd.findElement(By.id("wannamask2")).click();
    wd.findElement(By.id("wannamask2")).clear();
    wd.findElement(By.id("wannamask2")).click();
    wd.findElement(By.id("wannamask2")).sendKeys(mobNo);
    wd.findElement(By.xpath("//div[@id='tabs-m']/div/div/div[3]/a/span[1]")).click();
  }


}
