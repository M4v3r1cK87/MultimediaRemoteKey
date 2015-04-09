package org.multimediaremotekey;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.multimediaremotekey.socketHandler.ReceiveMessage;
import org.multimediaremotekey.socketHandler.SendMessage;
import org.multimediaremotekey.socketHandler.SocketHandler;
import org.multimediaremotekey.telnetHandler.MessageHandler;
import org.multimediaremotekey.telnetHandler.Receiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

import mobiletelnetsdk.feng.gao.*;

public class MainActivity extends ActionBarActivity {
    private Socket socket = null;
    private EditText txtSend = null;
    private TextView txtReceive = null;

    private boolean connected = false;

//    static {
//        System.loadLibrary("mobiletelnetsdkjni");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSend = ((EditText) findViewById(R.id.txtSend));
        txtReceive = ((TextView) findViewById(R.id.txtReceive));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (socket != null && socket.isConnected() && !socket.isClosed())
                socket.close();
            socket = null;
        }
        catch (Exception e)
        {
            Log.e(this.getClass().getSimpleName(), "Errore durante la chiusura della connessione ...", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnConnect_onClick(View view)
    {
        try
        {
            if (socket != null)
                socket.close();
            String address = ((EditText) findViewById(R.id.txtAddress)).getText().toString();
            int port = Integer.parseInt(((EditText) findViewById(R.id.txtPort)).getText().toString());

            WeakReference wr = new WeakReference(this);
            SocketHandler socketHandler = new SocketHandler(wr);
            socketHandler.execute(address, String.valueOf(port));

            socket = socketHandler.get();


            //        MessageHandler notificationHandler = new MessageHandler();
            //        TelnetAPIs.TelnetSetDataHandler(notificationHandler);
            //        TelnetAPIs.TelnetInit(address, port);
            //        new Receiver().run();

        }
        catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Errore durante la connessione ...", e);
        }
    }

    public void btnSend_onClick(View view)
    {
        try
        {
            SendMessage sendMessage = new SendMessage(socket);
            sendMessage.execute(txtSend.getText().toString());
            Boolean messageSent = sendMessage.get();
            if (messageSent) {
                ReceiveMessage receiveMessage = new ReceiveMessage();
                receiveMessage.execute(socket);
                txtReceive.setText(receiveMessage.get());
            }
            else
                txtReceive.setText("Messaggio non inviato.");
        }
        catch (Exception e)
        {
            Log.e(this.getClass().getSimpleName(), "Errore durante l'invio dei dati ...", e);
        }
    }
}
