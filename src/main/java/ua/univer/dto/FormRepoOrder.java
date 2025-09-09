package ua.univer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@Setter
@NoArgsConstructor
public class FormRepoOrder {

    private Integer applicationID = -1;
    private Integer crossApplicationID = -1;
    private XMLGregorianCalendar createDate;
    private String brokerID;
    private String armID;
    @NotNull
    private Boolean isBuy;
    @NotNull
    private Integer orderType;
    @NotBlank
    private String account;
    @NotBlank
    private String isin;
    @NotNull
    private Long quantity;
    @NotNull
    private Double price;
    @NotNull
    private Double pricePart2;
    @NotBlank
    private String contractSum;
    @NotBlank
    private String contractSumPart2;
    private Integer clientID;
    private String clientName;
    private String clientEDRPOU;
    private String clientINN;
    private String clientEDRISI;
    @NotBlank
    private String clientCode;
    private String contractNum;
    private String contractText;
    private String contractNumPart2;
    private String contractTextPart2;
    @NotBlank
    private String termDate;
    @NotBlank
    private String termDatePart2;
    @NotNull
    private Double repoRate;
    @NotNull
    private Double repoRateSum;
    @NotNull
    private Boolean isDisposeOfCP;
    private Integer couponRecipient;
    private Double couponSum;
    private String couponRequisites;
    private Double couponCurrencyRate;
    @NotNull
    private Boolean isAddressToBroker;
    private String toBrokerID;
    private String toBrokerEdrpou;
    @NotNull
    private Boolean isCCP;
    @NotBlank
    private String tradeCurrency;
    //@NotNull
    private String applicationContext;
    //@NotNull
    private String applicationContextUnique;
    // поля ниже только в Ответе
    private XMLGregorianCalendar isComplete;
    private XMLGregorianCalendar isRemoval;
    private XMLGregorianCalendar isRejected;
    private String description;
    // -------------------------
    @NotBlank
    private String account2;
    @NotBlank
    private String clientCode2;
    private Integer clientID2;
    private String contractNum2;
    private String contractText2;
    private String contractNum2Part2;
    private String contractText2Part2;

}
