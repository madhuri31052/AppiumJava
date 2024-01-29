package appiumTests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


public class BaseClassTest {
	
	public static AppiumDriverLocalService service;
	public static AppiumDriver driver;
	
	@BeforeClass
	public static void BaseClass() throws MalformedURLException, InterruptedException {
		
		service = new AppiumServiceBuilder().withAppiumJS(new File("C://Users//leela//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();
		
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("deviceName", "sdk_gphone64_x86_64");
		cap.setCapability("udid", "emulator-5554");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "12");
		cap.setCapability("appPackage", "com.android.settings");
		cap.setCapability("appActivity", ".Settings t39");
		cap.setCapability("automationName", "UiAutomator2");
		
		URL url = new URL("http://127.0.0.1:4723/");
		driver = new AppiumDriver(url, cap);
		
		driver.findElement(AppiumBy.id("com.android.settings:id/search_action_bar")).click();
		Thread.sleep(2000);
		driver.findElement(AppiumBy.id("com.google.android.settings.intelligence:id/open_search_view_edit_text")).sendKeys("about");
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"About emulated device\"]")).click();
		Thread.sleep(2000);
		driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
		Thread.sleep(2000);
		driver.findElement(AppiumBy.accessibilityId("Back")).click();
		// We often use scroll option. Google has provided an option to use androidUIAutomator for scroll. so we can just change the text and scroll till the text is present
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Tips & support\"));"));
		Thread.sleep(2000);
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Storage\"));"));

	}
	
	@AfterClass
	public void tearDown() {
			driver.quit();
			service.stop();
	}

	
	public static void main(String[] args) throws InterruptedException {
		
		try {
			BaseClass();
		} catch (MalformedURLException e) {
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
