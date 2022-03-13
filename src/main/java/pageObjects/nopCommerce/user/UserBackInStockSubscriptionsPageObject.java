package pageObjects.nopCommerce.user;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.nopCommerce.user.BackInStockSubscriptionsPageUI;

public class UserBackInStockSubscriptionsPageObject extends BasePage {
	private WebDriver driver;

	public UserBackInStockSubscriptionsPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isBackInStockSubscriptionsPageDisplayed() {
		return isElementDisplay(driver, BackInStockSubscriptionsPageUI.BACK_IN_STOCK_SUBSCRIPTIONS_HEADER);
	}
}
