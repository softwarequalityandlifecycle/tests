package at.fhv.issdistance;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Step implementation for the sentiment analysis UAT tests
 */
public class ISSDistanceSteps {

	private WebDriver driver;

	/**
	 * Setup the firefox test driver. This needs the environment variable
	 * 'webdriver.gecko.driver' with the path to the geckodriver binary
	 */
	@Before
	public void before(Scenario scenario) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "WIN10");
		capabilities.setCapability("version", "64");
		capabilities.setCapability("browserName", "firefox");
		capabilities.setCapability("name", scenario.getName());

		if (!scenario.getName().endsWith("(video)")) {
			capabilities.setCapability("headless", true);
		}

		driver = new RemoteWebDriver(
				new URL("http://0b081d953efe9c8eb29108f04e696332:cb6c37b5609337c63cf8242c4d8aac39@hub.testingbot.com/wd/hub"),
				capabilities);

		// prevent errors if we start from a sleeping heroku instance
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Shutdown the driver
	 */
	@After
	public void after() {
		driver.quit();
	}

	@Given("^Open (.*?)$")
	public void openUrl(String url) {
		driver.navigate().to(url);
	}

	@Given("^Login with user '(.*?)'$")
	public void login(String user) {
		WebElement emailField = driver.findElement(By.id("username"));
		emailField.sendKeys(user);
		driver.findElement(By.id("loginBtn")).click();
	}

	@When("^Click on Refresh$")
	public void analyzeText() {
		WebElement button = driver.findElement(By.id("analyzeBtn"));
		button.click();
	}

	@When("^I press logout$")
	public void logout() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait until popup is visible
		WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logoutBtn")));
		logoutBtn.click();
	}

	@Then("^I see the login page$")
	public void checkLoginPage() {
		//assertFalse(driver.findElements(By.id("username")).isEmpty());
	}


}
