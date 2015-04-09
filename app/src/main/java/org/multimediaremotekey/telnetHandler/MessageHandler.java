package org.multimediaremotekey.telnetHandler;

import android.widget.TextView;

import org.multimediaremotekey.MainActivity;
import org.multimediaremotekey.R;
import mobiletelnetsdk.feng.gao.TelnetNotification;

/**
 * Created by vviviani on 26/02/2015.
 */
public class MessageHandler implements TelnetNotification  {

    @Override
    public void notificationHandler(String s) {
        System.out.println("Notification Handler() - received : " + s);
//        ((TextView) MainActivity.(R.id.txtReceive)).setText(s);
    }

    @Override
    public void notificationConnectionStatus(int i) {
        System.out.println("Notification Connection Status() - received : " + i);
    }
}
