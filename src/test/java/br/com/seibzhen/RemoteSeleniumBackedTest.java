package br.com.seibzhen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.fail;

public class RemoteSeleniumBackedTest {

    protected RemoteWebDriver webdriver;

    private Logger logger = LoggerFactory.getLogger(RemoteSeleniumBackedTest.class);

    @Before
    public void setUp() {
        if (webdriver == null) {
            fail("Webdriver is null!");
        }
    }

    @Rule
    public TestRule seleniumRules = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            logger.debug("Instantiating RemoteWebDriver");
            webdriver = createRemoteSelenium();
        }

        @Override
        protected void finished(Description description) {
            logger.debug("Closing RemoteWebDriver");
            webdriver.quit();
        }

        @Override
        protected void failed(Throwable e, Description description) {
            logger.debug("Taking screenshot. . .");
           new ScreenshotTaker(webdriver).takeAsJenkinsPath(description.getTestClass(), description.getMethodName());
        }
    };

    private RemoteWebDriver createRemoteSelenium() {
        try {
            DesiredCapabilities capability = DesiredCapabilities.chrome();
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
