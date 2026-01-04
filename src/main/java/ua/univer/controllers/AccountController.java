package ua.univer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.IFBPGateService;
import ua.univer.BIT.BIT_PKCS11CL3;
import ua.univer.BIT.KeyStore;
import ua.univer.BIT.cDevice;
import ua.univer.dto.FormNewClient;
import ua.univer.dto.UtilForm;
import ua.univer.exeptions.MyException;
import ua.univer.BIT.CertGenerator;
import ua.univer.exeptions.UnprocessableEntityException;
import ua.univer.fbpgateclient.DocumentElement;
import ua.univer.fbpgateclient.ExchData;
import ua.univer.fbpgateclient.NewClient;
import ua.univer.util.ConverterUtil;

import javax.validation.Valid;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

import static ua.univer.util.FileUtil.writeStringToFile;


@RestController
@RequestMapping(value = "/api/account")
public class AccountController extends BaseController{

    final static String HOST_NAME = "${host_name}";


    public AccountController(HttpClient httpClient, IFBPGateService gate, CertGenerator genRSA, cDevice dev, KeyStore keyStore) {
        super(httpClient, gate, genRSA, dev, keyStore);
    }



    @PostMapping(value = "/" + HOST_NAME + "/createClient")
    public ResponseEntity<String> createClient(@RequestBody @Valid FormNewClient form) {

        logger.info("Method NewClient.");
        NewClient newClient = UtilForm.convertFormToNewClient(form);
        DocumentElement document = new DocumentElement();
        document.getNewClients().add(newClient);

        String xmlString = ConverterUtil.objectToXML(document);
        writeStringToFile(xmlString, "NewClient", ".xml");

        byte[] signedXml = tokenLib.SignData(dev.getCertificate(), dev.UsbSlot, pin, xmlString.getBytes(StandardCharsets.UTF_8), true, avPath, err);
        byte[] crypt = BIT_PKCS11CL3.Encrypt(signedXml, KeyStore.sessionKeyProd, err);
        byte[] response = gate.sendXMLResponse(cDevice.armID, crypt, ExchData.AddNewClient, false);
        byte[] decryptedResponse = BIT_PKCS11CL3.Decrypt(response, KeyStore.sessionKeyProd, err);

        if(decryptedResponse == null) {
            String resp = loginBase();
            throw new MyException(resp);
        }
        String responseStr = new String(decryptedResponse, StandardCharsets.UTF_8);
        writeStringToFile(responseStr, "Response", ".xml");
        DocumentElement de = ConverterUtil.xmlToObject(responseStr, DocumentElement.class);
        if (de.getNewClients() == null) throw new MyException("Список NewClients пуст");
        NewClient result = de.getNewClients().get(0);

        if (result.getError() != null) throw new UnprocessableEntityException(result.getError());

        return ResponseEntity.ok().body(ConverterUtil.objectToJson(result));
    }






}
