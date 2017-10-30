/**
 * @author zarif.arzimetov
 */

package beeline.number.checker;

import org.testng.annotations.Test;

public class Check extends TestBase{

  @Test
  // TestCase
  public void Check() {

    String mobNo = "AABBB";

    /**
     *@autor zarif.arzimetov
     * Checked on next mobNo values
     * 010208, 010288, AABBB, ABCD, AAABBBB
     */

    SequenceChecker sequenceChecker = new SequenceChecker();
//  System.setProperty("webdriver.chrome.driver", "C://chrm/chromedriver.exe");
    init(); // initialization
    waitForJSandJQueryToLoad();
    userActions(mobNo); // click "Другие" button, choose prefix, enter preferred numNo and Enter "Search"
    waitForJSandJQueryToLoad();
    clickAllOptions(); // show All Options

    // Free Numbers checking
    freeNumbersChecking(mobNo, sequenceChecker);

    // Bea Existence Checking
   // beaExistenceChecking();

    // Price Existence Cheking
   // priceExistenceChecking();
  }

}


