package ua.univer.dto;

import lombok.extern.slf4j.Slf4j;
import ua.univer.BIT.cDevice;
import ua.univer.exeptions.UnprocessableEntityException;
import ua.univer.fbpgateclient.AddressOrder;
import ua.univer.fbpgateclient.NewClient;
import ua.univer.fbpgateclient.RepoOrder;

import javax.xml.datatype.XMLGregorianCalendar;

import static ua.univer.util.DateTimeUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class UtilForm {

    private final static String PHRASE1 = "фактична адреса проживання";
    private final static String PHRASE2 = "ВПО№";
    private final static String PHRASE3 = "фактична:";

    public UtilForm() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static NewClient convertFormToNewClient (FormNewClient form){

        NewClient client = new NewClient();
        client.setClientName(form.getClientName());
        client.setClientNameFull(form.getClientNameFull());
        client.setIsLegalEntity(form.getIsLegalEntity());
        client.setInn(form.getInn());
        client.setEdrpou(form.getEdrpou());
        client.setEdrisi(form.getEdrisi());
        client.setCountryCode(form.getCountryCode());
        client.setPassportDateOf(oneBoxCalendar(form.getPassportDateOf()));
        client.setPassportIssuerOf(form.getPassportIssuerOf());
        client.setPassportNumOf(form.getPassportNumOf());
        client.setPassportSerOf(form.getPassportSerOf());
        client.setDateOfBirth(oneBoxCalendar(form.getDateOfBirth()));
        client.setPlaceOfBirth(form.getPlaceOfBirth());
        client.setAccNoNBU(form.getAccNoNBU());
        client.setAccountTypeNBU(form.getAccountTypeNBU());
        client.setAccNoNDU(form.getAccNoNDU());
        client.setAccountTypeNDU(form.getAccountTypeNDU());
        client.setAccNoInNDU(form.getAccNoInNDU().length() == 17 ? form.getAccNoInNDU().substring(7) : form.getAccNoInNDU());
        client.setAddrFMAREACODE(form.getAddrFMAREACODE());

        setCorrectAddress(form, client);

        return client;
    }


    private static void setCorrectAddress(FormNewClient form, NewClient client) {

        //String phrase1 = "фактична адреса проживання";
        //String phrase2 = "ВПО№";
        //String phrase3 = "фактична:";
        String district = form.getAddrDISTRICT();
        String street = form.getAddrSTREET();

        if (district.matches("([\\s\\S]*)" + PHRASE1 + "([\\s\\S]*)")) {
            String[] arrAddress = form.getAddrDISTRICT().split(PHRASE1, 2);
            district = arrAddress[0].trim();
            street = arrAddress.length > 1 ? PHRASE1 + " " + arrAddress[1].trim() : null;
        }
        else if (district.matches("([\\s\\S]*)" + PHRASE2 + "([\\s\\S]*)")){
            String[] arrAddress = form.getAddrDISTRICT().split(PHRASE2, 2);
            district = arrAddress[0].trim();
            street = arrAddress.length > 1 ? PHRASE2 + " " + arrAddress[1].trim() : null;
        }
        else if (district.matches("([\\s\\S]*)" + PHRASE3 + "([\\s\\S]*)")){
            String[] arrAddress = form.getAddrDISTRICT().split(PHRASE3, 2);
            district = arrAddress[0].trim();
            street = arrAddress.length > 1 ? PHRASE3 + " " + arrAddress[1].trim() : null;
        }

        if (district.length() > 200) {
            log.warn(district);
            throw new UnprocessableEntityException("адреса має бути не більше 200 символів");
        }
        client.setAddrDISTRICT(district);
        client.setAddrSTREET(street);
    }


    public static AddressOrder convertFormToAddressOrder (FormOrder form){

        AddressOrder order = new AddressOrder();
        order.setApplicationID(form.getApplicationID());
        order.setCrossApplicationID(form.getCrossApplicationID());
        order.setMainAppID(form.getMainAppID());
        order.setBrokerID(form.getBrokerID());
        order.setArmID(cDevice.armID);
        order.setOrderTypeID(form.getOrderTypeID());
        order.setIsAddressToBroker(form.getIsAddressToBroker());
        order.setToBrokerID(form.getToBrokerID());
        order.setToBrokerEdrpou(form.getToBrokerEdrpou());
        order.setToBrokerTicket(form.getToBrokerTicket());
        order.setListingID(form.getListingID());
        order.setPaperTypeID(form.getPaperTypeID());
        order.setTicker(form.getTicker());
        order.setIsin(form.getIsin());
        order.setListingTypeID(form.getListingTypeID());
        order.setCurrencyCode(form.getCurrencyCode());
        order.setIsBuy(form.getIsBuy());
        order.setPrice(form.getPrice());
        order.setNomPercent(form.getNomPercent());
        order.setQuantity(form.getQuantity());

        order.setTermDelivery(xmlGregorianCalendar(LocalDateTime.now()));
        order.setTetmPayment(oneBoxCalendar(form.getTetmPayment()));
        // depoID;
        // payKeeperID;
        // payKeeperName;
        // payKeeperAccNo;
        // payBankName;
        // nbuDealTypeID;
        // clcode;
        // payBank;
        // payEdrpou;
        order.setPayAccount(form.getPayAccount());
        order.setPayAccountPaper(form.getPayAccountPaper());
        // clientType;
        // clientID;
        // clientName;
        order.setClientEdrpou(form.getClientEdrpou());
        // isLawerClient;
        // isNerez;
        // startDate;
        // stopDate;
        order.setIsFullPackage(form.getIsFullPackage());
        // isEmitBackPay;
        order.setAgreements(form.getAgreements());
        order.setContractNum(form.getContractNum());
        order.setIsCCP(form.getIsCCP());

        order.setApplicationContext(form.getApplicationContext());
        order.setPayAccountPaper2(form.getPayAccountPaper2());
        order.setClientEdrpou2(form.getClientEdrpou2());
        // clientID2;
        order.setContractNum2(form.getContractNum2());
        order.setAgreements2(form.getAgreements2());
        // crossApplicationContext;
        order.setTradeCurrency(form.getTradeCurrency());
        order.setApplicationContextUnique(form.getApplicationContextUnique());
        order.setContractSum(new BigDecimal(form.getContractSum()));

        return order;


    }

    public static RepoOrder convertFormToRepoOrder (FormRepoOrder form){

        RepoOrder order = new RepoOrder();
        order.setApplicationID(form.getApplicationID());
        order.setCrossApplicationID(form.getCrossApplicationID());
        order.setCreateDate(xmlGregorianCalendar(LocalDateTime.now()));
        order.setBrokerID(form.getBrokerID());
        order.setArmID(cDevice.armID);
        order.setIsBuy(form.getIsBuy());
        order.setOrderType(form.getOrderType());
        order.setAccount(form.getAccount());
        order.setIsin(form.getIsin());
        order.setQuantity(form.getQuantity());
        order.setPrice(form.getPrice());
        order.setPricePart2(form.getPricePart2());
        order.setContractSum(new BigDecimal(form.getContractSum()));
        order.setContractSumPart2(new BigDecimal(form.getContractSumPart2()));
        order.setClientID(form.getClientID());
        order.setClientName(form.getClientName());
        order.setClientEDRPOU(form.getClientEDRPOU());
        order.setClientINN(form.getClientINN());
        order.setClientEDRISI(form.getClientEDRISI());
        order.setClientCode(form.getClientCode());
        order.setContractNum(form.getContractNum());
        order.setContractText(form.getContractText());
        order.setContractNumPart2(form.getContractNumPart2());
        order.setContractTextPart2(form.getContractTextPart2());
        order.setTermDate(oneBoxCalendar(form.getTermDate()));
        order.setTermDatePart2(oneBoxCalendar(form.getTermDatePart2()));
        order.setRepoRate(form.getRepoRate());
        order.setRepoRateSum(form.getRepoRateSum());
        order.setIsDisposeOfCP(form.getIsDisposeOfCP());
        order.setCouponRecipient(form.getCouponRecipient());
        order.setCouponSum(form.getCouponSum());
        order.setCouponRequisites(form.getCouponRequisites());
        order.setCouponCurrencyRate(form.getCouponCurrencyRate());
        order.setIsAddressToBroker(form.getIsAddressToBroker());
        order.setToBrokerID(form.getToBrokerID());
        order.setToBrokerEdrpou(form.getToBrokerEdrpou());
        order.setIsCCP(form.getIsCCP());
        order.setTradeCurrency(form.getTradeCurrency());
        //order.setApplicationContext(UUID.randomUUID().toString());
        order.setApplicationContext(form.getApplicationContext());
        order.setApplicationContextUnique(form.getApplicationContextUnique());
        //order.setApplicationContextUnique(UUID.randomUUID().toString());
        // isComplete;
        // isRemoval;
        // isRejected;
        // description;
        order.setAccount2(form.getAccount2());
        order.setClientCode2(form.getClientCode2());
        order.setClientID2(form.getClientID2());
        order.setContractNum2(form.getContractNum2());
        order.setContractText2(form.getContractText2());
        order.setContractNum2Part2(form.getContractNum2Part2());
        order.setContractText2Part2(form.getContractText2Part2());

        return order;

    }






}
