package ua.univer.fbpgateclient;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@XmlRootElement(name = "AddressOrder", namespace = "")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "applicationID",
        "crossApplicationID",
        "mainAppID",
        "brokerID",
        "armID",
        "orderTypeID",
        "isAddressToBroker",
        "toBrokerID",
        "toBrokerEdrpou",
        "toBrokerTicket",
        "listingID",
        "paperTypeID",
        "ticker",
        "isin",
        "listingTypeID",
        "currencyCode",
        "isBuy",
        "price",
        "nomPercent",
        "quantity",
        "completePrice",
        "completeQuantity",
        "createDate",
        "termDelivery",
        "tetmPayment",
        "depoID",
        "payKeeperID",
        "payKeeperName",
        "payKeeperAccNo",
        "payBankName",
        "nbuDealTypeID",
        "clcode",
        "payBank",
        "payEdrpou",
        "payAccount",
        "payAccountPaper",
        "clientType",
        "clientID",
        "clientName",
        "clientEdrpou",
        "isLawerClient",
        "isNerez",
        "startDate",
        "stopDate",
        "isFullPackage",
        "isEmitBackPay",
        "agreements",
        "contractNum",
        "isCCP",
        "isComplete",
        "isRemoval",
        "isRejected",
        "discription",
        "applicationContext",
        "payAccountPaper2",
        "clientEdrpou2",
        "clientID2",
        "contractNum2",
        "agreements2",
        "crossApplicationContext",
        "tradeCurrency",
        "applicationContextUnique",
        "contractSum"
})
public class AddressOrder {

    @XmlElement(name = "ApplicationID")
    protected Integer applicationID;
    @XmlElement(name = "CrossApplicationID")
    protected Integer crossApplicationID;
    @XmlElement(name = "MainAppID")
    protected Integer mainAppID;
    @XmlElement(name = "BrokerID")
    protected String brokerID;
    @XmlElement(name = "ArmID")
    protected String armID;
    @XmlElement(name = "OrderTypeID")
    protected Integer orderTypeID;
    @XmlElement(name = "IsAddressToBroker")
    protected Boolean isAddressToBroker;
    @XmlElement(name = "ToBrokerID")
    protected String toBrokerID;
    @XmlElement(name = "ToBrokerEdrpou")
    protected String toBrokerEdrpou;
    @XmlElement(name = "ToBrokerTicket")
    protected String toBrokerTicket;
    @XmlElement(name = "ListingID")
    protected Integer listingID;
    @XmlElement(name = "PaperTypeID")
    protected Integer paperTypeID;
    @XmlElement(name = "TICKER")
    protected String ticker;
    @XmlElement(name = "ISIN")
    protected String isin;
    @XmlElement(name = "ListingTypeID")
    protected Integer listingTypeID;
    @XmlElement(name = "CurrencyCode")
    protected String currencyCode;
    @XmlElement(name = "IsBuy")
    protected Boolean isBuy;
    @XmlElement(name = "Price")
    protected Double price;
    @XmlElement(name = "NomPercent")
    protected Double nomPercent;
    @XmlElement(name = "Quantity")
    protected Integer quantity;
    @XmlElement(name = "CompletePrice")
    protected Double completePrice;
    @XmlElement(name = "CompleteQuantity")
    protected Integer completeQuantity;
    @XmlElement(name = "CreateDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    @XmlElement(name = "TermDelivery")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar termDelivery;
    @XmlElement(name = "TetmPayment")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tetmPayment;
    @XmlElement(name = "DepoID")
    protected Integer depoID;
    @XmlElement(name = "PayKeeperID")
    protected String payKeeperID;
    @XmlElement(name = "PayKeeperName")
    protected String payKeeperName;
    @XmlElement(name = "PayKeeperAccNo")
    protected String payKeeperAccNo;
    @XmlElement(name = "PayBankName")
    protected String payBankName;
    @XmlElement(name = "NbuDealTypeID")
    protected Integer nbuDealTypeID;
    @XmlElement(name = "CLCODE")
    protected String clcode;
    @XmlElement(name = "PayBank")
    protected String payBank;
    @XmlElement(name = "PayEdrpou")
    protected String payEdrpou;
    @XmlElement(name = "PayAccount")
    protected String payAccount;
    @XmlElement(name = "PayAccountPaper")
    protected String payAccountPaper;
    @XmlElement(name = "ClientType")
    protected Integer clientType;
    @XmlElement(name = "ClientID")
    protected Integer clientID;
    @XmlElement(name = "ClientName")
    protected String clientName;
    @XmlElement(name = "ClientEdrpou")
    protected String clientEdrpou;
    @XmlElement(name = "IsLawerClient")
    protected Boolean isLawerClient;
    @XmlElement(name = "IsNerez")
    protected Boolean isNerez;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "StopDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar stopDate;
    @XmlElement(name = "IsFullPackage")
    protected Boolean isFullPackage;
    @XmlElement(name = "IsEmitBackPay")
    protected Boolean isEmitBackPay;
    @XmlElement(name = "Agreements")
    protected String agreements;
    @XmlElement(name = "ContractNum")
    protected String contractNum;
    @XmlElement(name = "IsCCP")
    protected Boolean isCCP;
    @XmlElement(name = "IsComplete")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar isComplete;
    @XmlElement(name = "IsRemoval")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar isRemoval;
    @XmlElement(name = "IsRejected")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar isRejected;
    @XmlElement(name = "Discription")
    protected String discription;
    @XmlElement(name = "ApplicationContext")
    protected String applicationContext;
    @XmlElement(name = "PayAccountPaper2")
    protected String payAccountPaper2;
    @XmlElement(name = "ClientEdrpou2")
    protected String clientEdrpou2;
    @XmlElement(name = "ClientID2")
    protected Integer clientID2;
    @XmlElement(name = "ContractNum2")
    protected String contractNum2;
    @XmlElement(name = "Agreements2")
    protected String agreements2;
    @XmlElement(name = "CrossApplicationContext")
    protected String crossApplicationContext;
    @XmlElement(name = "TradeCurrency")
    protected String tradeCurrency;
    @XmlElement(name = "ApplicationContextUnique")
    protected String applicationContextUnique;
    @XmlElement(name = "ContractSum")
    protected BigDecimal contractSum;

}
