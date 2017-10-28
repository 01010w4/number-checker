/**
 * @author zarif.arzimetov
 */

package beeline.number.checker;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Check {

  private static ChromeDriverService service;
  ChromeDriver wd;

  @BeforeClass
  // chromedriver service.start
  public static void createAndStartService() throws IOException {
    service = new ChromeDriverService.Builder()
            .usingDriverExecutable(new File("C://chrm/chromedriver.exe"))
            .usingAnyFreePort()
            .build();
    System.setProperty("webdriver.chrome.driver", "C://chrm/chromedriver.exe");
    try {
      service.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @AfterClass
  // chromedriver service.stop
  public static void createAndStopService() {
    service.stop();
  }

  public static boolean isAlertPresent(FirefoxDriver wd) {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
  }

  @Test
  // TestCase
  public void Check() {
    String mobNo = "010308";
    SequenceChecker sequenceChecker = new SequenceChecker();
//          System.setProperty("webdriver.chrome.driver", "C://chrm/chromedriver.exe");
    wd.get("https://nomer.beeline.kz/ru/Numbers");
    waitForJSandJQueryToLoad();
    wd.findElement(By.id("totabs-xx")).click();
    wd.findElement(By.xpath("//div[@id='tabs-m']/div/div/div[1]/ul/li/span")).click();
    wd.findElement(By.xpath("//div[@id='tabs-m']//a[.='776']")).click();
    wd.findElement(By.id("wannamask2")).click();
    wd.findElement(By.id("wannamask2")).clear();
    wd.findElement(By.id("wannamask2")).click();
    wd.findElement(By.id("wannamask2")).sendKeys(mobNo);
    wd.findElement(By.xpath("//div[@id='tabs-m']/div/div/div[3]/a/span[1]")).click();
    waitForJSandJQueryToLoad();
    wd.findElement(By.linkText("Показать еще")).click();

    // Free Numbers checking
    List<WebElement> freeNumbers = wd.findElements(By.className("num"));
    for (WebElement element : freeNumbers) {
      String freeNumberElement = element.getText();
      String freeNumberForChecking = (freeNumberElement.replaceAll("\\s+", "")).substring(5);
      System.out.println(freeNumberElement);
      Assert.assertEquals((sequenceChecker.findMask(mobNo, freeNumberForChecking)), true);
    }

    // Bea Existence Checking
    List<WebElement> beaElement = wd.findElements(By.className("bea"));
    for (WebElement element2 : beaElement) {
      Assert.assertNotNull(element2.getText());
      System.out.println(element2.getText());
    }

    // Price Existence Cheking
    List<WebElement> price = wd.findElements(By.cssSelector(".prc"));
    for (WebElement element: price){
      String priceExistenceChecking = element.getText().replace("Цена:", "").replace("тенге", "").trim();
      Assert.assertNotNull(priceExistenceChecking);
      System.out.println(priceExistenceChecking);
    }


    // Debugger for catching non-filled numbers
    // System.out.println(freeNumbers);                // Free number //
    //System.out.println(freeNumberForChecking);      //  Free number for Checking (trimmed)
    // System.out.println(priceExistenceChecking);     // Trimmed Price
    // System.out.println("________________________"); // Next Free number


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

  @AfterMethod
  public void tearDown() {
    wd.quit();
  }

}


