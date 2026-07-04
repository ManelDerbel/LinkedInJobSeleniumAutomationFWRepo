package com.qa.LinkedInJob.utils;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtils {

	private WebDriver driver;
	private Actions action;

	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	public WebElement getElement(By locator) {
		WebElement el = driver.findElement(locator);

		return el;
	}

	public List<WebElement> getElements(By locator) {
		List<WebElement> elList = driver.findElements(locator);

		return elList;
	}

	public String getLinkpointedElement(WebElement el, By locator) {
		String link = el.findElement(locator).getAttribute("href");

		return link;
	}

	public String getTextpointedElement(WebElement el, By locator) {
		String text = el.findElement(locator).getText();

		return text;
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public WebElement waitForElementVisibility(int TimeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		return el;
	}

	public WebElement waitForElementToBeClickable(int TimeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));

		return el;
	}

	public List<WebElement> waitForElementsVisibility(int TimeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		List<WebElement> elList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

		return elList;
	}

	public void TBD(int TimeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public String waitForExactTitle(int TimeOut, String expectValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));

		try {
			wait.until(ExpectedConditions.titleIs(expectValue));
		} catch (Exception e) {
			System.out.println("Expected Title is not matching");
			e.printStackTrace();
		}

		return driver.getTitle();
	}

	public String waitForExactURL(int TimeOut, String expectedValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));

		try {
			wait.until(ExpectedConditions.urlToBe(expectedValue));
		} catch (Exception e) {
			System.out.println("Exepected URL is not matching");
			e.printStackTrace();
		}

		return driver.getCurrentUrl();
	}

	public String waitForUrlContains(int TimeOut, String expectValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));

		try {
			wait.until(ExpectedConditions.urlContains(expectValue));
		} catch (Exception e) {
			System.out.println("Expected URL fraction is not matching");
			e.printStackTrace();
		}

		return driver.getCurrentUrl();
	}

	public String waitForTitleContains(int TimeOut, String expectedValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));

		try {
			wait.until(ExpectedConditions.titleContains(expectedValue));
		} catch (Exception e) {
			System.out.println("Expected Title fraction is not matching");
			e.printStackTrace();
		}

		return driver.getTitle();
	}

	public String waitForStringContains(int TimeOut, By locator, String expectValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));

		try {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectValue));
		} catch (Exception e) {
			System.out.println("Text linked of the WebElement is not contains expected value");
			e.printStackTrace();
		}

		return getElement(locator).getText();
	}

	public String waitForExactString(int TimeOut, By locator, String expectedValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOut));

		try {
			wait.until(ExpectedConditions.textToBe(locator, expectedValue));
		} catch (Exception e) {
			System.out.println("Text linked of the WebElement is not matching");
			e.printStackTrace();
		}

		return getElement(locator).getText();
	}

	public Boolean isElementDisplayed(By locator) {
		try {
			getElement(locator).isDisplayed();
			return true;

		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page");
			e.printStackTrace();
			return false;
		}
	}

	public Boolean isElementsDisplayed(By locator) {
		List<WebElement> elements = getElements(locator);

		try {
			if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
				return true;
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page");
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public WebElement doScrollToElement(WebElement el) {
		action.scrollToElement(el).perform();
		return el;
	}

	public void DoActionsClick(By locator) {
		action.moveToElement(getElement(locator)).perform();
		action.click(getElement(locator)).perform();
	}

}
