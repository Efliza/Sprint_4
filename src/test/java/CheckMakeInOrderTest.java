import org.example.MainPage;
import org.example.OrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CheckMakeInOrderTest {

    protected WebDriver driver;
    protected MainPage mainPage;
    protected OrderPage orderPage;
    private final String name;
    private final String lastname;
    private final String address;
    private final String station;
    private final String number;
    private final String date;
    private final String rentPeriod;
    private final String color;
    private final String comment;

    public CheckMakeInOrderTest(String name, String lastname, String address, String station, String number, String date, String rentPeriod, String color, String comment) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.station = station;
        this.number = number;
        this.date = date;
        this.rentPeriod = rentPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Кирилл", "Смирнов", "Москва, ул.Комсомольская, д.5, кв.14", "Сокольники", "+77894561236", "12.11.23", "двое суток", "black", "комментарий"},
                {"Семен", "Андреев", "Москва, ул.Центральная, д.3, кв.23", "ВДНХ", "+72361547895", "14.11.23", "сутки", "grey", "нет комментария"},
        });
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.open();
    }

    @Test
    public void orderTest1() {
        mainPage.clickCookieButton();
        mainPage.clickOrderUp();
        orderPage.fillOutFirstForm(name, lastname, address, station, number);
        orderPage.clickNextStep();
        orderPage.fillOutSecondPart(date, rentPeriod, color, comment);
        orderPage.clickOrderButton();
        orderPage.clickConfirmButton();
    }

    @Test
    public void orderTest2() {
        mainPage.clickCookieButton();
        mainPage.clickOrderDown();
        orderPage.fillOutFirstForm(name, lastname, address, station, number);
        orderPage.clickNextStep();
        orderPage.fillOutSecondPart(date, rentPeriod, color, comment);
        orderPage.clickOrderButton();
        orderPage.clickConfirmButton();
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
