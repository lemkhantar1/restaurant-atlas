package com.example.chakib.restaurantatlas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import Constantes.Constantes;
import Enumeration.EtatCmd;
import Enumeration.TypePacket;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;
import PrincipauxComposants.Packet;
import serialisation.Serialisation;

public class OperationActivity extends AppCompatActivity {

    Button consulter,  annuler, paiement;
    int idCommande, idServeur;
    public Message msg = null;
    Socket s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        consulter = (Button) findViewById(R.id.consulter);
        annuler  = (Button) findViewById(R.id.annulercommande);
        paiement = (Button) findViewById(R.id.paiement);

        idCommande = getIntent().getIntExtra("idCommande",0);
        idServeur = getIntent().getIntExtra("idServeur",0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    s = new Socket(Constantes.ip_database, Constantes.port);
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    pw.println(((Integer)idServeur).toString());
                    pw.flush();
                }
                catch(IOException e) {

                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new MonGrandThread(this,s).start();


        Hashtable<Integer, Commande> H = Serialisation.deserialiser();
        Commande cmdTmp = H.get(idCommande);
        if(cmdTmp.getEtat()==EtatCmd.EN_ATTENTE_PAIEMENT)
        {
            paiement.setEnabled(false);
        }
    }


    public void onClick(View view)
    {
        if(view == annuler)
        {
            Hashtable<Integer, Commande> H = Serialisation.deserialiser();
            final Commande cmdTmp = H.get(idCommande);
            if(cmdTmp.getEtat() != EtatCmd.EN_ATTENTE)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("NOTIFICATIONS");
                alertDialog.setMessage("Impossible d'annuler la commande !");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            else
            {

                H.remove(idCommande);
                Serialisation.serialisation(H);
                final Packet p = new Packet(TypePacket.ANNULATION_CMD,cmdTmp);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            ObjectOutput OOS = new ObjectOutputStream(s.getOutputStream());
                            OOS.writeObject(p);
                            OOS.flush();
                        }
                        catch(IOException e)
                        {

                        }

                    }
                }).start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(this, commandes.class);
                i.putExtra("idServeur",idServeur);
                startActivity(i);


            }
        }
        else if(view == paiement)
        {
            Hashtable<Integer, Commande> H = Serialisation.deserialiser();
            final Commande cmdTmp = H.get(idCommande);
            cmdTmp.setEtat(EtatCmd.EN_ATTENTE_PAIEMENT);
            H.put(cmdTmp.getIdCommande(), cmdTmp);
            Serialisation.serialisation(H);
            final Packet p = new Packet(TypePacket.PAIEMENT,cmdTmp);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        ObjectOutput OOS = new ObjectOutputStream(s.getOutputStream());
                        OOS.writeObject(p);
                        OOS.flush();
                    }
                    catch(IOException e)
                    {

                    }

                }
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Demandede paiement envoyeée !", Toast.LENGTH_LONG);
            view.setEnabled(false);

        }
        else if(view == consulter)
        {
            Intent ii = new Intent(this, ProduitActivity.class);
            ii.putExtra("idCommande", idCommande);
            ii.putExtra("idServeur",idServeur);
            startActivity(ii);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, commandes.class);
        i.putExtra("idCommande",idCommande);
        i.putExtra("idServeur",idServeur);
        startActivity(i);

    }

}
