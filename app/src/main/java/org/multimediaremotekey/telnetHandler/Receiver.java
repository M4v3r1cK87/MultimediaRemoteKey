package org.multimediaremotekey.telnetHandler;

import mobiletelnetsdk.feng.gao.TelnetAPIs;

/**
 * Created by vviviani on 09/03/2015.
 */
public class Receiver implements Runnable {

    @Override
    public void run() {
        TelnetAPIs.TelnetPumpMessage();
    }
}
