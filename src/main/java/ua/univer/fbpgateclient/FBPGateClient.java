/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.univer.fbpgateclient;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.tempuri.FBPGateService;
import org.tempuri.IFBPGateService;
import ua.univer.BIT.CertGenerator;
import ua.univer.BIT.Holder;
import ua.univer.BIT.cDevice;
import ua.univer.BIT.BIT_PKCS11CL3;
import ua.avtor.DsLib.Certificate;
//import WCFClient.*;

/**
 *
 * @author v_shevchenko
 */
public class FBPGateClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //RSA();
        //DSTU();
        RSA_GOST();
    }

    public static void RSA_GOST(){
        try
        {
            // Библиотека подпись/шифрование/дешифрование/сессионный ключ
            BIT_PKCS11CL3 tokenLib = new BIT_PKCS11CL3();
            String strError = "";
            Holder<String> err = new Holder<String>(strError);

            // Аппаратные ключи - BIT_PKCS11CL3.Av337PathChip
            // Программные ключи
            String avPath = BIT_PKCS11CL3.Av337PathProg;
            ArrayList<cDevice> devices = tokenLib.GetDeviceList(true, avPath, err);

            // Первое устройство
            cDevice dev = devices.get(0);

            // Список сертификатов устройства
            ArrayList<Certificate> certificates = tokenLib.GetCertificateList(dev.UsbSlot, avPath, err);

            // Последний сертификат из списка
            Certificate cer = certificates.get(certificates.size()-1);

            String pin = "12345678";

            // Проверка пина
            if (!tokenLib.CheckPin(dev.UsbSlot, avPath, pin)) return;


            CertGenerator genRSA = new CertGenerator();
            genRSA.GenerateRSA();


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
                    "<TokenMediaType>129</TokenMediaType>" + // Тип AES_GOST
                    "<RSAEncCert>" + Base64.getEncoder().encodeToString(genRSA.RSACert) + "</RSAEncCert>" +
                    "</LoginMsg>" +
                    "</LoginData>";

            // Клиент WCF сервиса
            // http://77.88.202.130:4000/FBPGate.IFBPGateService/?wsdl
            // Перед добавлением в новом проекте обязательно добавить библиотеку METRO 2.0
            // Проверить прокси, оно куда то записало по умолчанию
            FBPGateService srv = new FBPGateService();
            IFBPGateService gate = srv.getWSHttpBindingFBPGate();

            // Подпись данных для входа
            byte[] signedLogin = tokenLib.SignData(cer, dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

            // Вызов метода login через WCF клиент
            String xmlResponse = gate.login(armID, signedLogin);

            // Парсим ответ xml
            JAXBContext jaxbContextLogin = JAXBContext.newInstance(LoginData.class);
            Unmarshaller jaxbUnmarshallerLogin = jaxbContextLogin.createUnmarshaller();
            LoginData login = (LoginData) jaxbUnmarshallerLogin.unmarshal(new StringReader(xmlResponse));

            if (login.login == null || login.login.isEmpty() || login.login.get(0).IsLoginOk == null || !login.login.get(0).IsLoginOk.equalsIgnoreCase("True"))
            {
                return;
            }

            // Генерируем ключ симметричного шифрования ДСТУ
            byte[] sessionKey = genRSA.GenerateSessionKeyB(Base64.getDecoder().decode(login.login.get(0).Base64Token));

            // Просто проверка работы шифрования/дешифрования симметричным ключом
            byte[] encryptedMessage = tokenLib.Encrypt(new byte[]{0,1,2,3,4,5}, sessionKey, err);
            byte[] dencryptedMessage = tokenLib.Decrypt(encryptedMessage, sessionKey, err);

            // ПОРТФЕЛЬ ЦБ
            byte[] bportfolio = gate.getCryptXML(armID, ExchData.Portfolio, false);
            byte[] decryptedPortfolio = tokenLib.Decrypt(bportfolio, sessionKey, err);
            String portfolioXML = new String(decryptedPortfolio, StandardCharsets.UTF_8);
            JAXBContext jaxbContext = JAXBContext.newInstance(DocumentElement.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            // Убираем мусор вначале xml
            portfolioXML = portfolioXML.trim().replaceFirst("^([\\W]+)<","<");
            DocumentElement portfolio = (DocumentElement) jaxbUnmarshaller.unmarshal(new StringReader(portfolioXML));
            // ПОРТФЕЛЬ ЦБ

            if (!portfolio.papers.isEmpty())
            {
                Paper p = portfolio.papers.get(0);

                // Разблокировка ЦБ
                UnblockOrder uo = new UnblockOrder();
                uo.Account = p.Account;
                uo.ArmID = armID;
                uo.ISIN = p.ISIN;
                uo.OrderType = p.SecurityType;
                uo.Quantity = 1;
                uo.OrderContext="test";
                DocumentElement de = new DocumentElement();
                de.unblockOrders.add(uo);

                JAXBContext jaxbContextUO = JAXBContext.newInstance(DocumentElement.class);
                Marshaller jaxbMarshallerUO = jaxbContextUO.createMarshaller();
                StringWriter swUO = new StringWriter();
                jaxbMarshallerUO.marshal(de, swUO);

                String xmlUO = swUO.toString();
                byte[] signedUO = tokenLib.SignData(cer, dev.UsbSlot, pin, xmlUO.getBytes(), true, avPath, err);
                byte[] cryptUO = tokenLib.Encrypt(signedUO, sessionKey, err);
                int res = gate.sendXML(armID, cryptUO, ExchData.NewUnblockOrder);
                // Разблокировка ЦБ

                String error = gate.getLastError(armID);
                Object o = error;
            }

            String LogoutData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<LoginData>" +
                    "<LoginMsg>" +
                    "<BrokSystem>Test</BrokSystem>" +
                    "<ArmID>" + armID + "</ArmID>" +
                    "</LoginMsg>" +
                    "</LoginData>";

            byte[] signedLogout = tokenLib.SignData(cer, dev.UsbSlot, pin, LogoutData.getBytes(), true, avPath, err);

            gate.logout(armID, signedLogout);
        }
        catch(Exception ex)
        {
            String ee = ex.getMessage();
        }
    }

    public static void RSA(){
        try
        {
            // Библиотека подпись/шифрование/дешифрование/сессионный ключ
            BIT_PKCS11CL3 tokenLib = new BIT_PKCS11CL3();
            String strError = "";
            Holder<String> err = new Holder<String>(strError);

            // Аппаратные ключи - BIT_PKCS11CL3.Av337PathChip
            // Программные ключи
            String avPath = BIT_PKCS11CL3.Av337PathProg;
            ArrayList<cDevice> devices = tokenLib.GetDeviceList(true, avPath, err);

            // Первое устройство
            cDevice dev = devices.get(0);

            // Список сертификатов устройства
            ArrayList<Certificate> certificates = tokenLib.GetCertificateList(dev.UsbSlot, avPath, err);

            // Первый сертификат из списка
            Certificate cer = certificates.get(1);

            String pin = "12345678";

            // Проверка пина
            if (!tokenLib.CheckPin(dev.UsbSlot, avPath, pin)) return;


            CertGenerator genRSA = new CertGenerator();
            genRSA.GenerateRSA();


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
                    "<RSAEncCert>" + Base64.getEncoder().encodeToString(genRSA.RSACert) + "</RSAEncCert>" +
                    "</LoginMsg>" +
                    "</LoginData>";

            // Клиент WCF сервиса
            // http://77.88.202.130:4000/FBPGate.IFBPGateService/?wsdl
            // Перед добавлением в новом проекте обязательно добавить библиотеку METRO 2.0
            // Проверить прокси, оно куда то записало по умолчанию
            FBPGateService srv = new FBPGateService();
            IFBPGateService gate = srv.getWSHttpBindingFBPGate();

            // Подпись данных для входа
            byte[] signedLogin = tokenLib.SignData(cer, dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

            // Вызов метода login через WCF клиент
            String xmlResponse = gate.login(armID, signedLogin);

            // Парсим ответ xml
            JAXBContext jaxbContextLogin = JAXBContext.newInstance(LoginData.class);
            Unmarshaller jaxbUnmarshallerLogin = jaxbContextLogin.createUnmarshaller();
            LoginData login = (LoginData) jaxbUnmarshallerLogin.unmarshal(new StringReader(xmlResponse));

            if (login.login == null || login.login.isEmpty() || login.login.get(0).IsLoginOk == null || !login.login.get(0).IsLoginOk.equalsIgnoreCase("True"))
            {
                return;
            }

            // Генерируем ключ симметричного шифрования ДСТУ
            byte[] sessionKey = genRSA.GenerateSessionKeyB(Base64.getDecoder().decode(login.login.get(0).Base64Token));

            byte[] keyAES = new byte[16];
            for (int i=0; i<16; i++)
            {
                keyAES[i] = sessionKey[i];
            }

            byte[] ivAES = new byte[16];
            for (int i=0; i<16; i++)
            {
                ivAES[i] = sessionKey[i+16];
            }

            // Просто проверка работы шифрования/дешифрования симметричным ключом
            byte[] encryptedMessage = genRSA.EncryptAES(new byte[]{0,1,2,3,4,5}, keyAES, ivAES);
            byte[] dencryptedMessage = genRSA.DecryptAES(encryptedMessage, keyAES, ivAES);

            // ПОРТФЕЛЬ ЦБ
            byte[] bportfolio = gate.getCryptXML(armID, ExchData.Portfolio, false);
            byte[] decryptedPortfolio = genRSA.DecryptAES(bportfolio, keyAES, ivAES);
            String portfolioXML = new String(decryptedPortfolio, StandardCharsets.UTF_8);
            JAXBContext jaxbContext = JAXBContext.newInstance(DocumentElement.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            // Убираем мусор вначале xml
            portfolioXML = portfolioXML.trim().replaceFirst("^([\\W]+)<","<");
            DocumentElement portfolio = (DocumentElement) jaxbUnmarshaller.unmarshal(new StringReader(portfolioXML));
            // ПОРТФЕЛЬ ЦБ

            if (!portfolio.papers.isEmpty())
            {
                Paper p = portfolio.papers.get(0);

                // Разблокировка ЦБ
                UnblockOrder uo = new UnblockOrder();
                uo.Account = p.Account;
                uo.ArmID = armID;
                uo.ISIN = p.ISIN;
                uo.OrderType = p.SecurityType;
                uo.Quantity = 1;
                uo.OrderContext="test";
                DocumentElement de = new DocumentElement();
                de.unblockOrders.add(uo);

                JAXBContext jaxbContextUO = JAXBContext.newInstance(DocumentElement.class);
                Marshaller jaxbMarshallerUO = jaxbContextUO.createMarshaller();
                StringWriter swUO = new StringWriter();
                jaxbMarshallerUO.marshal(de, swUO);

                String xmlUO = swUO.toString();
                byte[] signedUO = tokenLib.SignData(cer, dev.UsbSlot, pin, xmlUO.getBytes(), true, avPath, err);
                byte[] cryptUO = genRSA.EncryptAES(signedUO, keyAES, ivAES);
                int res = gate.sendXML(armID, cryptUO, ExchData.NewUnblockOrder);
                // Разблокировка ЦБ

                String error = gate.getLastError(armID);
                Object o = error;
            }

            String LogoutData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<LoginData>" +
                    "<LoginMsg>" +
                    "<BrokSystem>Test</BrokSystem>" +
                    "<ArmID>" + armID + "</ArmID>" +
                    "</LoginMsg>" +
                    "</LoginData>";

            byte[] signedLogout = tokenLib.SignData(cer, dev.UsbSlot, pin, LogoutData.getBytes(), true, avPath, err);

            gate.logout(armID, signedLogout);
        }
        catch(Exception ex)
        {
            String ee = ex.getMessage();
        }
    }

    public static void DSTU(){
        try
        {
            // Библиотека подпись/шифрование/дешифрование/сессионный ключ
            BIT_PKCS11CL3 tokenLib = new BIT_PKCS11CL3();
            String strError = "";
            Holder<String> err = new Holder<String>(strError);

            // Аппаратные ключи - BIT_PKCS11CL3.Av337PathChip
            // Программные ключи
            String avPath = BIT_PKCS11CL3.Av337PathProg;
            ArrayList<cDevice> devices = tokenLib.GetDeviceList(true, avPath, err);

            // Первое устройство
            cDevice dev = devices.get(0);

            // Список сертификатов устройства
            ArrayList<Certificate> certificates = tokenLib.GetCertificateList(dev.UsbSlot, avPath, err);

            // Первый сертификат из списка
            Certificate cer = certificates.get(0);

            String pin = "12345678";

            // Проверка пина
            if (!tokenLib.CheckPin(dev.UsbSlot, avPath, pin)) return;

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
            // Проверить прокси, оно куда то записало по умолчанию
            FBPGateService srv = new FBPGateService();
            IFBPGateService gate = srv.getWSHttpBindingFBPGate();

            // Подпись данных для входа
            byte[] signedLogin = tokenLib.SignData(cer, dev.UsbSlot, pin, strLoginData.getBytes(), true, avPath, err);

            // Вызов метода login через WCF клиент
            String xmlResponse = gate.login(armID, signedLogin);

            // Парсим ответ xml
            JAXBContext jaxbContextLogin = JAXBContext.newInstance(LoginData.class);
            Unmarshaller jaxbUnmarshallerLogin = jaxbContextLogin.createUnmarshaller();
            LoginData login = (LoginData) jaxbUnmarshallerLogin.unmarshal(new StringReader(xmlResponse));

            if (login.login == null || login.login.isEmpty() || login.login.get(0).IsLoginOk == null || !login.login.get(0).IsLoginOk.equalsIgnoreCase("True"))
            {
                return;
            }

            // Генерируем ключ симметричного шифрования ДСТУ
            byte[] sessionKey = tokenLib.GenerateSessionKeyB(cer, dev.UsbSlot, pin, Base64.getDecoder().decode(login.login.get(0).Base64Token), avPath, err);

            // Просто проверка работы шифрования/дешифрования симметричным ключом
            byte[] encryptedMessage = tokenLib.Encrypt(new byte[]{0,1,2,3,4,5}, sessionKey, err);
            byte[] dencryptedMessage = tokenLib.Decrypt(encryptedMessage, sessionKey, err);

            // ПОРТФЕЛЬ ЦБ
            byte[] bportfolio = gate.getCryptXML(armID, ExchData.Portfolio, false);
            byte[] decryptedPortfolio = tokenLib.Decrypt(bportfolio, sessionKey, err);
            String portfolioXML = new String(decryptedPortfolio, StandardCharsets.UTF_8);
            JAXBContext jaxbContext = JAXBContext.newInstance(DocumentElement.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            // Убираем мусор вначале xml
            portfolioXML = portfolioXML.trim().replaceFirst("^([\\W]+)<","<");
            DocumentElement portfolio = (DocumentElement) jaxbUnmarshaller.unmarshal(new StringReader(portfolioXML));
            // ПОРТФЕЛЬ ЦБ

            if (!portfolio.papers.isEmpty())
            {
                Paper p = portfolio.papers.get(0);

                // Разблокировка ЦБ
                UnblockOrder uo = new UnblockOrder();
                uo.Account = p.Account;
                uo.ArmID = armID;
                uo.ISIN = p.ISIN;
                uo.OrderType = p.SecurityType;
                uo.Quantity = 1;
                uo.OrderContext="test";
                DocumentElement de = new DocumentElement();
                de.unblockOrders.add(uo);

                JAXBContext jaxbContextUO = JAXBContext.newInstance(DocumentElement.class);
                Marshaller jaxbMarshallerUO = jaxbContextUO.createMarshaller();
                StringWriter swUO = new StringWriter();
                jaxbMarshallerUO.marshal(de, swUO);

                String xmlUO = swUO.toString();
                byte[] signedUO = tokenLib.SignData(cer, dev.UsbSlot, pin, xmlUO.getBytes(), true, avPath, err);
                byte[] cryptUO = tokenLib.Encrypt(signedUO, sessionKey, err);
                int res = gate.sendXML(armID, cryptUO, ExchData.NewUnblockOrder);
                // Разблокировка ЦБ

                String error = gate.getLastError(armID);
                Object o = error;
            }

            String LogoutData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<LoginData>" +
                    "<LoginMsg>" +
                    "<BrokSystem>Test</BrokSystem>" +
                    "<ArmID>" + armID + "</ArmID>" +
                    "</LoginMsg>" +
                    "</LoginData>";

            byte[] signedLogout = tokenLib.SignData(cer, dev.UsbSlot, pin, LogoutData.getBytes(), true, avPath, err);

            gate.logout(armID, signedLogout);
        }
        catch(Exception ex)
        {
            String ee = ex.getMessage();
        }
    }
}