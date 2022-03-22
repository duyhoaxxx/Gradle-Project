package com.nopcommerce.user;

import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.nopCommerce.admin.AdminDashboardPageObject;
import pageObjects.nopCommerce.admin.AdminLoginPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;
import pageUIs.nopCommerce.admin.AdminLoginPageUI;

import java.util.Random;
import java.util.Set;

public class User_01_Switch_Role extends BaseTest {
    private UserHomePageObject userHomePage;
    private UserRegisterPageObject userRegisterPage;
    private UserLoginPageObject userLoginPage;
    private AdminLoginPageObject adminLoginPage;
    private AdminDashboardPageObject adminDashboardPage;

    WebDriver driver;
    public static Set<Cookie> LoginPageCookie;
    private String firstName, lastName, email, password;

    @Parameters({"envName", "serverName", "browser", "ipAddress", "portNumber", "osName", "osVersion"})
    @BeforeClass
    private void beforeClass(@Optional("local") String envName, @Optional("admin") String serverName, @Optional("chrome") String browserName, @Optional("localhost") String ipAddress, @Optional("4444") String portNumber, @Optional("Windows") String osName, @Optional("10") String osVersion) {
        driver = getBrowserDriver(envName, serverName, browserName, ipAddress, portNumber, osName, osVersion);
        userHomePage = PageGeneratorManager.getUserHomePage(driver);

        firstName = "Kane";
        lastName = "Pham";
        email = fakeEmail();
        password = "123456";
    }

    @Test
    public void TC_01_Register_Valid() {
        userRegisterPage = userHomePage.clickToResgisterLink();

        log.info("Register with email" + email + "   Pass: 123456");
        userRegisterPage.inputToFirstNameTextbox(firstName);
        userRegisterPage.inputToLastNameTextbox(lastName);
        userRegisterPage.inputToEmailTextbox(email);
        userRegisterPage.inputToPasswordTextbox(password);
        userRegisterPage.inputToConfirmPasswordTextbox(password);

        userRegisterPage.clickToRegisterButton();

        Assert.assertEquals(userRegisterPage.getSuccessRegisterMessage(), "Your registration completed");

        userHomePage = userRegisterPage.ClickToLogoutLinkAtUserPage(driver);
    }

    @Test
    public void TC_02_Login_User() {
        userLoginPage = userHomePage.clickToLoginLink();
        userHomePage = userLoginPage.LoginAsUser(email, password);

        LoginPageCookie = userHomePage.getAllCookies(driver);
        log.info("Verify");
        verifyEquals(userHomePage.getTopicBlockTitle(), "Welcome to our store");
        log.info("Assert");
        Assert.assertEquals(userHomePage.getTopicBlockTitle(), "Welcome to our store");
        userHomePage = userHomePage.ClickToLogoutLinkAtUserPage(driver);
    }

    @Test
    @Step("Login_Admin")
    public void TC_03_Login_Admin() {
        userHomePage.openPageUrl(driver, GlobalConstants.getGlobalConstants().getAdminPageUrl());
        adminLoginPage = PageGeneratorManager.getAdminLoginPage(driver);
        adminDashboardPage = adminLoginPage.LoginAsUser(AdminLoginPageUI.EMAIL_ADMIN, AdminLoginPageUI.PASSWORD_ADMIN);
        Assert.assertTrue(adminDashboardPage.isDashboardPageDisplayed());
        adminLoginPage = adminDashboardPage.ClickToLogoutLinkAtAdminPage(driver);

        adminLoginPage.openPageUrl(driver, GlobalConstants.getGlobalConstants().getPortalPageUrl());
        userHomePage = PageGeneratorManager.getUserHomePage(driver);
        userLoginPage = userHomePage.clickToLoginLink();
        showBrowserConsoleLogs(driver);

        userLoginPage.setAllCookies(driver, LoginPageCookie);
        userLoginPage.sleepInSecond(5);
        userLoginPage.refreshCurrentPage(driver);
    }

    @Parameters("browser")
    @AfterClass(alwaysRun = true)
    private void afterClass(String browserName) {
        log.info("Post-Condition: Close browser " + browserName);
        cleanBrowserAndDriver();
    }

    private String fakeEmail() {
        return "AutoTest" + (new Random().nextInt(9999)) + "@gmail.com";
    }
}