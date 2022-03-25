package org.whatever;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;

@Test
public class ShutterbugTest {


    @DataProvider
    public static Iterator<Object[]> webdriverProvider() {
        return Arrays.asList(new Object[]{getWebDriver()}, new Object[]{getRemoteWebDriver()}).iterator();
    }

    @Test(dataProvider = "webdriverProvider")
    public void testLocal(WebDriver webDriver) {

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

    private static WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        return RemoteWebDriver.builder().addAlternative(new ChromeOptions()).build();
    }
    private static WebDriver getRemoteWebDriver() {
        return RemoteWebDriver.builder().address("http://gpuigrid:4444").addAlternative(new ChromeOptions()).build();
    }
}
