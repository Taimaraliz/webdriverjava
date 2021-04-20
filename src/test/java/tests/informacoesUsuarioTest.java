 package tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")


public class informacoesUsuarioTest {
    private WebDriver navegador;
    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){
        navegador = Web.createChrome();

        //CLICAR NO LINK QUE QUE POSSUI O TEXTO "SIGN IN"
        navegador.findElement(By.linkText("Sing In")).click();

        //IDENTIFICAR O FORMULARIO DE "LOGIN"
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //DIGITAR NO CAMPO NOME LOGIN QUE ESTÁ DENTRO DO FORMULARIO DE ID "SIGNINBOX" O TEXTO "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        //CLICAR NO CAMPO COM NOME "PASSWORD" QUE ESTÁ DENTRO DO FORMULÁRIO DE ID "SIGNINBOX" O TEXTO "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        //CLICAR NO LINK COM O TEXTO "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        // CLICAR EM UM LINK QUE POSSUI A CLASS "ME"
        navegador.findElement(By.className("me")).click();

        // CLICAR EM UM LINK QUE POSSUI O TEXTO "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo, @Param(name="contato")String cotato, @Param(name="mensagem") String mensagemEsperada){

        // CLICAR NO BOTÃO ATRAVÉS DO SEU XPATH //BUTTON[@DATA-TARGET="ADDMOREDATA"]
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        // IDENTIFICAR A POPUP ONDE ESTÁ O FORMULARIO DE ID ADDMOREDATA
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        // NO CAMPO DE NAME "TYPE" ESCOLHER A OPÇÃO "PHONE"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText("tipo");

        // NO CAMPO DE NAME "CONTACT" DIGITAR "+5511999999999"
        popupAddMoreData.findElement(By.name("contact")).sendKeys("contato");

        // CLICAR NO LINK DE TEXT "SAVE" QUE ESTÁ NA POPUP
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        // NA MENSAGEM DE ID "TOAST-CONTAINER" VALIDAR QUE O TEXTO É "YOUR CONTACT HAS BEEN ADDED!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("mensagemEsperada", mensagem);

        //VALIDAR DENTRO DO ELEMENTO CLASS "ME" ESTÁ O TEXTO "HI, JUNIOR"
        WebElement me = navegador.findElement(By.className("me"));
        String textoNomeElementoMe = me.getText();
        assertEquals("HI JUNIOR", textoNomeElementoMe);

    }
    @Test
    public void removerUmContatoDeUmUsuario(){
        // CLICAR NO ELEMENTO PELO SEU XPATH // SPAN [TEXT()="+551133334444"] FOLLOWING-SIBLING::A
        navegador.findElement(By.xpath("//span[text()=\"+551133334444\"]/following-sibling::a")).click();

        // CONFIRMAR A JANELA JAVASCRIPT
        navegador.switchTo().alert().accept();

        // VALIDAR QUE A MENSAGEM APRESENTADA FOI REST IN PEACE, DEAR PHONE!
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);
        String screenshotArquivo = ("/Usuario/taimara/aula-java/taskit/" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png");
        Screenshot.tirar(navegador, "screenshotArquivo");

        // AGUARDAR ATÉ 10 SEGUNDOS PARA QUE A JANELA DESAPAREÇA
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        // CLICAR NO LINK COM TEXTO "LOGOUT"
        navegador.findElement(By.linkText("logout")).click();

    }
    @After
    public void tearDown(){

        //FECHAR O NAVEGADOR
        //navegador.quit();
    }
}
