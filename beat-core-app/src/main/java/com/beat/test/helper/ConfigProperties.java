package com.beat.test.helper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by stevevarsanis on 13/11/19.
 */
public class ConfigProperties {

    Properties properties = new Properties();

    public static String configurationFileName  = "Configuration.properties";
    public static String configurationsFilePath = "/src/test/resources/Properties/";
    private static String root =  System.getProperty("user.dir");
    private static String LINE = "-------------------------------------------------------------------------";

    public static final String PRINT_CONFIGURATION_ON_STARTUP = "PRINT_CONFIGURATION_ON_STARTUP";
    public static final String APPLICATION_URL = "APPLICATION_URL";


    public ConfigProperties(){
    }

    private Properties loadProperties() {
        try {
            InputStream input = new FileInputStream(root + configurationsFilePath + configurationFileName);
            properties.load(input);
        }catch (Exception ex) {
            ex.printStackTrace();}

            return properties;
    }

    public String getPropertyValue(String property) {
            properties = loadProperties();
            return properties.getProperty(property);
    }



}
