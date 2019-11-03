package test;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import PageObject.BasePage;
import PageObject.HomeExpendiaPageObject;
import PageObject.ResultFlights;

public class ExpendiaTest extends BasePage {

	     
	/***
	 * Test the parameters received from xml file
	 * @param flightFrom
	 * @param flightTo
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	@Parameters({"flightFrom","flightTo", })
	@Test
	public void findFlight(String flightFrom, String flightTo) throws InterruptedException, IOException {
		setup();
		HomeExpendiaPageObject d = new HomeExpendiaPageObject(driver);
		d.selectflight();
		d.imputflightFrom(flightFrom);
		d.imputflightTo(flightTo);
		d.selectDateDeparting();
		d.selectDateReturning();
		d.selectTravelers();
		// Then press 'Search' button
		d.searchButton();
		try { Thread.sleep(2000); }
		catch (InterruptedException e) {
		 e.printStackTrace(); }
		System.out.println("--------------------------------------");
	}
	
	/***
	 * Test the result of the flight search above
	 */
	@Test(dependsOnMethods = { "findFlight" })
	public void showFlightsResults() {
		
		ResultFlights r = new ResultFlights(driver);
		r.showResult();
		System.out.println("--------------------------------------");
	}
    
	/***
	 *  Compare the price from xml file with the first search result
	 * @param value
	 */
	@Parameters({"Price" })
	@Test(dependsOnMethods = { "findFlight" })
	public void comparePriceResult(String value) {

		ResultFlights r = new ResultFlights(driver);		
		assertTrue(r.comparePrice(value));
		System.out.println("--------------------------------------");
	}
    
	/***
	 * Compare the cheapest price from xml file with the search result
	 * @param value
	 */
	@Parameters({"CheapPrice"})
	@Test(dependsOnMethods = { "findFlight" })
	public void compareCheapPriceResult(String value) {

		ResultFlights r = new ResultFlights(driver);
		try { Thread.sleep(2000); }
		catch (InterruptedException e) {
		 e.printStackTrace(); }
		assertFalse(r.comparePrice(value));
		System.out.println("--------------------------------------");
	}

	/***
	 *  Verify the airline filter works
	 */
	@Test(dependsOnMethods = {"findFlight"})
	public void verifyPanelAirlines() {

		ResultFlights r = new ResultFlights(driver);
		assertTrue(r.verifyAirlinesPanel());
	}
	
	/***
	 * Expedia, Inc. is not responsible for content on external Web sites. © 2019 Expedia, Inc. All rights reserved. 
	 */
	@Test (dependsOnMethods = {"findFlight"})
	public void verifyText() {
		
		ResultFlights r = new ResultFlights(driver);
		assertTrue(r.verifyTextBottonPage());
		
	}

	/***
	 *  Close all browsers and selenium web drivers
	 */
	@AfterTest
	public void cleanUp()  {

		try {
			System.out.println("//******End of the suite**********************//");

		} finally {
			
			//finish();			
		}
	}

}
