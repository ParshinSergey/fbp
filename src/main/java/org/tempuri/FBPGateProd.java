package org.tempuri;

import ua.univer.config.ConfigProperties;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "FBPGateService", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://77.88.202.130:4000/FBPGate.IFBPGateService/?wsdl")
public class FBPGateProd extends Service
{

    private final static URL FBPGATESERVICE_WSDL_LOCATION;
    private final static WebServiceException FBPGATESERVICE_EXCEPTION;
    private final static QName FBPGATESERVICE_QNAME = new QName("http://tempuri.org/", "FBPGateService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(ConfigProperties.SERVER_URL);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FBPGATESERVICE_WSDL_LOCATION = url;
        FBPGATESERVICE_EXCEPTION = e;
    }

    public FBPGateProd() {
        super(__getWsdlLocation(), FBPGATESERVICE_QNAME);
    }

    public FBPGateProd(WebServiceFeature... features) {
        super(__getWsdlLocation(), FBPGATESERVICE_QNAME, features);
    }

    public FBPGateProd(URL wsdlLocation) {
        super(wsdlLocation, FBPGATESERVICE_QNAME);
    }

    public FBPGateProd(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FBPGATESERVICE_QNAME, features);
    }

    public FBPGateProd(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FBPGateProd(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns IFBPGateService
     */
    @WebEndpoint(name = "WSHttpBinding_FBPGate")
    public IFBPGateProd getWSHttpBindingFBPGate() {
        return super.getPort(new QName("http://tempuri.org/", "WSHttpBinding_FBPGate"), IFBPGateProd.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IFBPGateService
     */
    @WebEndpoint(name = "WSHttpBinding_FBPGate")
    public IFBPGateProd getWSHttpBindingFBPGate(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "WSHttpBinding_FBPGate"), IFBPGateProd.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FBPGATESERVICE_EXCEPTION!= null) {
            throw FBPGATESERVICE_EXCEPTION;
        }
        return FBPGATESERVICE_WSDL_LOCATION;
    }

}
