package pageUIs.nopCommerce;

public class BasePageUI {
	public static final String DYNAMIC_TEXTBOX_BY_ID = "//input[@id='%s']";
	public static final String DYNAMIC_TEXTAREA_BY_ID = "//textarea[@id='%s']";
	public static final String DYNAMIC_RADIO_BUTTON_BY_ID = "//input[@id='%s']";
	public static final String DYNAMIC_CHECKBOX_BUTTON_BY_ID = "//input[@id='%s']";
	public static final String DYNAMIC_DROPDOWN_BY_NAME = "//select[@name='%s']";
	public static final String DYNAMIC_DROPDOWN_BY_ID = "//select[@id='%s']";
	public static final String DYNAMIC_BUTTON_BY_TEXT = "//button[normalize-space()='%s']";

	public static final String LOGOUT_LINK_AT_USER = "//div[@class='header']//a[@class='ico-logout']";
	public static final String LOGOUT_LINK_AT_ADMIN = "//a[text()='Logout']";
}
