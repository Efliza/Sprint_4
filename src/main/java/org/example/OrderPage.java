package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OrderPage {
    private final WebDriver driver;
    //поле Имя
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    //поле Фамилия
    private final By lastnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    //поле Адрес
    private final By adressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    //поле Метро
    private final By metroChoose = By.xpath("//input[@placeholder='* Станция метро']");
    //поле телефон
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    //кнопка далее
    private final By nextStep = By.xpath("//button[text()='Далее']");
    //вторая часть формы
    private final By secondPartForm = By.className("Order_Content__bmtHS");
    //дата доставки
    private final By dateDelivery = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    //Кнопка для списка со сроком аренды
    private final By buttonRentPeriod = By.xpath(".//span[@class='Dropdown-arrow']");
    //поле комменатрий курьеру
    private final By leftComment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    //кнопка заказать
    private final By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //подтвердить заказ
    private final By orderConfirmButton = By.xpath(".//button[text()='Да']");
    //поп-ап подтверждения заказа
    private final By confirmedOrder = By.xpath(".//div[text()='Заказ оформлен']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillOutFirstForm(String name, String lastname, String address, String station, String number) {
        //заполняем поля формы
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(lastnameInput).sendKeys(lastname);
        driver.findElement(adressInput).sendKeys(address);
        driver.findElement(phoneInput).sendKeys(number);
        //выбираем метро и ждем появления списка
        driver.findElement(metroChoose).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement stationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Order_Text__2broi' and text()='" + station + "']")));
        // Скроллим до нужной станции и выбираем ее
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", stationElement);
        stationElement.click();

    }

    public void clickNextStep() {
        // Нажимаем кнопку "Далее"
        driver.findElement(nextStep).click();
        // Ждем появления следующего элемента
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(secondPartForm));
        assertTrue("Переход на вторую часть форму не осуществлен", driver.findElement(secondPartForm).isDisplayed());
    }

    //сначала хотела разбить заполнение на несколько блоков, но посчитала, что это лишнее. С другой стороны в допах было написать тест для ошибок при заполнении формы и тут детализация бы помогла
    public void fillOutSecondPart(String date, String rentPeriod, String color, String comment) {
        //заполнили дату - рукописный ввод подходит
        driver.findElement(dateDelivery).sendKeys(date);
        //кликаем на период аренды
        driver.findElement(buttonRentPeriod).click();
        //ищем и скролим до нужного периода
        By rentPeriodOption = By.xpath(".//div[@class='Dropdown-option' and text()='" + rentPeriod + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement rentPeriodElement = wait.until(ExpectedConditions.visibilityOfElementLocated(rentPeriodOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", rentPeriodElement);
        //кликаем на нужный период
        rentPeriodElement.click();
        //Выбираем чек-бокс
        By colorCheckbox = By.xpath("//input[@type='checkbox' and @id='" + color + "']");
        driver.findElement(colorCheckbox).click();
        //добавляем комментарий
        driver.findElement(leftComment).sendKeys(comment);

    }

    public void clickOrderButton() {
        //нажимаем Заказать
        driver.findElement(orderButton).click();
        //Ожидаем окно с подтверждением заказа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmButton));
    }

    public void clickConfirmButton() {
        //нажимаем на кнопку подтверждения заказа
        driver.findElement(orderConfirmButton).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmedOrder));
        } catch (TimeoutException e) {
            // Если элемент не появился, выводим сообщение об ошибке
            fail("Сообщение о подтверждении заказа не появилось");
        }
    }
}
