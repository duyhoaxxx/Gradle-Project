package pageObjects.nopCommerce.user;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.nopCommerce.user.DownloadableProductsPageUI;

public class UserDownloadableProductsPageObject extends BasePage {
	private WebDriver driver;

	public UserDownloadableProductsPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isDownloadableProductsPageDisplayed() {
		return isElementDisplay(driver, DownloadableProductsPageUI.DOWNLOADABLE_PRODUCTS_HEADER);
	}
}
