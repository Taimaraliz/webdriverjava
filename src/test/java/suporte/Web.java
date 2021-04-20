package suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Web {
    public static WebDriver createChrome(){
        //ABRIR O NAVEGADOR:
        System.setProperty("webdriver.chrome.driver",  "C:\\Users\\Taimara\\drivers\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //NAVEGANDO PARA A PÁGINA TASKIT:
        navegador.get("http://www.juliodelima.com.br/taskit");

        return navegador;
    }
}
