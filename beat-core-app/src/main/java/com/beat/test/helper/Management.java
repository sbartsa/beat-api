package com.beat.test.helper;

/**
 * Created by stevevarsanis on 14/11/19.
 */
public class Management {

    private Logger logger;
    public ConfigProperties properties;


    Management() {
        this.properties = new ConfigProperties();
        this.logger = new Logger();
    }
}
