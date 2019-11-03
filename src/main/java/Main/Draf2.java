package Main;

import java.util.Calendar;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Draf2 {

	public static WebDriver driver = null;
	public static void main(String[] args) throws InterruptedException {
		

		try {
			System.setProperty("webdriver.gecko.driver", "c:\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.navigate().to("https://www.expedia.com/");
			driver.manage().window().maximize();

			Draf2 d = new Draf2();
			d.selectflight();
			d.imputflightFrom("Madrid, Spain (MAD)");
			d.imputflightTo("London, England, UK (LON-All Airports)");
			d.selectDateDeparting();
			d.selectDateReturning();
			d.selectTravelers();
			d.searchButton();
			/*
			 * try { Thread.sleep(2000); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
		//	d.comparePrice("€104");
		//	d.showResult();
			d.verifyAirlinesPanel();

		} finally {

			driver.close();
			driver.quit();
			
		}

		// http://download.eclipse.org/egit/updates

	}
    
	// methods

	public void selectflight() {

		By flight = By.id("tab-flight-tab-hp");
		driver.findElement(flight).click();
	}

	// Enter a flightFrom

	public void imputflightFrom(String cityFrom) throws InterruptedException {

		By focus = By.xpath("//div[@class='input-btn-group']");
		By from_city = By.xpath("//*[@id='flight-origin-hp-flight']");
		driver.findElement(focus).click();
		driver.findElement(from_city).sendKeys(cityFrom);
		// selectElementDropDownList();
	}

	// Enter a flightTo

	public void imputflightTo(String cityTo) throws InterruptedException {

		By to_city = By.xpath("//*[@id='flight-destination-hp-flight']");
		By focus = By.xpath("//*[@id='gcw-flights-form-hp-flight']/div[3]/div[2]/div/div[1]");
		driver.findElement(focus).click();
		driver.findElement(to_city).sendKeys(cityTo);

		// selectElementDropDownList();
	}

	// Select an element of the list

	public void selectElementDropDownList() {

		By containerResult = By.id("flightModuleList");

		java.util.List<WebElement> childrenElements = null;

		WebElement childrenContainer = driver.findElement(containerResult).findElement(By.tagName("ul"));
		childrenElements = childrenContainer.findElements(By.tagName("li"));

		for (WebElement child : childrenElements) {

			if (child.getAttribute("class").equals("results-item")) {
				child.click();
			}
		}
	}

	// Enter date of the departing flight

	public void selectDateDeparting() {

		By departingDate = By.id("flight-departing-hp-flight");
		By returningDate = By.id("flight-returning-hp-flight");
		By datepickerTable = By.className("datepicker-cal");

		driver.findElement(departingDate).click();
		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(datepickerTable);

		// This are the rows of the from date picker table
		// List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));

		// This are the columns of the from date picker table
		java.util.List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			/*
			 * //If you want to click 18th Date if (cell.getText().equals("18")) {
			 */
			// Select Today's Date
			System.out.println(cell.getText());
			System.out.println(getCurrentDay());
			if (cell.getText().contains(getCurrentDay())) {
				cell.click();
				break;
			}
		}

		// Wait for 4 Seconds to see Today's date selected.
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// driver.findElement(returningDate).click();

	}

	// Get The Current Day
	private String getCurrentDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);

		return todayStr;
	}

	// Enter date of the returning flight

	public void selectDateReturning() {

		By returningDate = By.id("flight-returning-hp-flight");
		By datepickerTable = By.className("datepicker-cal");

		driver.findElement(returningDate).click();
		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(datepickerTable);

		// This are the rows of the from date picker table
		// List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));

		// This are the columns of the from date picker table
		java.util.List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			/*
			 * //If you want to click 18th Date if (cell.getText().equals("18")) {
			 */
			// Select Today's Date
			System.out.println(cell.getText());
			System.out.println(getReturnDay());
			if (cell.getText().contains(getReturnDay())) {
				cell.click();
				break;
			}
		}

		// Wait for 4 Seconds to see Today's date selected.
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// driver.findElement(returningDate).click();

	}

	// Get The Return Day
	public String getReturnDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		calendar.add(Calendar.DAY_OF_YEAR, 3); // numero de días a añadir, o restar en caso de días<0

		int todayInt = calendar.get(Calendar.DAY_OF_MONTH); // Devuelve el objeto Date con los nuevos días añadidos

		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		return todayStr;
	}

	// Select Travelers
	public void selectTravelers() {

		By menuTravelers = By.xpath("//*[@id='traveler-selector-hp-flight']/div/ul/li/button");
		By num = By.xpath("//*[@id='traveler-selector-hp-flight']/div/ul/li/div/div/div/div[1]/div[4]/button");

		driver.findElement(menuTravelers).click();
		driver.findElement(num).click();
		driver.findElement(menuTravelers).click();
	}

	// Search button
	public void searchButton() {

		By searchButton = By.xpath("//*[@id=\'gcw-flights-form-hp-flight\']/div[7]/label/button");
		driver.findElement(searchButton).click();

	}

	// **************************************************************************************************//

	// Show the first 3 results

	public void showResult() {
		By containerResult = By.xpath("//*[@id=\"flightModuleList\"]");
		java.util.List<WebElement> childrenElements = null;
		java.util.List<WebElement> childrenElemntsbySpan = null;
		WebElement childrenElementsbyH3 = null;
		WebElement childrenContainer = driver.findElement(containerResult);
		childrenElements = childrenContainer.findElements(By.tagName("li"));
		int j = 0;
		for (WebElement cell : childrenElements) {
			childrenElemntsbySpan = cell.findElements(By.tagName("span"));
			childrenElementsbyH3 = cell.findElement(By.tagName("h3"));
			System.out.println(childrenElementsbyH3.getText());

			// resultList.addAll(childrenElemntsbySpan);
			for (int i = 0; i <= childrenElemntsbySpan.size(); i++) {
				if (i == 0) {
					System.out.println(childrenElemntsbySpan.get(i).getText());
					// showResultList.add(childrenElemntsbySpan.get(i).getText());
				}

				if (i == 3) {
					System.out.println(childrenElemntsbySpan.get(i).getText());
					// showResultList.add(childrenElemntsbySpan.get(i).getText());
					System.out.println("------------------------------------------");
					break;
				}

			}
			j++;
			if (j >= 3)
				break;
		}

	}

	// Compare variable price

	public Boolean comparePrice(String price) {

		By containerResult = By.xpath("//*[@id=\"flightModuleList\"]");
		java.util.List<WebElement> childrenElements = null;
		java.util.List<WebElement> childrenElemntsbySpan = null;
		boolean result = false;
		WebElement childrenContainer = driver.findElement(containerResult);
		childrenElements = childrenContainer.findElements(By.tagName("li"));
		for (WebElement cell : childrenElements) {
			childrenElemntsbySpan = cell.findElements(By.tagName("span"));

			for (int i = 0; i <= childrenElemntsbySpan.size(); i++) {
				if (i == 14) {					
					if (childrenElemntsbySpan.get(i).getText().contains(price)) {
						result = true;
						break;
					} else {
						System.out.println("NO MATCH : The price of the flight is: " + childrenElemntsbySpan.get(i).getText()
								+ "The price of the flight searching is:  " + price);
					}
				}
			}
			break;
		}
		return result;
	}

	// Verify the flight by airlines
	
	public void verifyAirlinesPanel() {
		
		//Handle Airline Panel
		By containerAirlines = By.id("airlines");
		java.util.List<WebElement> containerAirlinesPanel = null;
		containerAirlinesPanel= driver.findElement(containerAirlines).findElements(By.tagName("label"));
		String elementBySpan =null;
		String elementAirlineName =null;
		boolean result= false;
		int temp=0;
		for (WebElement cell : containerAirlinesPanel) {
		
		    cell.click();
		    elementAirlineName=cell.findElement(By.className("inline-label")).getText();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			elementBySpan = cell.findElement(By.className("count")).getText();
			temp = Integer.parseInt(elementBySpan);
			break;
		}
		
		// Handle the flight container
		By containerResult = By.xpath("//*[@id=\"flightModuleList\"]");
		java.util.List<WebElement> childrenElements = null;
		
		WebElement childrenContainer = driver.findElement(containerResult);
		childrenElements = childrenContainer.findElements(By.tagName("li"));
		
		int numberoftheflightContainer = childrenElements.size();
						
		if (numberoftheflightContainer== temp ) {			
			System.out.println("The number of the flight into the panel for "+ elementAirlineName+" is: " +numberoftheflightContainer +" the number of the flight found after to apply the filter is: "+ temp);
			result=true;
		}else {
			System.out.println("The number of the flight into the panel for "+ elementAirlineName+" is: " +numberoftheflightContainer +" the number of the flight found after to apply the filter is: "+ temp);
			result=false;
		}
		
			
	}

}
