package beeline.number.checker;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.io.File;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import static org.openqa.selenium.OutputType.*;

public class NewChecker extends TestBase {
    FirefoxDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "C://chrm/geckodriver.exe");
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
    
    @Test
    public void NewChecker() {
        wd.get("https://nomer.beeline.kz/ru/Numbers");
        wd.findElement(By.xpath("//a[@id='totabs-l']/div/img[1]")).click();
        wd.findElement(By.id("wannacnt")).click();
        wd.findElement(By.id("wannacnt")).clear();
        wd.findElement(By.id("wannacnt")).sendKeys("19");
        wd.findElement(By.xpath("//div[@id='tabs-l']//button[.='Подобрать']")).click();
        wd.findElement(By.xpath("//*[text()='Показать больше']")).click();
        List<WebElement> labels = wd.findElements(By.xpath(".//*[@id='numbersXssArea2']/div/div"));
        int ProdNum = labels.size();
        String strI1 = Integer.toString(ProdNum);
        System.out.print("Number of products on this card:" + strI1 + " and labels.size = " + labels.size() );
        System.out.print("____________________________");
        // Bea Existence Checking
       // beaExistenceChecking();

        //Price Existence Cheking
       // priceExistenceChecking();
    }
    
    @AfterMethod
    public void tearDown() {
        wd.quit();
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
