package br.com.seibzhen;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

import static org.junit.Assert.assertEquals;

@Ignore
public class ScreenshotTakerTest {

    public static final String ANY_FILEPATH = "/Users/hien/Downloads/screen.png";

    private ScreenshotTaker screenshotTaker;

    private WebDriver webdriver;

    private File takenScreenshot;

    @Before
    public void setup() {
        webdriver = new FirefoxDriver();
        screenshotTaker = new ScreenshotTaker(webdriver);

        takenScreenshot = screenshotTaker.takeAs(ANY_FILEPATH);
    }

    @Test
    public void itShouldReturnAFile() {
        assertEquals(new File(ANY_FILEPATH), takenScreenshot);
    }

    @Test
    @Ignore
    public void itShouldSaveScreenshotInAJenkinsCompatiblePath() {
        File expectedFile = new File("br.com.seibzhen.ScreenshotTakerTest/screen.png");
        takenScreenshot = screenshotTaker.takeAsJenkinsPath(ScreenshotTakerTest.class, "screen");
        assertEquals(expectedFile, takenScreenshot);
    }

    @After
    public void tearDown() {
        webdriver.quit();
        takenScreenshot.delete();
    }

}
