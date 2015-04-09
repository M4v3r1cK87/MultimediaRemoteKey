package org.multimediaremotekey.socketHandler;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by vviviani on 08/04/2015.
 */
public class SendMessage extends AsyncTask<String, Void, Boolean> {
    private Socket socket = null;
    public SendMessage(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        try
        {
            if (socket != null && socket.isConnected()) {
//                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
//                outputStream.writeUTF(params[0]);
//                outputStream.flush();
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                out.print(params[0]);
                return true;
            }
            else
                return false;
        }
        catch(Exception e)
        {
            Log.e(this.getClass().getSimpleName(), "Errore durante l'invio dei dati ...", e);
        }
        return null;
    }
}