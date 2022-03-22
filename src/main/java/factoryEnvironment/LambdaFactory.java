package factoryEnvironment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class LambdaFactory {
    private WebDriver driver;
    private String browserName;
    private String osName;

    public LambdaFactory(String browserName, String osName) {
        this.browserName = browserName;
        this.osName = osName;
    }

    public WebDriver createDriver() {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("platform", osName);
        capability.setCapability("browserName", browserName);
        capability.setCapability("version", "latest");
        capability.setCapability("video", "true");
        capability.setCapability("visual", "true");
        capability.setCapability("name", "Run on " + osName + "|" + browserName);
        if (osName.contains("Windows")) {
            capability.setCapability("screenResolution", "1920x1080");
        } else {
            capability.setCapability("screenResolution", "1920x1440");
        }

        try {
            driver = new RemoteWebDriver(new URL("http://......"), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
