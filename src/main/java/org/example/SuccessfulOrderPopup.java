package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessfulOrderPopup {
    private final WebDriver driver;
    private final By confirmedOrder = By.xpath(".//div[1] [contains(text(), 'Заказ оформлен')]");

    public SuccessfulOrderPopup (WebDriver driver) {
        this.driver = driver;
    }
    //Показ попапа успешно оформленного заказа
    public boolean checkSuccesfulOrderPopup() {
        return driver.findElements(confirmedOrder).size() > 0;
    }

}
