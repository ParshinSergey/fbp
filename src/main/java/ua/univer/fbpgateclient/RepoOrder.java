package ua.univer.fbpgateclient;

import javax.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@Getter
@Setter
@XmlRootElement(name = "RepoOrder", namespace = "")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "applicationID",
        "crossApplicationID",
        "createDate",
        "brokerID",
        "armID",
        "isBuy",
        "orderType",
        "account",
        "isin",
        "quantity",
        "price",
        "pricePart2",
        "contractSum",
        "contractSumPart2",
        "clientID",
        "clientName",
        "clientEDRPOU",
        "clientINN",
        "clientEDRISI",
        "clientCode",
        "contractNum",
        "contractText",
        "contractNumPart2",
        "contractTextPart2",
        "termDate",
        "termDatePart2",
        "repoRate",
        "repoRateSum",
        "isDisposeOfCP",
        "couponRecipient",
        "couponSum",
        "couponRequisites",
        "couponCurrencyRate",
        "isAddressToBroker",
        "toBrokerID",
        "toBrokerEdrpou",
        "isCCP",
        "tradeCurrency",
        "applicationContext",
        "applicationContextUnique",
        "isComplete",
        "isRemoval",
        "isRejected",
        "description",
        "account2",
        "clientCode2",
        "clientID2",
        "contractNum2",
        "contractText2",
        "contractNum2Part2",
        "contractText2Part2",
        "journalID",
        "journalID2",
        "journalIDPart2",
        "journalID2Part2"
})
public class RepoOrder {

    @XmlElement(name = "ApplicationID")
    protected Integer applicationID;
    @XmlElement(name = "CrossApplicationID")
    protected Integer crossApplicationID;
    @XmlElement(name = "CreateDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    @XmlElement(name = "BrokerID")
    protected String brokerID;
    @XmlElement(name = "ArmID")
    protected String armID;
    @XmlElement(name = "IsBuy")
    protected Boolean isBuy;
    @XmlElement(name = "OrderType")
    protected Integer orderType;
    @XmlElement(name = "Account")
    protected String account;
    @XmlElement(name = "ISIN")
    protected String isin;
    @XmlElement(name = "Quantity")
    protected Long quantity;
    @XmlElement(name = "Price")
    protected Double price;
    @XmlElement(name = "PricePart2")
    protected Double pricePart2;
    @XmlElement(name = "ContractSum")
    protected BigDecimal contractSum;
    @XmlElement(name = "ContractSumPart2")
    protected BigDecimal contractSumPart2;
    @XmlElement(name = "ClientID")
    protected Integer clientID;
    @XmlElement(name = "ClientName")
    protected String clientName;
    @XmlElement(name = "ClientEDRPOU")
    protected String clientEDRPOU;
    @XmlElement(name = "ClientINN")
    protected String clientINN;
    @XmlElement(name = "ClientEDRISI")
    protected String clientEDRISI;
    @XmlElement(name = "ClientCode")
    protected String clientCode;
    @XmlElement(name = "ContractNum")
    protected String contractNum;
    @XmlElement(name = "ContractText")
    protected String contractText;
    @XmlElement(name = "ContractNumPart2")
    protected String contractNumPart2;
    @XmlElement(name = "ContractTextPart2")
    protected String contractTextPart2;
    @XmlElement(name = "TermDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar termDate;
    @XmlElement(name = "TermDatePart2")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar termDatePart2;
    @XmlElement(name = "RepoRate")
    protected Double repoRate;
    @XmlElement(name = "RepoRateSum")
    protected Double repoRateSum;
    @XmlElement(name = "IsDisposeOfCP")
    protected Boolean isDisposeOfCP;
    @XmlElement(name = "CouponRecipient")
    protected Integer couponRecipient;
    @XmlElement(name = "CouponSum")
    protected Double couponSum;
    @XmlElement(name = "CouponRequisites")
    protected String couponRequisites;
    @XmlElement(name = "CouponCurrencyRate")
    protected Double couponCurrencyRate;
    @XmlElement(name = "IsAddressToBroker")
    protected Boolean isAddressToBroker;
    @XmlElement(name = "ToBrokerID")
    protected String toBrokerID;
    @XmlElement(name = "ToBrokerEdrpou")
    protected String toBrokerEdrpou;
    @XmlElement(name = "IsCCP")
    protected Boolean isCCP;
    @XmlElement(name = "TradeCurrency")
    protected String tradeCurrency;
    @XmlElement(name = "ApplicationContext")
    protected String applicationContext;
    @XmlElement(name = "ApplicationContextUnique")
    protected String applicationContextUnique;
    @XmlElement(name = "IsComplete")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar isComplete;
    @XmlElement(name = "IsRemoval")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar isRemoval;
    @XmlElement(name = "IsRejected")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar isRejected;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Account2")
    protected String account2;
    @XmlElement(name = "ClientCode2")
    protected String clientCode2;
    @XmlElement(name = "ClientID2")
    protected Integer clientID2;
    @XmlElement(name = "ContractNum2")
    protected String contractNum2;
    @XmlElement(name = "ContractText2")
    protected String contractText2;
    @XmlElement(name = "ContractNum2Part2")
    protected String contractNum2Part2;
    @XmlElement(name = "ContractText2Part2")
    protected String contractText2Part2;

    @XmlElement(name = "JournalID")
    protected Integer journalID;
    @XmlElement(name = "JournalID2")
    protected Integer journalID2;
    @XmlElement(name = "JournalIDPart2")
    protected Integer journalIDPart2;
    @XmlElement(name = "JournalID2Part2")
    protected Integer journalID2Part2;


}
