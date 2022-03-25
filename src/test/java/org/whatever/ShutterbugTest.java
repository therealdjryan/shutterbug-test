package org.whatever;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;

@Test
public class ShutterbugTest {

    /**
     * this case passes and creates a screenshot
     */
    public void testLocal() {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        WebDriver webDriver = RemoteWebDriver.builder().addAlternative(new ChromeOptions()).build();
        try {
            webDriver.navigate().to("https://google.com");
            Path tempDirectory = new File("/tmp/test").toPath();
            Shutterbug.shootPage(webDriver, Capture.FULL).withName("foo").save(tempDirectory.toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            webDriver.quit();
        }
    }

    /**
     * this case fails and throws an exception
     */
    public void testRemote() {

        // use a valid selenium grid url
        WebDriver webDriver =
                RemoteWebDriver.builder().address("http://gridaddress:4444")
                        .addAlternative(new ChromeOptions()).build();
        try {
            webDriver.navigate().to("https://google.com");
            Path tempDirectory = new File("/tmp/test").toPath();
            Shutterbug.shootPage(webDriver, Capture.FULL).withName("foo").save(tempDirectory.toString());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            webDriver.quit();
        }
    }
}
