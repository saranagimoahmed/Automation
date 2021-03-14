package Testing.Automation;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Page {
	WebDriver driver;
    //URL-Website
	String PAGE_URL = "https://arielkiell.wixsite.com/interview";

	// Locators
	@FindBy(id = "comp-iy4cwgmq1label")
	WebElement Shop; //shop id 
	@FindBy(xpath = "/html/body/div[1]/div/div[4]/div/main//div[2]//div[1]//div/section/div/ul/li[4]/div/a/div[2]/div/span[2]")
	WebElement Product; //4thproduct 
	@FindBy(xpath = "//input[@type='radio' and @class='_1-wlP']")
	WebElement Color; //black color
	@FindBy(css = "span._11lkb")
	WebElement QuantityButton; //quantity arrow
	@FindBy(xpath = "/html/body/div[1]/div/div[4]/div/main//div[2]//div/article/div[1]/div/article/section[2]/div[7]/div[2]/button/span")
	WebElement AddCart; //button-Add cart

	@FindBy(xpath = "//a[@id=\"widget-view-cart-button\"]")
	WebElement ViewCart; //button-viewcart

	@FindBy(id = "total-sum")
	WebElement TotalSum; //total paid

	// Constructor
	public Page(WebDriver driver) {
		this.driver = driver;
		driver.get(PAGE_URL);
		// Initialise Elements
		PageFactory.initElements(driver, this);
	}

	// Take screen shot
	public String takeScreenshot(String fileName) {
		fileName = fileName + ".png";
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		try {
			FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir") + "//Screenshots//" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String destination = System.getProperty("user.dir") + "//Screenshots//" + fileName;
		return destination;
	}

	// Click on shop
	public void Gotoshop() {

		Shop.click();
		this.takeScreenshot("STEP-1");
	}

	// Select Product
	public void SelectProduct() {
		Product.click();
		this.takeScreenshot("STEP-2");
	}

	// Select Color
	public void SelectColor() throws Exception {

		WebElement Black = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(Color));
		Thread.sleep(5000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Black);
		this.takeScreenshot("STEP-3");

	}
    //Select 3 items 
	public void SelectQuantity() throws Exception {
		Thread.sleep(3000);
		for (int i = 0; i < 2; i++) {
			Actions action = new Actions(driver);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			action.doubleClick(QuantityButton).build();
			js.executeScript("arguments[0].click();", QuantityButton);
		}
		this.takeScreenshot("STEP-4");

	}
     //add to cart
	public void ADDTOCART() {

		AddCart.click();
		this.takeScreenshot("STEP-5");

	}
	/*this function to click on view cart and check price of items but unfortunately i can not detect frame or view cart element so i can not run this part */

	public void CART() throws InterruptedException {
		Thread.sleep(7000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/cart-popup/div[2]/header")));
		WebElement iframe = driver.findElement(By.xpath("/html/body/cart-popup/div[2]/header"));
		driver.switchTo().frame(iframe);
		ViewCart.click();
		ViewCart.click();
		Thread.sleep(5000);
		String ActualTotalPaid = TotalSum.getText();
		String ExpectedTotalPaid = "C$75.00";
		Assert.assertEquals(ActualTotalPaid, ExpectedTotalPaid);
		this.takeScreenshot("STEP-6");

	}
}