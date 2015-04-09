package org.multimediaremotekey.socketHandler;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by vviviani on 08/04/2015.
 */
public class ReceiveMessage extends AsyncTask<Socket, Void, String> {

    @Override
    protected String doInBackground(Socket... params) {
        String receiveBuffer = "";
        String buffer = null;

        if (params[0] != null && params[0].isConnected()) {
            try {
//                DataInputStream inputStream = new DataInputStream(params[0].getInputStream());
//                receiveBuffer = inputStream.readUTF();

                BufferedReader br = new BufferedReader(new InputStreamReader(params[0].getInputStream()));
                while((buffer = br.readLine()) != null)
                    receiveBuffer += buffer;
            } catch (Exception e) {
                Log.e(this.getClass().getSimpleName(), "Errore durante la ricezione dei dati della Socket ...", e);
            }
        }
        return receiveBuffer;
    }
}
