package ua.univer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tempuri.IFBPGateService;
import ua.univer.BIT.*;
import ua.univer.fbpgateclient.*;
import ua.univer.util.ConverterUtil;

import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static ua.univer.util.FileUtil.writeStringToFile;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected final HttpClient httpClient;
    protected final IFBPGateService gate;
    protected final CertGenerator genRSA;
    protected final cDevice dev;
    protected final KeyStore keyStore;

    protected final BIT_PKCS11CL3 tokenLib = new BIT_PKCS11CL3();
    protected Holder<String> err = new Holder<>("");
    protected String avPath = BIT_PKCS11CL3.Av337PathProg;
    protected String pin = "12345678";


    public BaseController(HttpClient httpClient, IFBPGateService gate, CertGenerator genRSA, cDevice dev, KeyStore keyStore) {
        this.httpClient = httpClient;
        this.gate = gate;
        this.genRSA = genRSA;
        this.dev = dev;
        this.keyStore = keyStore;
    }



    protected String loginBase() {

        logger.info("Method Login from Base Class");

        String strLoginData = loginXML(cDevice.armID, Base64.getEncoder().encodeToString(dev.getCertificate().getEncoded()),
                KeyStore.login, KeyStore.password, Base64.getEncoder().encodeToString(CertGenerator.RSACert));
        byte[] signedLogin = tokenLib.SignData(dev.getCertificate(), dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

        String responseStr = gate.login(cDevice.armID, signedLogin);
        writeStringToFile(responseStr, "Response", ".xml");
        LoginData loginData = ConverterUtil.xmlToObject(responseStr, LoginData.class);
        if (loginData.login == null || loginData.login.isEmpty() || loginData.login.get(0).IsLoginOk == null || !loginData.login.get(0).IsLoginOk.equalsIgnoreCase("True")) {
            logger.error("CAN NOT CONNECT TO FBP");
            return "Вхід на ФБ Перспектива не виконано !!";
        }
        KeyStore.sessionKeyProd = genRSA.GenerateSessionKeyB(Base64.getDecoder().decode(loginData.login.get(0).Base64Token));

        return "Вхід до Системи виконано. Спробуйте ще раз.";
    }



    protected String loginXML(String armID, String cert, String login, String password, String rsaCert){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<LoginData>" +
                "<LoginMsg>" +
                "<BrokSystem>Test</BrokSystem>" +
                "<ArmID>" + armID + "</ArmID>" +
                "<Base64Cert>" + cert + "</Base64Cert>" +
                "<Login>" + login + "</Login>" +
                "<Pwd>" + password + "</Pwd>" +
                "<TokenMediaType>129</TokenMediaType>" +
                "<RSAEncCert>" + rsaCert + "</RSAEncCert>" +
                "</LoginMsg>" +
                "</LoginData>";
    }




    @GetMapping(value = "/v1/crypt", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public ResponseEntity<String> crypt(){

        String example = "example";
        Holder<String> err = new Holder<>("");

        byte[] encryptedMessage = BIT_PKCS11CL3.Encrypt(example.getBytes(StandardCharsets.UTF_8), "s".getBytes(), err);
        byte[] decryptedMessage = BIT_PKCS11CL3.Decrypt(encryptedMessage, "s".getBytes(), err);

        if (decryptedMessage == null){
            return ResponseEntity.ok().body("там пусто");
        }

        return ResponseEntity.ok().body(new String(decryptedMessage));
    }





}
