package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.nopCommerce.admin.AdminLoginPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageUIs.nopCommerce.BasePageUI;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {

    private final int longTimeoutInSecound = GlobalConstants.getGlobalConstants().getLongTimeout();
    private final int shortTimeoutInSecound = GlobalConstants.getGlobalConstants().getShortTimeout();

    public void openPageUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSourceCode(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void zoomMax(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public void setAllCookies(WebDriver driver, Set<Cookie> allCookies) {
        for (Cookie cookie : allCookies) {
            driver.manage().addCookie(cookie);
        }
    }

    public Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    public void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    public String getTextAlert(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    public void sendKeyToAlert(WebDriver driver, String key) {
        waitForAlertPresence(driver).sendKeys(key);
    }

    public void CloseAllWindowWithoutParent(WebDriver driver, String parentID) {
        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            if (!tab.equals(parentID)) {
                driver.switchTo().window(tab);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    public void SwitchToWindowByID(WebDriver driver, String id) {
        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            if (tab.equals(id)) {
                driver.switchTo().window(tab);
                break;
            }
        }
    }

    public void SwitchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            driver.switchTo().window(tab);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    public void SwitchToWindowByUrl(WebDriver driver, String url) {
        Set<String> allTabs = driver.getWindowHandles();
        for (String tab : allTabs) {
            driver.switchTo().window(tab);
            if (driver.getCurrentUrl().equals(url)) {
                break;
            }
        }
    }

    private By getByXpath(String xpathLocator) {
        return By.xpath(xpathLocator);
    }

    protected String getDynamicXpath(String xpathLocator, String... dynamicValues) {
        return String.format(xpathLocator, (Object[]) dynamicValues);
    }

    private WebElement getWebElement(WebDriver driver, String xpathLocator) {
        return driver.findElement(getByXpath(xpathLocator));
    }

    public List<WebElement> getListWebElement(WebDriver driver, String xpathLocator) {
        return driver.findElements(getByXpath(xpathLocator));
    }

    public List<WebElement> getListWebElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        return driver.findElements(getByXpath(xpathLocator));
    }

    public void clickToElement(WebDriver driver, String xpathLocator) {
        waitForElementClickable(driver, xpathLocator);
        getWebElement(driver, xpathLocator).click();
    }

    public void clickToElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementClickable(driver, xpathLocator);
        getWebElement(driver, xpathLocator).click();
    }

    public void senkeyToElement(WebDriver driver, String xpathLocator, String key) {
        waitForElementVisible(driver, xpathLocator);
        WebElement element = getWebElement(driver, xpathLocator);
        element.clear();
        element.sendKeys(key);
    }

    public void senkeyToElement(WebDriver driver, String xpathLocator, String key, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementVisible(driver, xpathLocator);
        WebElement element = getWebElement(driver, xpathLocator);
        element.clear();
        element.sendKeys(key);
    }

    public void selectItemInDefaultDropdown(WebDriver driver, String xpathLocator, String textItem) {
        waitForElementVisible(driver, xpathLocator);
        Select select = new Select(getWebElement(driver, xpathLocator));
        select.selectByVisibleText(textItem);
    }

    public void selectItemInDefaultDropdown(WebDriver driver, String xpathLocator, String textItem, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementVisible(driver, xpathLocator);
        Select select = new Select(getWebElement(driver, xpathLocator));
        select.selectByVisibleText(textItem);
    }

    public String getFirstSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator) {
        Select select = new Select(getWebElement(driver, xpathLocator));
        return select.getFirstSelectedOption().getText();
    }

    public List<WebElement> getAllSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator) {
        Select select = new Select(getWebElement(driver, xpathLocator));
        return select.getAllSelectedOptions();
    }

    public boolean isDropdownMultiple(WebDriver driver, String xpathLocator) {
        Select select = new Select(getWebElement(driver, xpathLocator));
        return select.isMultiple();
    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void SelectItemInCustomDropDown(WebDriver driver, String buttonXpath, String xpathLoadItem, String expected) {
        clickToElement(driver, buttonXpath);
        sleepInSecond(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(xpathLoadItem)));

        List<WebElement> listWebElements = getListWebElement(driver, xpathLoadItem);
        for (WebElement element : listWebElements) {
            if (element.getText().trim().equals(expected)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].click();", element);
                break;
            }
        }
    }

    public void SelectItemInEditDropDown(WebDriver driver, String xpathLocator, String xpathLoadItem, String expected) {
        senkeyToElement(driver, xpathLocator, expected);
        sleepInSecond(2);
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(xpathLoadItem)));

        List<WebElement> listWebElements = getListWebElement(driver, xpathLoadItem);

        for (WebElement element : listWebElements) {
            if (element.getText().trim().equals(expected)) {
                if (element.isDisplayed()) {
                    element.click();
                } else {
                    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
                    sleepInSecond(1);
                    jsExecutor.executeScript("arguments[0].click();", element);
                }
                break;
            }
        }
    }

    public void SelectMultiDropDown(WebDriver driver, String buttonXpath, String loadXpath, String[] expected) {
        clickToElement(driver, buttonXpath);
        sleepInSecond(2);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(loadXpath)));

        List<WebElement> ListWebElements = getListWebElement(driver, loadXpath);
        for (WebElement webElement : ListWebElements) {
            for (String result : expected) {
                if (webElement.getText().trim().equals(result)) {
                    webElement.click();
                    sleepInSecond(2);
                }
            }
        }
        clickToElement(driver, buttonXpath);
        sleepInSecond(2);
    }

    public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName) {
        waitForElementVisible(driver, xpathLocator);
        return getWebElement(driver, xpathLocator).getAttribute(attributeName);
    }

    public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementVisible(driver, xpathLocator);
        return getWebElement(driver, xpathLocator).getAttribute(attributeName);
    }

    public String getElementText(WebDriver driver, String xpathLocator) {
        waitForElementVisible(driver, xpathLocator);
        return getWebElement(driver, xpathLocator).getText();

    }

    public String getElementText(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementVisible(driver, xpathLocator);
        return getWebElement(driver, xpathLocator).getText();
    }

    public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
        waitForElementVisible(driver, xpathLocator);
        return getWebElement(driver, xpathLocator).getCssValue(propertyName);
    }

    public String getHexaColorFromRGBA(String RGBAValue) {
        return Color.fromString(RGBAValue).asHex();
    }

    public int getElementSize(WebDriver driver, String xpathLocator) {
        waitForAllElementVisible(driver, xpathLocator);
        return getListWebElement(driver, xpathLocator).size();
    }

    public int getElementSize(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForAllElementVisible(driver, xpathLocator);
        return getListWebElement(driver, xpathLocator).size();
    }

    public void checkToDefaultCheckbox(WebDriver driver, String xpathLocator) {
        waitForElementClickable(driver, xpathLocator);
        if (!isElementSelected(driver, xpathLocator))
            getWebElement(driver, xpathLocator).click();
    }

    public void checkToDefaultCheckbox(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementClickable(driver, xpathLocator);
        if (!isElementSelected(driver, xpathLocator))
            getWebElement(driver, xpathLocator).click();
    }

    public void uncheckToDefaultCheckbox(WebDriver driver, String xpathLocator) {
        waitForElementClickable(driver, xpathLocator);
        if (isElementSelected(driver, xpathLocator))
            getWebElement(driver, xpathLocator).click();
    }

    public void uncheckToDefaultCheckbox(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementClickable(driver, xpathLocator);
        if (isElementSelected(driver, xpathLocator))
            getWebElement(driver, xpathLocator).click();
    }

    public boolean isElementDisplay(WebDriver driver, String xpathLocator) {
        waitForElementVisible(driver, xpathLocator);
        return getWebElement(driver, xpathLocator).isDisplayed();
    }

    public boolean isElementDisplay(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        return getWebElement(driver, xpathLocator).isDisplayed();
    }

    public void overrideGlobalTimeout(WebDriver driver, int timeOut) {
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    public void waitToElementUndisplayed(WebDriver driver, String xpathLocator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeoutInSecound);
        overrideGlobalTimeout(driver, shortTimeoutInSecound);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
        overrideGlobalTimeout(driver, longTimeoutInSecound);
    }

    public void waitToElementUndisplayed(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeoutInSecound);
        overrideGlobalTimeout(driver, shortTimeoutInSecound);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
        overrideGlobalTimeout(driver, longTimeoutInSecound);
    }

    public boolean isElementUndisplayed(WebDriver driver, String xpathLocator) {
        overrideGlobalTimeout(driver, shortTimeoutInSecound);
        List<WebElement> elements = getListWebElement(driver, xpathLocator);
        overrideGlobalTimeout(driver, longTimeoutInSecound);

        if (elements.size() == 0) {
            return true;
        } else if (!elements.get(0).isDisplayed()) {
            return true;
        }
        return false;
    }

    public boolean isElementUndisplayed(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        overrideGlobalTimeout(driver, shortTimeoutInSecound);
        List<WebElement> elements = getListWebElement(driver, xpathLocator);
        overrideGlobalTimeout(driver, longTimeoutInSecound);

        if (elements.size() == 0) {
            return true;
        } else if (!elements.get(0).isDisplayed()) {
            return true;
        }
        return false;
    }

    public boolean isElementEnable(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isEnabled();
    }

    public boolean isElementEnable(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        return getWebElement(driver, xpathLocator).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isSelected();
    }

    public boolean isElementSelected(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        return getWebElement(driver, xpathLocator).isSelected();
    }

    public void switchToFrameIframe(WebDriver driver, String xpathLocator) {
        driver.switchTo().frame(getWebElement(driver, xpathLocator));
    }

    public void switchToFrameIframe(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void hoverMouseToElement(WebDriver driver, String xpathLocator) {
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, xpathLocator)).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, xpathLocator)).perform();
    }

    public void pressKeyToElement(WebDriver driver, String xpathLocator, Keys key) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, xpathLocator), key).perform();
    }

    public void pressKeyToElement(WebDriver driver, String xpathLocator, Keys key, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, xpathLocator), key).perform();
    }

    public void scrollToBottomPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void hightlightElement(WebDriver driver, String xpathlocator) {
        WebElement element = getWebElement(driver, xpathlocator);
        String originalStyle = element.getAttribute("style");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String xpathLocator) {
        waitForElementVisible(driver, xpathLocator);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
    }

    public void clickToElementByJS(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        waitForElementVisible(driver, xpathLocator);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
    }

    public void scrollToElementOnTop(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locator));
    }

    public void scrollToElementOnDown(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getWebElement(driver, locator));
    }

    public void removeAttributeInDOM(WebDriver driver, String xpathlocator, String attributeRemove) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, xpathlocator));
    }

    public WebElement getShadowDOM(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot;", getWebElement(driver, locator));
        return element;
    }

    public String getElementValidationMessage(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, xpathLocator));
    }

    public boolean isImageLoaded(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
                getWebElement(driver, xpathLocator));
        if (status) {
            return true;
        }
        return false;
    }

    public boolean isJQueryAJAXLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> JQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
                } catch (Exception e) {
                    return true;
                }
            }
        };

        return explicitWait.until(JQueryLoad);
    }

    public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> JQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> JsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(JQueryLoad) && explicitWait.until(JsLoad);
    }

    public void waitForElementVisible(WebDriver driver, String xpathLocator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public void waitForElementVisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public void waitForAllElementVisible(WebDriver driver, String xpathlocator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathlocator)));
    }

    public void waitForAllElementVisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));
    }

    public void waitForElementInvisible(WebDriver driver, String xpathlocator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathlocator)));
    }

    public void waitForElementInvisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public void waitForAllElementInvisible(WebDriver driver, String xpathlocator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, xpathlocator)));
    }

    public void waitForAllElementInvisible(WebDriver driver, String xpathlocator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, xpathlocator, dynamicValues)));
    }

    public void waitForElementClickable(WebDriver driver, String xpathlocator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathlocator)));
    }

    public void waitForElementClickable(WebDriver driver, String xpathLocator, String... dynamicValues) {
        xpathLocator = getDynamicXpath(xpathLocator, dynamicValues);
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeoutInSecound);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
    }

    public void uploadMultipleFiles(WebDriver driver, String... fileNames) {
        String filePath = GlobalConstants.getGlobalConstants().getUploadFilesFolder();
        String fullFileName = "";
        for (String file : fileNames) {
            fullFileName = fullFileName + filePath + file + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(driver, GlobalConstants.getGlobalConstants().getUploadFilesType()).sendKeys(fullFileName);
    }

    public void EnterToTextboxByID(WebDriver driver, String textboxID, String value) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
        waitForElementVisible(driver, locator);
        senkeyToElement(driver, locator, value);
        sleepInSecond(1);
    }

    public void EnterToTextareaByID(WebDriver driver, String textboxID, String value) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_TEXTAREA_BY_ID, textboxID);
        waitForElementVisible(driver, locator);
        senkeyToElement(driver, locator, value);
    }

    public void ClickToCheckboxButtonByID(WebDriver driver, String radioButtonID, boolean status) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_CHECKBOX_BUTTON_BY_ID, radioButtonID);
        waitForElementClickable(driver, locator);
        if (isElementSelected(driver, locator) != status)
            clickToElement(driver, locator);
    }

    public void ClickToRadioButtonByID(WebDriver driver, String radioButtonID) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_RADIO_BUTTON_BY_ID, radioButtonID);
        clickToElementByJS(driver, locator);
    }

    public void SelectDropdownByName(WebDriver driver, String dropdownName, String value) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_DROPDOWN_BY_NAME, dropdownName);
        selectItemInDefaultDropdown(driver, locator, value);
    }

    public void SelectDropdownByID(WebDriver driver, String dropdownID, String value) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_DROPDOWN_BY_ID, dropdownID);
        selectItemInDefaultDropdown(driver, locator, value);
    }

    public void ClickToButtonByText(WebDriver driver, String buttonText) {
        String locator = getDynamicXpath(BasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
        clickToElementByJS(driver, locator);
    }

    ////////////////////////////////////////////////////////////////////////////////////

    public UserHomePageObject ClickToLogoutLinkAtUserPage(WebDriver driver) {
        waitForElementVisible(driver, BasePageUI.LOGOUT_LINK_AT_USER);
        clickToElementByJS(driver, BasePageUI.LOGOUT_LINK_AT_USER);
        return PageGeneratorManager.getUserHomePage(driver);
    }

    public AdminLoginPageObject ClickToLogoutLinkAtAdminPage(WebDriver driver) {
        waitForElementVisible(driver, BasePageUI.LOGOUT_LINK_AT_ADMIN);
        clickToElementByJS(driver, BasePageUI.LOGOUT_LINK_AT_ADMIN);
        return PageGeneratorManager.getAdminLoginPage(driver);
    }
}