package br.com.seibzhen;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

public class SimpleSeleniumTest extends RemoteSeleniumBackedTest {

    @Test
    public void testIt() throws InterruptedException {
        webdriver.navigate().to("http://www.google.com.br");
        webdriver.findElement(By.cssSelector("#gbqfq")).sendKeys("bulls");

        Thread.sleep(5000L);
        org.junit.Assert.fail();
    }

}
