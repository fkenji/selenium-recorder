package br.com.seibzhen

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

class SimpleSeleniumTest {

    WebDriver webdriver;

    @Before
    def void setup() {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        webdriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);

    }

    @Test
    def void test() {
        webdriver.navigate().to("http://www.google.com.br");
        webdriver.findElement(By.cssSelector("#gbqfq")).sendKeys("bulls");

        Thread.sleep(5000L);
    }

    @After
    def void tearDown() {
        webdriver.quit();
    }

}
