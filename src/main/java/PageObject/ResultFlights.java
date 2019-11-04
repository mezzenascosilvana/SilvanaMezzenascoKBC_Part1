package PageObject;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import PageObject.helpers.SmartWaits;

public class ResultFlights {

	WebDriver driver;

	public ResultFlights(WebDriver driver) {
		this.driver = driver;
	}

	/* verifyAirlinesPanel Elements */
	By containerAirlines = By.id("airlines");
	By byNameLocatorLabel = By.tagName("label");
	By byClassLocator = By.className("inline-label");
	By byClassLocatorCount = By.className("count");
	By byNameLocatorLi = By.tagName("li");
	By byNameLocatorSpan = By.tagName("span");
	By byNameLocatorH3 = By.tagName("h3");
	By containerResult = By.xpath("//*[@id='flightModuleList']");
	By byClassLocatorLegal = By.className("legal");
	By byNameLocatorDiv = By.tagName("div");

	// Handle time
	SmartWaits w = new SmartWaits(driver);

	/***
	 * Verify airline filters works Select one airline and compare filter result
	 * matches actual displayed result
	 * 
	 * @return
	 */
	public Boolean verifyAirlinesPanel() {

		// Handle Airline Panel
		List<WebElement> containerAirlinesPanel = driver.findElement(containerAirlines)
				.findElements(byNameLocatorLabel);
		String elementBySpan = null;
		String elementAirlineName = null;
		boolean result = false;
		int temp = 0;
		for (WebElement cell : containerAirlinesPanel) {
			cell.click(); // click on the firsht airline
			elementAirlineName = cell.findElement(byClassLocator).getText();
			// w.waitForElementToBeClickable(byClassLocator_count);
			elementBySpan = cell.findElement(byClassLocatorCount).getText();
			temp = Integer.parseInt(elementBySpan);
			System.out.println(
					"The number of the flights into the panel for " + "(" + elementAirlineName + ")" + " is: " + temp);
			if (cell.isDisplayed()) {
				result = true;
			} else {
				result = false;
				break;
			}
			break;
		}
		return result;
	}

	/***
	 * Compares price received with first result returned from website
	 * 
	 * @param price
	 * @return
	 */
	public Boolean comparePrice(String price) {

		List<WebElement> childrenElementsbySpan = null;
		WebElement childrenElementsbyH3 = null;
		boolean result = false;
		String temp = null;
		WebElement childrenContainer = driver.findElement(containerResult);
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocatorLi);
		for (WebElement cell : childrenElements) {
			childrenElementsbySpan = cell.findElements(byNameLocatorSpan);
			childrenElementsbyH3 = cell.findElement(byNameLocatorH3);
			temp = handleString(childrenElementsbyH3.getText());
			for (int i = 0; i <= childrenElementsbySpan.size();) {
				if (temp.contains(String.valueOf(price))) {
					result = true;
					break;
				} else {
					System.out.println("NO MATCH : The price of the first flight is €: " + temp
							+ "The price of the flight searching is €:  " + price);
					break;
				}
			}
			break;
		}
		return result;
	}

	/***
	 * Parses one string and obtains flight price
	 * 
	 * @param value
	 * @return
	 */
	public String handleString(String value) {
		String result = null;
		char buf[] = new char[32];
		value.getChars(32, 35, buf, 0);
		result = new String(buf);
		return result;
	}

	/***
	 * Displays first 3 results in console Displays price flight, departure date,
	 * return date and airline name
	 */
	public void showResult() {
		List<WebElement> childrenElements = null;
		List<WebElement> childrenElemntsbySpan = null;
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

				if (i == 5) {
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

	/***
	 * Verify if the element is into the page and it is showing in console
	 * 
	 */
	public Boolean verifyTextBottonPage() {
		boolean result = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Find element by link text and store in variable "Element"
		WebElement element = driver.findElement(byClassLocatorLegal);

		// This will scroll the web page till end.
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		// This will scroll the page till the element is found

		if (element.isDisplayed())
			result = true;
		System.out.println(element.getText());
		return result;
	}

}
