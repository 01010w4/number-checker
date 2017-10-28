/**
 *
 * @author zarif.arzimetov
 */

package beeline.number.checker;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
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
            wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }

        @Test
        // TestCase
        public void Check() {
            String mobNo = "010208";
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
            wd.findElement(By.linkText("Показать еще")).click();
            Assert.assertEquals((sequenceChecker.findMask(mobNo, "0010208")), true);
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
                    return ((Long)((JavascriptExecutor)getDriver()).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
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
                return ((JavascriptExecutor)getDriver()).executeScript("return document.readyState")
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


