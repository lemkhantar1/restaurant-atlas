package com.example.chakib.restaurantatlas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import Constantes.Constantes;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;
import serialisation.Serialisation;

public class ProduitActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    int idCommande, idServeur;
    public Message msg = null;
    Socket s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);
        ListView mainListView = (ListView) findViewById( R.id.mainListView);

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


        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, new ArrayList<String>());

        Hashtable<Integer, Commande> H = new Hashtable<Integer,Commande>() ;
        H = Serialisation.deserialiser();
        Commande cmdTmp = H.get(idCommande);

        for(int i=0; i<cmdTmp.getListeProduits().size() ; i++)
        {
            listAdapter.add(cmdTmp.getListeProduits().get(i).getIntitule()+"   -   Quantite : "+cmdTmp.getListeProduits().get(i).getQuantite());
        }


        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, OperationActivity.class);
        i.putExtra("idCommande",idCommande);
        i.putExtra("idServeur",idServeur);
        startActivity(i);

    }
}
