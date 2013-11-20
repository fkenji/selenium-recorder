package br.com.seibzhen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static br.com.seibzhen.FileUtils.folderNameBasedOn;
import static org.junit.Assert.fail;

public class RemoteSeleniumBackedTest {

    protected RemoteWebDriver webdriver;

    protected String hostAddress;

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

            try {
                hostAddress = new GridNodeLocator("localhost","4444").locate(webdriver);
                logger.info("Grid node at {}", hostAddress);

                String sessionId = webdriver.getSessionId().toString();
                new VideoServiceNotifier().notifyStart(hostAddress, sessionId);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void finished(Description description) {
            logger.debug("Closing RemoteWebDriver");

            try {
                new VideoServiceNotifier().notifyDestroy(hostAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }

            webdriver.quit();
        }

        @Override
        protected void failed(Throwable e, Description description) {
           new ScreenshotTaker(webdriver).takeAsJenkinsPath(description.getTestClass(), description.getMethodName());
            try {

                String sessionId = webdriver.getSessionId().toString();
                new VideoServiceNotifier().notifyStop(hostAddress);
                File video = new VideoServiceNotifier().downloadVideo(hostAddress, sessionId);
                org.apache.commons.io.FileUtils.copyFileToDirectory(video, new File(folderNameBasedOn(description.getTestClass())));
                new VideoServiceNotifier().notifyDestroy(hostAddress);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
