/**
 * @author zarif.arzimetov
 */

package beeline.number.checker;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;

public class ApplicationManager {
  protected static ChromeDriverService service;
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

  public static boolean isAlertPresent(ChromeDriver wd) {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  protected void init() {
    wd.get("https://nomer.beeline.kz/ru/Numbers");
  }
}
