package PageObject;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import PageObject.helpers.SmartWaits;

import java.util.concurrent.TimeUnit;


public class HomeExpendiaPageObject  {

	WebDriver driver;

	public HomeExpendiaPageObject(WebDriver driver) {
		this.driver = driver;
	}

	/* selectflight Elements */
	By flight = By.id("tab-flight-tab-hp");

	/* imputflightFrom Elements */
	By focusFrom = By.xpath("//div[@class='input-btn-group']");
	By from_city = By.xpath("//*[@id='flight-origin-hp-flight']");

	/* imputflightTo Elements */
	By to_city = By.xpath("//*[@id='flight-destination-hp-flight']");
	By focus = By.xpath("//*[@id='gcw-flights-form-hp-flight']/div[3]/div[2]/div/div[1]");
	
	/*selectElementDropDownList Elements*/
	By containerResult = By.id("flightModuleList");
	By byNameLocator_ul = By.tagName("ul");
	By byNameLocator_li = By.tagName("li");   

	/*selectDateDeparting Elements*/
	By departingDate = By.id("flight-departing-hp-flight");
	By byNameLocator_td = By.tagName("td");
	
	/*selectDateReturning Elements*/
	By returningDate = By.id("flight-returning-hp-flight");
	By datepickerTable = By.className("datepicker-cal");
	
   /*selectTravelers Elements*/
	By menuTravelers = By.xpath("//*[@id='traveler-selector-hp-flight']/div/ul/li/button");
	By num = By.xpath("//*[@id='traveler-selector-hp-flight']/div/ul/li/div/div/div/div[1]/div[4]/button");

   /*searchButton*/
	By searchButton = By.xpath("//*[@id='gcw-flights-form-hp-flight']/div[7]/label/button");
	
	SmartWaits w= new SmartWaits(driver);
	
	
	/***
	 * Select 'Flights' from menu home page
	 */
	public void selectflight() {

		driver.findElement(flight).click();
	}

	/***
	 * Populate the 'Flying from' input textfile Parameters received from xml fle
	 */
	public void imputflightFrom(String cityFrom) throws InterruptedException {

		driver.findElement(focusFrom).click();
		driver.findElement(from_city).sendKeys(cityFrom);
		// selectElementDropDownList(); // Couldn't fully test as I was working on a
		// virtual machine and I lost the focus
	}

	/***
	 * Populate the 'Flight to' input textfile Parameters received from xml fle
	 * 
	 * @param cityTo
	 * @throws InterruptedException
	 */
	public void imputflightTo(String cityTo) throws InterruptedException {

		driver.findElement(focus).click();
		driver.findElement(to_city).sendKeys(cityTo);
		// selectElementDropDownList();
	}

	/***
	 *  Select one element from the dropdown list that matches with one value
	 */
	public void selectElementDropDownList() {
		
		WebElement childrenContainer = driver.findElement(containerResult).findElement(byNameLocator_ul);
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocator_li);
		for (WebElement child : childrenElements) {
			if (child.getAttribute("class").equals("results-item")) {
				child.click();
			}
		}
	}

	/***
	 *  Select date of departure. Handles datepicker object
	 */
	public void selectDateDeparting() {

		driver.findElement(departingDate).click();
		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(datepickerTable);

		// These are the rows of the 'From date' picker table
		// List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));
		// This are the columns of the from date picker table
		List<WebElement> columns = dateWidgetFrom.findElements(byNameLocator_td);

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			// Select Today's Date
			if (cell.getText().contains(getCurrentDay())) {
				cell.click();
				break;
			}
		}
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS); 
		

	}

	/***
	* Get The Current Day
	*Flight selection restricted for all flights before 5pm. If select flight
	*after 5pm, next day is selected
	* This is because of an expedia.com error
	*/
	private String getCurrentDay() {
		
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		String todayStr = null;
		int currentlyTime = calendar.get(Calendar.HOUR_OF_DAY);
		if (currentlyTime > 17) {
			todayStr = getReturnDay(1);
			return todayStr;
		} else {
			// Get Current Day as a number
			int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
			// Integer to String Conversion
			todayStr = Integer.toString(todayInt);
		}
		return todayStr;
	}

	/***
	 * Select return date. Handles datepicker object
	 */
	public void selectDateReturning() {
		// Handle time
		SmartWaits w= new SmartWaits(driver);
		driver.findElement(returningDate).click();
		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(datepickerTable);

		// This are the rows of the from date picker table
		// List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));

		// This are the columns of the from date picker table
		List<WebElement> columns = dateWidgetFrom.findElements(byNameLocator_td);

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			// Select Today's Date
			if (cell.getText().contains(getReturnDay(6))) {
				cell.click();
				break;
			}
		}
       
	}

	/***
	 *  Get The Return Day
	 *  Calculate number of days between departure date and return date
	 *  Pass this value as a parameter
	 * @param day
	 * @return
	 */	
	public String getReturnDay(int day) {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// Number of days to add
		calendar.add(Calendar.DAY_OF_YEAR, day);
		// Return the Date object with the new days value added
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		return todayStr;
	}

	/***
	 * Select Travelers
	 */
	public void selectTravelers() {
		
		//w.waitForElementToBeClickable(menuTravelers);
		driver.findElement(menuTravelers).click();
		driver.findElement(num).click();
		driver.findElement(menuTravelers).click();
	}

	/***
	 * Press Search button
	 */
	public void searchButton() {
		
		driver.findElement(searchButton).click();

	}
}
