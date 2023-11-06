import org.example.MainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

@RunWith(Parameterized.class)
public class CheckOpensAccordionOneTest {

    private WebDriver driver;
    private final String questionText;
    private final String expectedAnswer;


    public CheckOpensAccordionOneTest(String questionText, String expectedAnswer){
        this.questionText = questionText;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
        };
    }

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test
    public void checkOpensAccordionQuestion1Test(){
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickCookieButton();
        mainPage.scrollToBlockQuestions();
        String answerText = mainPage.findQuestionAndGetAnswer(questionText);
        Assert.assertEquals("Текст не совпал!", expectedAnswer, answerText);
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
