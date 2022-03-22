package factoryBrowser;

import commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Architecture;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

public class IEDriverManager implements BrowserFactory {
    @Override
    public WebDriver getBrowserDriver() {
        if (!IS_OS_WINDOWS) {
            throw new BrowserNotSupportedException("IE is not supported on " + GlobalConstants.getGlobalConstants().getOsName());
        }

        WebDriverManager.iedriver().architecture(Architecture.X32).setup();
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        capability.setJavascriptEnabled(true);
        capability.setBrowserName("internet explorer");
        capability.setPlatform(Platform.ANY);

        InternetExplorerOptions options = new InternetExplorerOptions(capability);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
        options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
        options.setCapability("ignoreProtectedModeSettings", true);
        options.setCapability("ignoreZoomSettings", true);
        options.setCapability("requireWindowFocus", true);
        options.setCapability("enableElementCacheCleanup", true);

        return new InternetExplorerDriver(options);
    }
}
