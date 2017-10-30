/**
 * @author zarif.arzimetov
 */

package beeline.number.checker;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBase extends ApplicationManager{

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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


  public void clickAllOptions() {
    boolean a = false;
    boolean b = isElementPresent(By.xpath(".//*[@id='loadMoreTrigger']/span[1]"));
    while (a!=b){
      try {
        click(By.xpath(".//*[@id='loadMoreTrigger']/span[1]"));
      } catch (Exception ex){
        return;
      }

    }

  }

  private void click(By locator) {
    wd.findElement(locator).click();
  }

  private boolean isElementPresent(By locator) {

      try {
        wd.findElement(locator);
        return true;
      } catch (Exception ex) {
        return false;
      }
    }

  //Price Existence Cheking
  protected void priceExistenceChecking() {
    List<WebElement> price = wd.findElements(By.cssSelector(".prc"));
    for (WebElement element: price){
      String priceExistenceChecking = element.getText().replace("Цена:", "").replace("тенге", "").trim();
      Assert.assertNotNull(priceExistenceChecking);
     // System.out.println(priceExistenceChecking); //Debug
    }
  }
  // Bea Existence Checking
  protected void beaExistenceChecking() {
    List<WebElement> beaElement = wd.findElements(By.className("bea"));
    for (WebElement element2 : beaElement) {
      Assert.assertNotNull(element2.getText());
     // System.out.println(element2.getText()); // Debug
    }
  }

  // Free Numbers checking
  protected void freeNumbersChecking(String mobNo, SequenceChecker sequenceChecker) {
    String NoSuchNumbers = isElementNull(By.xpath("html/body/div[4]/div[2]/div[6]/div[3]/div")).getText();
    int length = NoSuchNumbers.length();

    if (length > 0) {
      System.out.println("(✖╭╮✖) NO SUCH FREE numbers");
    } else {
      System.out.println("**** Sequence checking is started **** ");
      System.out.println("          (-(-_(-_-)_-)-)          ");
      List<WebElement> freeNumbers = wd.findElements(By.className("num"));
      for (WebElement element : freeNumbers) {
        String freeNumberElement = element.getText();
        String freeNumberForChecking = (freeNumberElement.replaceAll("\\s+", "")).substring(5);
       // System.out.println(freeNumberForChecking); //Debug
        Assert.assertEquals((sequenceChecker.findMask(mobNo, freeNumberForChecking)), true);

      }

      // Bea Existence Checking
      beaExistenceChecking();

      //Price Existence Cheking
      priceExistenceChecking();

      System.out.println("**** Sequence checking is ended ****");
      System.out.println("             '(◣_◢)'               ");
    }
  }

  private WebElement isElementNull(By locator) {
    try {
      return  wd.findElement(locator);
    } catch (Exception ex) {
      return null;
    }
  }
}
