package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    public static final String URL = "https://qa-scooter.praktikum-services.ru";
    private final WebDriver driver;

    //локатор кнопки "Куки"
    private final By cookieButton = By.xpath(".//button[@class = 'App_CookieButton__3cvqF']");

    //локатор до блока "Вопросы о важном"
    private final By blockQuestions = By.xpath(".//div[@class = 'Home_SubHeader__zwi_E' and text() = 'Вопросы о важном']");

    private final By orderUp = By.xpath(".//button[@class='Button_Button__ra12g']");
    //Кнопка заказать в центре
    private final By orderDown = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //форма заказа
    private final By formOrder = By.xpath(".//div[@class='Order_Header__BZXOb']");
    //private final OrderPage orderPage;

    //конструктор класса
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //метод открывает сайт
    public MainPage open() {
        driver.get(URL);
        return this;
    }
    //метод, который кликает по кнопке Куки
    public void clickCookieButton(){
        driver.findElement(cookieButton).click();
    }

    //метод, который скроллит до блока "Вопросы о важном"
    public void scrollToBlockQuestions(){
        WebElement element = driver.findElement(blockQuestions);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    //найти и кликнуть на вопрос, получить ответ
    public String findQuestionAndGetAnswer(String questionText) {
        //Найти вопрос
        By question = By.xpath(".//div[@class='accordion__button' and text()='" + questionText + "']");
        WebElement questionElement = driver.findElement(question);
        //кликнуть на вопрос
        questionElement.click();
        //получить id вопроса
        String attribute = driver.findElement(question).getAttribute("id");
        //преобразовать для использования
        int idQuestion = attribute.lastIndexOf("-");
        String idNumStr = attribute.substring(idQuestion);
        String attributAnswer = "accordion__panel" + idNumStr;
        //составить xpath для ответа
        String answerXPath = ".//div[@id='" + attributAnswer + "']/p";
        By answer = By.xpath(answerXPath);
        //найти ответ и получить его текст
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement answerElement = wait.until(ExpectedConditions.presenceOfElementLocated(answer));
        System.out.println(answerElement.getText());
        return answerElement.getText();
    }

    //Кликнуть первую кнопку заказать
    public void clickOrderUp() {
        driver.findElement(orderUp).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(formOrder));
    }

    //кликнуть вторую кнопку заказать
    public void clickOrderDown() {
        WebElement orderDownElement = driver.findElement(orderDown);
        // Скролл до элемента
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderDownElement);
        orderDownElement.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(formOrder));
    }
}
