package ua.univer.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.IFBPGateProd;
import org.tempuri.IFBPGateService;
import ua.univer.BIT.BIT_PKCS11CL3;
import ua.univer.BIT.CertGenerator;
import ua.univer.BIT.KeyStore;
import ua.univer.BIT.cDevice;
import ua.univer.exeptions.MyException;
import ua.univer.fbpgateclient.DocumentElement;
import ua.univer.fbpgateclient.ExchData;
import ua.univer.fbpgateclient.LoginData;
import ua.univer.util.ConverterUtil;

import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static ua.univer.util.FileUtil.writeStringToFile;

@RestController
@RequestMapping(value = "/api")
public class LoginController extends BaseController{


    public LoginController(HttpClient httpClient, IFBPGateProd gateProd, CertGenerator genRSA, cDevice dev, KeyStore keyStore) {
        super(httpClient, gateProd, genRSA, dev, keyStore);
    }
/*


    @Scheduled(fixedRate = 365*24*60*60, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
    @Scheduled(cron="2 0 10 * * MON-FRI")
    @GetMapping(value = "/v1/login", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> login() {

        logger.info("Method Login. TEST.");

        String strLoginData = loginXML(cDevice.armID, Base64.getEncoder().encodeToString(dev.getCertificate().getEncoded()),
                "1", "1", Base64.getEncoder().encodeToString(CertGenerator.RSACert));

        // Подпись данных для входа
        byte[] signedLogin = tokenLib.SignData(dev.getCertificate(), dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

        String responseStr = gateTest.login(cDevice.armID, signedLogin);
        writeStringToFile(responseStr, "Response", ".xml");
        LoginData loginData = ConverterUtil.xmlToObject(responseStr, LoginData.class);
        if (loginData.login == null || loginData.login.isEmpty() || loginData.login.get(0).IsLoginOk == null || !loginData.login.get(0).IsLoginOk.equalsIgnoreCase("True")) {
            throw new MyException("Вход не выполнен !!");
        }
        // Генерируем ключ симметричного шифрования ДСТУ
        KeyStore.sessionKey = genRSA.GenerateSessionKeyB(Base64.getDecoder().decode(loginData.login.get(0).Base64Token));

        return ResponseEntity.ok().body(responseStr);
    }


    @GetMapping(value = "/v1/getPortfolio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPortfolio() {

        logger.info("Method GetPortfolio. TEST.");
        byte[] response = gateTest.getCryptXML(cDevice.armID, ExchData.Portfolio, false);
        byte[] decryptedResponse = BIT_PKCS11CL3.Decrypt(response, KeyStore.sessionKey, err);
        if(decryptedResponse == null) throw new MyException("Response is null");
        String responseStr = new String(decryptedResponse, StandardCharsets.UTF_8);
        writeStringToFile(responseStr, "Response", ".xml");
        DocumentElement de = ConverterUtil.xmlToObject(responseStr, DocumentElement.class);

        return ResponseEntity.ok().body(ConverterUtil.objectToJson(de));
    }
*/


    @Scheduled(fixedRate = 365*24*60*60, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
    @Scheduled(cron="0 0 10 * * MON-FRI")
    @GetMapping(value = "/prod/login", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> loginProd() {

        logger.info("Method Login");

        String strLoginData = loginXML(cDevice.armID, Base64.getEncoder().encodeToString(dev.getCertificate().getEncoded()),
                KeyStore.login, KeyStore.password, Base64.getEncoder().encodeToString(CertGenerator.RSACert));

        byte[] signedLogin = tokenLib.SignData(dev.getCertificate(), dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

        String responseStr = gateProd.login(cDevice.armID, signedLogin);
        writeStringToFile(responseStr, "Response", ".xml");
        LoginData loginData = ConverterUtil.xmlToObject(responseStr, LoginData.class);
        if (loginData.login == null || loginData.login.isEmpty() || loginData.login.get(0).IsLoginOk == null || !loginData.login.get(0).IsLoginOk.equalsIgnoreCase("True")) {
            throw new MyException("Вход не выполнен !!");
        }
        KeyStore.sessionKeyProd = genRSA.GenerateSessionKeyB(Base64.getDecoder().decode(loginData.login.get(0).Base64Token));

        return ResponseEntity.ok().body(responseStr);
    }


    @GetMapping(value = "/prod/getPortfolio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPortfolioProd() {

        logger.info("Method GetPortfolio");
        byte[] response = gateProd.getCryptXML(cDevice.armID, ExchData.Portfolio, false);
        byte[] decryptedResponse = BIT_PKCS11CL3.Decrypt(response, KeyStore.sessionKeyProd, err);
        if(decryptedResponse == null) throw new MyException("Response is null");
        String responseStr = new String(decryptedResponse, StandardCharsets.UTF_8);
        writeStringToFile(responseStr, "Response", ".xml");
        DocumentElement de = ConverterUtil.xmlToObject(responseStr, DocumentElement.class);

        return ResponseEntity.ok().body(ConverterUtil.objectToJson(de));
    }


    @GetMapping(value = "/prod/authorizationCheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authorizationCheck() {

        logger.info("Method AuthorizationCheck.");

        String abc = "Проверка авторизации и шифрования";
        byte[] signedXml = tokenLib.SignData(dev.getCertificate(), dev.UsbSlot, pin, abc.getBytes(), true, avPath, err);
        byte[] crypt = BIT_PKCS11CL3.Encrypt(signedXml, KeyStore.sessionKeyProd, err);
        Integer response = gateProd.sendXML(cDevice.armID, crypt, ExchData.AuthorizationCheck);

        String result;
        switch (response){
            case 1:
                result = "Авторизация выполнена " + LocalDateTime.now().toLocalTime();
                break;
            case 0:
                result = "Ошибки с шифрованием или подписью";
                break;
            case -1:
                result = "Служба отстутствует в списке подключений";
                break;
            default:
                result = "Неизвестная ошибка";
                break;
        }

        return ResponseEntity.ok().body(result);
    }


    @GetMapping(value = "/prod/getLastError", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLastError() {

        logger.info("getLastError.");
        String result = gateProd.getLastError(cDevice.armID);

        return ResponseEntity.ok().body(result);
    }



}
