/*

package org.tempuri;

import com.arrays.ArrayOfstring;
import com.fbpgate.ArrayOfMassXMLDataType;
import com.fbpgate.DataChangesType;
import com.fbpgate.DataRequestType;
import com.fbpgate.DataResponseType;

import javax.jws.*;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "IFBPGateService", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
        com.arrays.ObjectFactory.class,
        com.fbpgate.ObjectFactory.class,
        com.serialization.ObjectFactory.class,
        com.tempuri.ObjectFactory.class
})
public interface IFBPGateProd {


    */
/**
     *
     * @return
     *     returns byte[]
     *//*

    @WebMethod(operationName = "GetServerCertificate", action = "http://tempuri.org/IFBPGateService/GetServerCertificate")
    @WebResult(name = "GetServerCertificateResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetServerCertificate", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetServerCertificate")
    @ResponseWrapper(localName = "GetServerCertificateResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetServerCertificateResponse")
    public byte[] getServerCertificate();

    */
/**
     *
     * @param strArmID
     * @return
     *     returns java.lang.String
     *//*

    @WebMethod(operationName = "GetLastError", action = "http://tempuri.org/IFBPGateService/GetLastError")
    @WebResult(name = "GetLastErrorResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetLastError", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetLastError")
    @ResponseWrapper(localName = "GetLastErrorResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetLastErrorResponse")
    public String getLastError(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID);

    */
/**
     *
     * @param rawSignData
     * @param strArmID
     * @return
     *     returns java.lang.String
     *//*

    @WebMethod(operationName = "Login", action = "http://tempuri.org/IFBPGateService/Login")
    @WebResult(name = "LoginResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Login", targetNamespace = "http://tempuri.org/", className = "com.tempuri.Login")
    @ResponseWrapper(localName = "LoginResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.LoginResponse")
    public String login(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "rawSignData", targetNamespace = "http://tempuri.org/")
            byte[] rawSignData);

    */
/**
     *
     * @param rawSignData
     * @param strArmID
     *//*

    @WebMethod(operationName = "Logout", action = "http://tempuri.org/IFBPGateService/Logout")
    @Oneway
    @RequestWrapper(localName = "Logout", targetNamespace = "http://tempuri.org/", className = "com.tempuri.Logout")
    public void logout(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "rawSignData", targetNamespace = "http://tempuri.org/")
            byte[] rawSignData);

    */
/**
     *
     * @return
     *     returns java.lang.Long
     *//*

    @WebMethod(operationName = "GetTradeStep", action = "http://tempuri.org/IFBPGateService/GetTradeStep")
    @WebResult(name = "GetTradeStepResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetTradeStep", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetTradeStep")
    @ResponseWrapper(localName = "GetTradeStepResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetTradeStepResponse")
    public Long getTradeStep();

    */
/**
     *
     * @return
     *     returns java.lang.Long
     *//*

    @WebMethod(operationName = "GetOrdersChangeID", action = "http://tempuri.org/IFBPGateService/GetOrdersChangeID")
    @WebResult(name = "GetOrdersChangeIDResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetOrdersChangeID", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetOrdersChangeID")
    @ResponseWrapper(localName = "GetOrdersChangeIDResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetOrdersChangeIDResponse")
    public Long getOrdersChangeID();

    */
/**
     *
     * @return
     *     returns java.lang.Long
     *//*

    @WebMethod(operationName = "GetLastJournalID", action = "http://tempuri.org/IFBPGateService/GetLastJournalID")
    @WebResult(name = "GetLastJournalIDResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetLastJournalID", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetLastJournalID")
    @ResponseWrapper(localName = "GetLastJournalIDResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetLastJournalIDResponse")
    public Long getLastJournalID();

    */
/**
     *
     * @param nData
     * @param withShema
     * @return
     *     returns java.lang.String
     *//*

    @WebMethod(operationName = "GetXML", action = "http://tempuri.org/IFBPGateService/GetXML")
    @WebResult(name = "GetXMLResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetXML", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetXML")
    @ResponseWrapper(localName = "GetXMLResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetXMLResponse")
    public String getXML(
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema);

    */
/**
     *
     * @param nData
     * @param withShema
     * @param strArmID
     * @return
     *     returns byte[]
     *//*

    @WebMethod(operationName = "GetCryptXML", action = "http://tempuri.org/IFBPGateService/GetCryptXML")
    @WebResult(name = "GetCryptXMLResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetCryptXML", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXML")
    @ResponseWrapper(localName = "GetCryptXMLResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLResponse")
    public byte[] getCryptXML(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema);

    */
/**
     *
     * @param nLastID
     * @param nData
     * @param withShema
     * @param strArmID
     * @return
     *     returns com.fbpgate.DataChangesType
     *//*

    @WebMethod(operationName = "GetCryptXMLChanges", action = "http://tempuri.org/IFBPGateService/GetCryptXMLChanges")
    @WebResult(name = "GetCryptXMLChangesResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetCryptXMLChanges", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLChanges")
    @ResponseWrapper(localName = "GetCryptXMLChangesResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLChangesResponse")
    public DataChangesType getCryptXMLChanges(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "nLastID", targetNamespace = "http://tempuri.org/")
            Long nLastID,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema);

    */
/**
     *
     * @param nLastID
     * @param nData
     * @param withShema
     * @param nLimit
     * @param strArmID
     * @return
     *     returns com.fbpgate.DataChangesType
     *//*

    @WebMethod(operationName = "GetCryptXMLChangesLimited", action = "http://tempuri.org/IFBPGateService/GetCryptXMLChangesLimited")
    @WebResult(name = "GetCryptXMLChangesLimitedResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetCryptXMLChangesLimited", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLChangesLimited")
    @ResponseWrapper(localName = "GetCryptXMLChangesLimitedResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLChangesLimitedResponse")
    public DataChangesType getCryptXMLChangesLimited(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "nLastID", targetNamespace = "http://tempuri.org/")
            Long nLastID,
            @WebParam(name = "nLimit", targetNamespace = "http://tempuri.org/")
            Long nLimit,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema);

    */
/**
     *
     * @param isCurrent
     * @param onDate
     * @param nData
     * @param withShema
     * @param strArmID
     * @return
     *     returns byte[]
     *//*

  */
/*  @WebMethod(operationName = "GetCryptXMLContracts", action = "http://tempuri.org/IFBPGateService/GetCryptXMLContracts")
    @WebResult(name = "GetCryptXMLContractsResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetCryptXMLContracts", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLContracts")
    @ResponseWrapper(localName = "GetCryptXMLContractsResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLContractsResponse")
    public byte[] getCryptXMLContracts(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema,
            @WebParam(name = "OnDate", targetNamespace = "http://tempuri.org/")
            XMLGregorianCalendar onDate,
            @WebParam(name = "IsCurrent", targetNamespace = "http://tempuri.org/")
            Boolean isCurrent);*//*


    */
/**
     *
     * @param nData
     * @param withShema
     * @param journalID
     * @param strArmID
     * @return
     *     returns byte[]
     *//*

*/
/*    @WebMethod(operationName = "GetCryptXMLByJournalID", action = "http://tempuri.org/IFBPGateService/GetCryptXMLByJournalID")
    @WebResult(name = "GetCryptXMLByJournalIDResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetCryptXMLByJournalID", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLByJournalID")
    @ResponseWrapper(localName = "GetCryptXMLByJournalIDResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLByJournalIDResponse")
    public byte[] getCryptXMLByJournalID(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema,
            @WebParam(name = "JournalID", targetNamespace = "http://tempuri.org/")
            Long journalID);*//*


    */
/**
     *
     * @param signedData
     * @param nData
     * @param strArmID
     * @return
     *     returns java.lang.Integer
     *//*

    @WebMethod(operationName = "SendXML", action = "http://tempuri.org/IFBPGateService/SendXML")
    @WebResult(name = "SendXMLResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "SendXML", targetNamespace = "http://tempuri.org/", className = "com.tempuri.SendXML")
    @ResponseWrapper(localName = "SendXMLResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.SendXMLResponse")
    public Integer sendXML(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "SignedData", targetNamespace = "http://tempuri.org/")
            byte[] signedData,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData);

    */
/**
     *
     * @param strArmID
     * @param xmlData
     * @return
     *     returns java.lang.Integer
     *//*

    @WebMethod(operationName = "SendMassXML", action = "http://tempuri.org/IFBPGateService/SendMassXML")
    @WebResult(name = "SendMassXMLResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "SendMassXML", targetNamespace = "http://tempuri.org/", className = "com.tempuri.SendMassXML")
    @ResponseWrapper(localName = "SendMassXMLResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.SendMassXMLResponse")
    public Integer sendMassXML(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "XMLData", targetNamespace = "http://tempuri.org/")
            ArrayOfMassXMLDataType xmlData);

    */
/**
     *
     * @param dataRequest
     * @return
     *     returns com.fbpgate.DataResponseType
     *//*

    @WebMethod(operationName = "GetExhageData", action = "http://tempuri.org/IFBPGateService/GetExhageData")
    @WebResult(name = "GetExhageDataResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetExhageData", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetExhageData")
    @ResponseWrapper(localName = "GetExhageDataResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetExhageDataResponse")
    public DataResponseType getExhageData(
            @WebParam(name = "dataRequest", targetNamespace = "http://tempuri.org/")
            DataRequestType dataRequest);

    */
/**
     *
     * @param signedData
     * @param nData
     * @param withShemaResponse
     * @param strArmID
     * @return
     *     returns byte[]
     *//*

    @WebMethod(operationName = "SendXML_Response", action = "http://tempuri.org/IFBPGateService/SendXML_Response")
    @WebResult(name = "SendXML_ResponseResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "SendXML_Response", targetNamespace = "http://tempuri.org/", className = "com.tempuri.SendXMLResponse2")
    @ResponseWrapper(localName = "SendXML_ResponseResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.SendXMLResponseResponse")
    public byte[] sendXMLResponse(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "SignedData", targetNamespace = "http://tempuri.org/")
            byte[] signedData,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "WithShemaResponse", targetNamespace = "http://tempuri.org/")
            Boolean withShemaResponse);

    */
/**
     *
     * @param applicationContext
     * @param nData
     * @param withShema
     * @param strArmID
     * @return
     *     returns byte[]
     *//*

    @WebMethod(operationName = "GetCryptXMLContext", action = "http://tempuri.org/IFBPGateService/GetCryptXMLContext")
    @WebResult(name = "GetCryptXMLContextResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetCryptXMLContext", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLContext")
    @ResponseWrapper(localName = "GetCryptXMLContextResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetCryptXMLContextResponse")
    public byte[] getCryptXMLContext(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID,
            @WebParam(name = "nData", targetNamespace = "http://tempuri.org/")
            Integer nData,
            @WebParam(name = "ApplicationContext", targetNamespace = "http://tempuri.org/")
            ArrayOfstring applicationContext,
            @WebParam(name = "WithShema", targetNamespace = "http://tempuri.org/")
            Boolean withShema);

    */
/**
     *
     * @param strArmID
     * @return
     *     returns java.lang.Integer
     *//*

    @WebMethod(operationName = "GetNewAddressOrderRequestInProcessCnt", action = "http://tempuri.org/IFBPGateService/GetNewAddressOrderRequestInProcessCnt")
    @WebResult(name = "GetNewAddressOrderRequestInProcessCntResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetNewAddressOrderRequestInProcessCnt", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetNewAddressOrderRequestInProcessCnt")
    @ResponseWrapper(localName = "GetNewAddressOrderRequestInProcessCntResponse", targetNamespace = "http://tempuri.org/", className = "com.tempuri.GetNewAddressOrderRequestInProcessCntResponse")
    public Integer getNewAddressOrderRequestInProcessCnt(
            @WebParam(name = "strArmID", targetNamespace = "http://tempuri.org/")
            String strArmID);

}
*/
