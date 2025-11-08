package ua.univer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tempuri.FBPGateService;
import org.tempuri.IFBPGateProd;
import org.tempuri.IFBPGateService;
import ua.avtor.DsLib.Certificate;
import ua.avtor.DsLib.CertificateException;
import ua.univer.BIT.*;
import ua.univer.exeptions.MyException;
import ua.univer.fbpgateclient.*;
import ua.univer.util.ConverterUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

import static ua.univer.util.FileUtil.writeStringToFile;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected final HttpClient httpClient;
    //protected final IFBPGateService gateTest;
    protected final IFBPGateProd gateProd;
    protected final CertGenerator genRSA;
    protected final cDevice dev;
    protected final KeyStore keyStore;

    protected final BIT_PKCS11CL3 tokenLib = new BIT_PKCS11CL3();
    protected Holder<String> err = new Holder<>("");
    protected String avPath = BIT_PKCS11CL3.Av337PathProg;
    protected String pin = "12345678";


    public BaseController(HttpClient httpClient, IFBPGateProd gateProd, CertGenerator genRSA, cDevice dev, KeyStore keyStore) {
        this.httpClient = httpClient;
        //this.gateTest = gateTest;
        this.gateProd = gateProd;
        this.genRSA = genRSA;
        this.dev = dev;
        this.keyStore = keyStore;
    }



    protected String loginProdBase() {

        logger.info("Method Login from Base Class");

        String strLoginData = loginXML(cDevice.armID, Base64.getEncoder().encodeToString(dev.getCertificate().getEncoded()),
                KeyStore.login, KeyStore.password, Base64.getEncoder().encodeToString(CertGenerator.RSACert));
        byte[] signedLogin = tokenLib.SignData(dev.getCertificate(), dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

        String responseStr = gateProd.login(cDevice.armID, signedLogin);
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



/*


    @GetMapping(value = "/test/loginT", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> loginT() throws CertificateException, JAXBException, IllegalAccessException, InvocationTargetException {

        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump","true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump","true");

        // Библиотека подпись/шифрование/дешифрование/сессионный ключ
        BIT_PKCS11CL3 tokenLib = new BIT_PKCS11CL3();
        String strError = "";
        Holder<String> err = new Holder<>(strError);

        // Аппаратные ключи - BIT_PKCS11CL3.Av337PathChip
        // Программные ключи
        String avPath = BIT_PKCS11CL3.Av337PathProg;
        logger.info("Программные ключи " + avPath);

        ArrayList<cDevice> devices = tokenLib.GetDeviceList(true, avPath, err);

        // Первое устройство
        cDevice dev = new cDevice();
        if(devices.isEmpty()) {
            logger.info("Список устройств пуст");
        }
        else {
            dev = devices.get(0);
            logger.info("Имя устройства " + dev.DeviceName + " Тип устройства " + dev.DeviceType + " конец");
        }

        // Список сертификатов устройства
        ArrayList<Certificate> certificates = tokenLib.GetCertificateList(dev.UsbSlot, avPath, err);

        // Первый сертификат из списка
        Certificate cer = certificates.get(certificates.size() - 1);
        logger.info("Сертификат ");
        logger.info(new String(cer.getEncoded(), StandardCharsets.UTF_8));
        logger.info("getAuthorityKeyIdentifier " + new String(cer.getAuthorityKeyIdentifier(), StandardCharsets.UTF_8));
        logger.info("getSubjectName " + cer.getSubjectName("OU"));
        logger.info("getEMail " + cer.getEMail());
        logger.info("getSerial " + new String(cer.getSerial(), StandardCharsets.UTF_8));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dd1 = Instant.ofEpochMilli(cer.getNotBefore()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dd2 = Instant.ofEpochMilli(cer.getNotAfter()).atZone(ZoneId.systemDefault()).toLocalDate();
       // Date date = new Date(cer.getNotBefore());
        logger.info("NotBefore " + dd1.format(dateTimeFormatter));
       // Date date2 = new Date(cer.getNotAfter());
        logger.info("NotAfter " + dd2.format(dateTimeFormatter));
        logger.info("_____________________________________");


        for (Field field : cer.getClass().getDeclaredFields()) {
            field.setAccessible(true); // You might want to set modifier to public first.
            Object value = field.get(cer);
            if (value != null) {
                System.out.println(field.getName() + " = " + value);
            }
        }

        logger.info("_____________________________________");

        for (Method method : cer.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))
            ) {
                Object value = method.invoke(cer);
                if (value != null) {
                    System.out.println(method.getName() + " = " + value);
                }
            }
        }

        logger.info("_____________________________________");


        logger.info(pin);
        // Проверка пина
        if (!tokenLib.CheckPin(dev.UsbSlot, avPath, pin)) return ResponseEntity.badRequest().body("Ошибка проверки ПИНа");

        // Рабочее место из сертификата
        String armID = cer.getSubjectName("OU");

        // XML для входа
        String strLoginData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<LoginData>" +
                "<LoginMsg>" +
                "<BrokSystem>Test</BrokSystem>" +
                "<ArmID>" + armID + "</ArmID>" +
                "<Base64Cert>" + Base64.getEncoder().encodeToString(cer.getEncoded()) +"</Base64Cert>" +
                "<Login>1</Login>" + // Логин
                "<Pwd>1</Pwd>" + // Пароль
                "</LoginMsg>" +
                "</LoginData>";

        // Клиент WCF сервиса
        // http://77.88.202.130:4000/FBPGate.IFBPGateService/?wsdl
        // Перед добавлением в новом проекте обязательно добавить библиотеку METRO 2.0
        // Проверить прокси, оно куда-то записало по умолчанию
        FBPGateService srv = new FBPGateService();
        IFBPGateService gate = srv.getWSHttpBindingFBPGate();

        // Подпись данных для входа
        byte[] signedLogin = tokenLib.SignData(cer, dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

        String xmlResponse = gate.login(armID, signedLogin);
        System.out.println(xmlResponse);

        // Парсим ответ xml
        JAXBContext jaxbContextLogin = JAXBContext.newInstance(LoginData.class);
        Unmarshaller unmarshaller = jaxbContextLogin.createUnmarshaller();
        LoginData loginData = (LoginData) unmarshaller.unmarshal(new StringReader(xmlResponse));

        if (loginData.login == null || loginData.login.isEmpty() || loginData.login.get(0).IsLoginOk == null || !loginData.login.get(0).IsLoginOk.equalsIgnoreCase("True"))
        {
            logger.warn("Логина нет, или там пусто");
            return ResponseEntity.ok(xmlResponse);
        }

        return ResponseEntity.ok().body(xmlResponse);
    }

*/



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
