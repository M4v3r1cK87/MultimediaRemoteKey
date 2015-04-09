package org.multimediaremotekey.socketHandler;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.multimediaremotekey.R;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by vviviani on 07/04/2015.
 */
public class SocketHandler extends AsyncTask<String, Void, Socket> {
    private Activity activity;
    private TextView tvConnectionStatus = null;

    public SocketHandler(WeakReference wr)
    {
        activity = (Activity)wr.get();
        tvConnectionStatus = ((TextView)activity.findViewById(R.id.txtReceive));
    }

    @Override
    protected Socket doInBackground(String... params)
    {
        Socket socket = null;
        try
        {
            socket = new Socket(params[0], Integer.parseInt(params[1]));
            socket.setReceiveBufferSize(10240);
            socket.setSendBufferSize(10240);
        }
        catch (UnknownHostException e)
        {
            Log.e(this.getClass().getSimpleName(), "Errore durante la creazione della Socket ...", e);
            tvConnectionStatus.setText("Errore : Host di destinazione sconosciuto.");
        }
        catch (ConnectException e)
        {
            Log.e(this.getClass().getSimpleName(), "Errore durante la creazione della Socket ...", e);
            tvConnectionStatus.setText("Errore : Connessione rifiutata.");
        }
        catch (Exception e)
        {
            Log.e(this.getClass().getSimpleName(), "Errore durante la creazione della Socket ...", e);
            tvConnectionStatus.setText("Errore generico.");
        }

        return socket;
    }

    @Override
    protected void onPostExecute(Socket socket) {
        tvConnectionStatus.setText("Connesso : " + socket.isConnected());
    }
}
