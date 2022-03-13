package pageObjects.nopCommerce.user;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.nopCommerce.user.AddressesPageUI;

public class UserAddressPageObject extends BasePage {
	private WebDriver driver;

	public UserAddressPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isAddressesPageDisplayed() {
		return isElementDisplay(driver, AddressesPageUI.ADDRESSES_HEADER);
	}
}
