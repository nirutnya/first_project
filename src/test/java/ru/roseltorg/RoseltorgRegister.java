package ru.roseltorg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RoseltorgRegister {
  private WebDriver driver;

  public By registrationButton = By.xpath("(//button[text()='Регистрация'])[2]");//Кнопка регистрации
  public By postavshik = By.xpath("//img[contains(@alt,'Поставщик')]");//Выбрать "поставщик"
  public By yandexPoisk = By.id("text"); //Строка ввода яндекс
  public By vybor = By.xpath("//b[text()='Roseltorg.ru']");//Выбрать результат поиска Roseltorg.ru
  public By postavshikInostr = By.xpath("//a[contains(text(),'Единая аккредитация')]");// Поставщик Иностранный
  public By registrPolz = By.xpath("//button[text()='Продолжить']");//Ждем элемент со страницы регистрации пользователя
  public String expectedUrl = "https://etp.roseltorg.ru/authentication/register";


  @Before
  public void setUp() throws Exception {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testRoseltorgRegister() throws Exception {
    goToUrl("https://yandex.ru/");
    vvodText(yandexPoisk,"росэлторг"+Keys.ENTER);
    waitClickableClick(driver,vybor);
    switchHandle();
    waitClickableClick(driver, registrationButton, 30);
    waitClickableClick(driver, postavshik, 30);
    waitClickableClick(driver, postavshikInostr, 30);
    waitClickable(driver, registrPolz, 30);
//    waitClickableClick(driver, registrPolz);
    checkUrl();//Ссылки не совпадают, тест падает
    tearDown();
  }

  private void checkUrl() {
    String currentUrl = driver.getCurrentUrl();
    System.out.println("Текущая ссылка: " + currentUrl);
    System.out.println("Ожидаемая ссылка: " + expectedUrl);
    if ((expectedUrl).equals(currentUrl)) {
      System.out.println("Ссылки совпадают");
    } else {
      System.out.println("Ссылки не совпадают");
      Assert.assertTrue(currentUrl.equals(expectedUrl));
      //Assert.assertFalse(currentUrl.equals(expectedUrl));
    }
  }

  private void goToUrl(String url) {
    driver.get(url);
  }

  private void vvodText(By locator, String text) {
    driver.findElement(By.id("text")).clear();
    driver.findElement(By.id("text")).sendKeys("росэлторг"+Keys.ENTER);
  }

  public void switchHandle(){
    for(String tab:driver.getWindowHandles()) {
      driver.switchTo().window(tab);
    }
  }

  public void waitClickableClick(WebDriver driver, By locator, int time){
    WebDriverWait wait = new WebDriverWait(driver, time);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    element.click();
  }

  public void waitClickable(WebDriver driver, By locator, int time){
    WebDriverWait wait = new WebDriverWait(driver, time);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  public void waitClickableClick(WebDriver driver, By locator){
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    element.click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();

  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }


}
