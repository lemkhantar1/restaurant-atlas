package com.example.chakib.restaurantatlas;

import android.app.Activity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import PrincipauxComposants.Message;

/**
 * Created by chakib on 12/27/15.
 */
public class MonGrandThread extends  Thread {

    Activity act;
    Socket socket;
    ObjectInputStream OIS;

    public MonGrandThread(Activity act, Socket socket)
    {
        this.act = act;
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        while (true)
        {

            try {
               OIS = new ObjectInputStream(socket.getInputStream());
                new MonPetitThread(act,(Message)OIS.readObject()).execute();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
