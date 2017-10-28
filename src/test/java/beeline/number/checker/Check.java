/**
 * @author zarif.arzimetov
 */

package beeline.number.checker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Check extends TestBase{

  @Test
  // TestCase
  public void Check() {
    String mobNo = "010308";
    SequenceChecker sequenceChecker = new SequenceChecker();
//          System.setProperty("webdriver.chrome.driver", "C://chrm/chromedriver.exe");
    init();
    waitForJSandJQueryToLoad();
    userActions(mobNo);
    waitForJSandJQueryToLoad();
    showAllOptions();

    // Free Numbers checking
    List<WebElement> freeNumbers = wd.findElements(By.className("num"));
    for (WebElement element : freeNumbers) {
      String freeNumberElement = element.getText();
      String freeNumberForChecking = (freeNumberElement.replaceAll("\\s+", "")).substring(5);
     // System.out.println(freeNumberForChecking); Debug
      Assert.assertEquals((sequenceChecker.findMask(mobNo, freeNumberForChecking)), true);
    }

    // Bea Existence Checking
    List<WebElement> beaElement = wd.findElements(By.className("bea"));
    for (WebElement element2 : beaElement) {
      Assert.assertNotNull(element2.getText());
    //  System.out.println(element2.getText()); Debug
    }

    // Price Existence Cheking
    List<WebElement> price = wd.findElements(By.cssSelector(".prc"));
    for (WebElement element: price){
      String priceExistenceChecking = element.getText().replace("Цена:", "").replace("тенге", "").trim();
      Assert.assertNotNull(priceExistenceChecking);
    //  System.out.println(priceExistenceChecking); //Debug
    }

  }

}


