import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Check {

    private static ChromeDriverService service;
    ChromeDriver wd;
    SequenceCheck z = new SequenceCheck();

    @BeforeClass
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


        @BeforeMethod
        public void setUp() throws Exception {
            wd = new ChromeDriver();
            wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }

        @Test
        public void Check() {
            String mobNo = "010208";
//          System.setProperty("webdriver.chrome.driver", "C://chrm/chromedriver.exe");
            wd.get("https://nomer.beeline.kz/ru/Numbers");
            wd.findElement(By.id("totabs-xx")).click();
            wd.findElement(By.xpath("//div[@id='tabs-m']/div/div/div[1]/ul/li/span")).click();
            wd.findElement(By.xpath("//div[@id='tabs-m']//a[.='776']")).click();
            wd.findElement(By.id("wannamask2")).click();
            wd.findElement(By.id("wannamask2")).clear();
            wd.findElement(By.id("wannamask2")).click();
            wd.findElement(By.id("wannamask2")).sendKeys(mobNo);

            wd.findElement(By.xpath("//div[@id='tabs-m']/div/div/div[3]/a/span[1]")).click();
            wd.findElement(By.linkText("Показать еще")).click();
            z.findMask("mobNo", "1234511");
        }

        @AfterMethod
        public void tearDown() {
            wd.quit();
        }

    @AfterClass
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

    }


