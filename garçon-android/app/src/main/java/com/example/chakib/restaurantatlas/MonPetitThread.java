package com.example.chakib.restaurantatlas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Hashtable;

import Enumeration.EtatCmd;
import Enumeration.TypeMsg;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;
import serialisation.Serialisation;

/**
 * Created by chakib on 12/27/15.
 */
public class MonPetitThread extends AsyncTask<Void,Void,Void>{

    Activity act;
    Message msgRecu;
    ObjectInputStream OIS;

    public MonPetitThread(Activity act, Message msgRecu)

    {
        this.act = act;
        this.msgRecu = msgRecu;
        System.out.println("un msg est recu !");
    }


    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(msgRecu.getTypeMessage() == TypeMsg.FACTURE_IMPRIMEE)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(act).create();
            alertDialog.setTitle("NOTIFICATIONS");
            Hashtable<Integer, Commande> H = Serialisation.deserialiser();
            Commande cmdTmp = H.get(msgRecu.getIdCommande());
            alertDialog.setMessage("La facture de la table "+cmdTmp.getIdTable()+" est imprimee !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else if(msgRecu.getTypeMessage() == TypeMsg.CMD_PRETE)
        {
            System.out.println("***********************************_____________________**********************************");
            AlertDialog alertDialog = new AlertDialog.Builder(act).create();
            alertDialog.setTitle("NOTIFICATIONS");
            Hashtable<Integer, Commande> H = Serialisation.deserialiser();
            Commande cmdTmp = H.get(msgRecu.getIdCommande());
            cmdTmp.setEtat(EtatCmd.PRETE);
            H.put(msgRecu.getIdCommande(), cmdTmp);
            Serialisation.serialisation(H);
            alertDialog.setMessage("La commande de la table " + cmdTmp.getIdTable() + " est prête !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else if(msgRecu.getTypeMessage() == TypeMsg.CMD_ENCOURS)
        {
            Hashtable<Integer, Commande> H = Serialisation.deserialiser();
            Commande cmdTmp = H.get(msgRecu.getIdCommande());
            cmdTmp.setEtat(EtatCmd.ENCOURS);
            H.put(msgRecu.getIdCommande(), cmdTmp);
            Serialisation.serialisation(H);
        }
        else if(msgRecu.getTypeMessage() == TypeMsg.FACTURE_REGLE)
        {
            Hashtable<Integer, Commande> H = Serialisation.deserialiser();
            H.remove(msgRecu.getIdCommande());
            Serialisation.serialisation(H);
            AlertDialog alertDialog = new AlertDialog.Builder(act).create();
            alertDialog.setTitle("NOTIFICATIONS");
            alertDialog.setMessage("La commande " + msgRecu.getIdCommande() + " est reglée ! Elle va etre supprimée ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(act instanceof commandes)
            {
                act.finish();
                act.startActivity(act.getIntent());
            }
        }
    }



}
