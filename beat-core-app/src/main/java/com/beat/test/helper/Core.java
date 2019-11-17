package com.beat.test.helper;

/**
 * Created by stevevarsanis on 13/11/19.
 */
public class Core {

    private Management management;

    public Management suiteManager() {
        if(management == null) {
            management = new Management();
        }
        return management;
    }
}
