package com.beat.test.helper;
import org.testng.annotations.BeforeSuite;

/**
 * Created by stevevarsanis on 14/11/19.
 */
public class CoreHelper {

    private static String LINE = "-------------------------------------------------------------------------";
    public static Core core;
    public static Logger logger;
    public static ConfigProperties properties;

    @BeforeSuite(alwaysRun = true, groups = "initial")
    public Core Initialize() {

        try {
            core = new Core();
            logger = new Logger();
            properties = new ConfigProperties();
            Logger.info("helper.Core initialized");
            printConfiguration();
            } catch (Exception e) {
            Logger.error("Problem initializing core");
                e.printStackTrace();
        }
        return core;
    }

    private void printConfiguration() {
        if(properties.getPropertyValue(ConfigProperties.PRINT_CONFIGURATION_ON_STARTUP).equals("true")) {
            Logger.info(LINE);
            Logger.info("Configuration filename             : " + ConfigProperties.configurationFileName);
            Logger.info("Configuration path                 : " + ConfigProperties.configurationsFilePath);
            Logger.info("Application URL                    : " + (properties.getPropertyValue(ConfigProperties.APPLICATION_URL)));
            Logger.info(LINE);
        }
    }
}
