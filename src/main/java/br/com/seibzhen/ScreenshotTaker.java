package br.com.seibzhen;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ScreenshotTaker {

    private static final String SCREENSHOT_FILE_EXTENSION = ".png";
    private static final String PACKAGE_CLASS_SEPARATOR = ".";
    private WebDriver driver;

    private Logger logger = LoggerFactory.getLogger(ScreenshotTaker.class);

    public ScreenshotTaker(WebDriver driver) {
        if (driver instanceof TakesScreenshot) {
            this.driver = driver;
        } else {
            logger.debug("Augmenting {}", driver);
            this.driver = new Augmenter().augment(driver);
        }

    }

    public File takeAs(String destination) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(destination));
            logger.info("Screenshot taken at {}", destination);
            return new File(destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File takeAsJenkinsPath(Class clazz, String fileName) {
        return takeAs(folderNameBasedOn(clazz) + File.separator + fileName + SCREENSHOT_FILE_EXTENSION);
    }

    private String folderNameBasedOn(Class clazz) {
        return clazz.getPackage().getName() + PACKAGE_CLASS_SEPARATOR + clazz.getSimpleName();
    }
}
