package commons;

import factoryEnvironment.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private WebDriver driver;
    protected final Log log;

    protected BaseTest() {
        log = LogFactory.getLog(getClass());
    }

    protected WebDriver getBrowserDriver(String envName, String serverName, String browserName, String ipAddress, String portNumber, String osName, String osVersion) {
        switch (envName) {
            case "local":
                driver = new LocalFactory(browserName).createDriver();
                break;
            case "grid":
                driver = new GridFactory(browserName, ipAddress, portNumber).createDriver();
                break;
            case "browserStack":
                driver = new BrowserStackFactory(browserName, osName, osVersion).createDriver();
                break;
            case "saucelab":
                driver = new SaucelabFactory(browserName, osName).createDriver();
                break;
            case "crossBrowser":
                driver = new CrossBrowserFactory(browserName, osName).createDriver();
                break;
            case "lambda":
                driver = new LambdaFactory(browserName, osName).createDriver();
                break;
            default:
                driver = new LocalFactory(browserName).createDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(getEnvironmentValue(serverName));
        return driver;
    }

    private String getEnvironmentValue(String serverName) {
        String envUrl = null;
        EnvironmentList environment = EnvironmentList.valueOf(serverName.toUpperCase());
        if (environment == EnvironmentList.DEV)
            envUrl = "http://demo.guru99.com/v1";
        else if (environment == EnvironmentList.TESTING)
            envUrl = "http://demo.guru99.com/v2";
        else if (environment == EnvironmentList.STAGING)
            envUrl = "http://demo.guru99.com/v3";
        else if (environment == EnvironmentList.PRODUCTION)
            envUrl = "http://demo.guru99.com/v4";
        else if (environment == EnvironmentList.PORTABLE)
            envUrl = "https://demo.nopcommerce.com/";
        else if (environment == EnvironmentList.ADMIN)
            envUrl = "https://admin-demo.nopcommerce.com/";

        return envUrl;
    }

    public WebDriver getDriverInstance() {
        return this.driver;
    }

    private boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertTrue(condition);
            log.info(" ----------------------VerifyTrue: PASSED ---------------------- ");
        } catch (Throwable e) {
            log.info(" ----------------------VerifyTrue: FAILED ---------------------- ");
            pass = false;

            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    private boolean checkFailed(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertFalse(condition);
            log.info(" ----------------------VerifyFalse: PASSED ---------------------- ");
        } catch (Throwable e) {
            log.info(" ----------------------VerifyFalse: FAILED ---------------------- ");
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        return checkFailed(condition);
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
            log.info(" ----------------------VerifyEquals: PASSED ---------------------- ");
        } catch (Throwable e) {
            pass = false;
            log.info(" ----------------------VerifyEquals: PASSED ---------------------- ");
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }

    @BeforeTest
    public void deleteAllFilesInReportNGScreenshot() {
        log.info("delete file in folder Report Image");
        try {
            String pathFolder = GlobalConstants.getGlobalConstants().getProjectPath() + File.separator + "ReportNGImage";
            File file = new File(pathFolder);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        log.info("delete file in folder Allure-json");
        try {
            String pathFolder = GlobalConstants.getGlobalConstants().getProjectPath() + File.separator + "allure-json";
            File file = new File(pathFolder);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    protected void cleanBrowserAndDriver() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
        }

        String cmd = "";
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            log.info("OS name = " + osName);

            String driverInstanceName = driver.toString().toLowerCase();
            log.info("Driver instance name = " + osName);

            if (driverInstanceName.contains("chrome")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                } else {
                    cmd = "pkill chromedriver";
                }
            } else if (driverInstanceName.contains("internetexplorer")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                }
            } else if (driverInstanceName.contains("firefox")) {
                if (osName.contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
                } else {
                    cmd = "pkill geckodriver";
                }
            } else if (driverInstanceName.contains("edge")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
                } else {
                    cmd = "pkill msedgedriver";
                }
            } else if (driverInstanceName.contains("opera")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
                } else {
                    cmd = "pkill operadriver";
                }
            } else if (driverInstanceName.contains("safari")) {
                if (osName.contains("mac")) {
                    cmd = "pkill safaridriver";
                }
            }

            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.quit();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void showBrowserConsoleLogs(WebDriver driver) {
        if (driver.toString().contains("chrome")) {
            LogEntries logs = driver.manage().logs().get("browser");
            List<LogEntry> logList = logs.getAll();
            for (LogEntry logging : logList) {
                log.info("~~~~~~~~~~~~~" + logging.getLevel().toString() + "~~~~~~~~~~~~~ \n" + logging.getMessage());
            }
        }
    }
}
