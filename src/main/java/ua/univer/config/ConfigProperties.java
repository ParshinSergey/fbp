package ua.univer.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "fbp.server")
public class ConfigProperties {

    private String hostName;
    private String url;
    private String wsdl;

    public static String SERVER_HOST_NAME;
    public static String SERVER_URL;
    public static String SERVER_WSDL;


    public void setHostName(String hostName) {
        this.hostName = hostName;
        SERVER_HOST_NAME = hostName;
    }

    public void setUrl(String url) {
        this.url = url;
        SERVER_URL = url;
    }

    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
        SERVER_WSDL = wsdl;
    }



}
