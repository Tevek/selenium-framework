package hu.virgo.selenium.framework.page.verify;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementVerifier implements PageVerifier {

	private final By element;
	private int waitTimoutInSeconds = 30;

	public ElementVerifier(By element) {
		this.element = element;
	}

	public ElementVerifier(By element, int waitTimoutInSeconds) {
		this.element = element;
		this.waitTimoutInSeconds = waitTimoutInSeconds;
	}

	@Override
	public void verify(WebDriver driver) throws PageVerificationException {
		WebDriverWait wait = new WebDriverWait(driver, waitTimoutInSeconds);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			throw new PageVerificationException("Element could not be found:  " + element.getClass().getName() + " " + element.toString());
		}
	}
}
