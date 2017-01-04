package com.example.chakib.restaurantatlas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import Constantes.Constantes;
import Enumeration.EtatCmd;
import Enumeration.TypePacket;
import PrincipauxComposants.Commande;
import PrincipauxComposants.Message;
import PrincipauxComposants.Packet;
import PrincipauxComposants.Produit;
import jdbc.GestionBaseDonnee;
import serialisation.Serialisation;

public class MenuActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	GestionBaseDonnee db;
    final Context context = this;
    Vector<Produit> listeProduits = new Vector<Produit>();
    Produit produitTmp;
    List<String >MylistDataHeader = new ArrayList<String>();
    HashMap<String,List<String>> MylistDataChild = new HashMap<String, List<String>>();
    int idServeur;
    int idCommande;
    Socket s;
    public Message msg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {


        idServeur  = getIntent().getIntExtra("idServeur",0);

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

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuactivity);



		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                final String intituleProduit = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try
                        {

                            produitTmp = db.recuprerProduitParIntitule(intituleProduit);

                        }
                        catch(SQLException e)
                        {

                        }


                    }
                }).start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.prompts, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText description = (EditText) promptsView
                            .findViewById(R.id.description);

                final NumberPicker quantite = (NumberPicker) promptsView
                        .findViewById(R.id.quantite);
                quantite.setMaxValue(10);
                quantite.setMinValue(0);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            if(quantite.getValue()!=0)
                                            {
                                                produitTmp.setQuantite(quantite.getValue());
                                                produitTmp.setDescription(description.getText().toString());
                                                listeProduits.add(produitTmp);

                                            }
                                            else
                                            {
                                                Toast.makeText(MenuActivity.this, "quantite nulle !!!!!!!",Toast.LENGTH_LONG).show();

                                            }


                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.setTitle(produitTmp.getIntitule());
                    alertDialog.show();
                return false;

                }

        });
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();



		new Thread(new Runnable() {


			@Override
			public void run() {

				try {


					System.out.println("connecting.......");
					Class.forName("com.mysql.jdbc.Driver");
					db = new GestionBaseDonnee(Constantes.ip_database+":"+Constantes.port_database, Constantes.name_database, Constantes.login_database, Constantes.passwd_database);
					System.out.println("**************************connceted !!!!!*****************************");
					ResultSet categories = db.recupererListeCategorie();

					while(categories.next())
					{
						List<String> sousMenu  = new ArrayList<String>();

						MylistDataHeader.add(categories.getString(2));

						Vector<Produit> produits = db.recupererListeProduitParCategorie(categories.getInt(1));

						for(int i=0;i<produits.size();i++)
						{
							sousMenu.add(produits.get(i).getIntitule());
						}

						MylistDataChild.put(categories.getString(2), sousMenu);


					}


				}
				catch(SQLException | ClassNotFoundException e)
				{

				}
			}
		}).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i<MylistDataHeader.size() ; i++)
        {
            listDataHeader.add(MylistDataHeader.get(i));

        }

        listDataChild = (HashMap<String,List<String>>) MylistDataChild.clone();






    }

    public void ajouterCommande(View view) throws SQLException {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts_table, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText numTable = (EditText) promptsView
                .findViewById(R.id.nbTable);




        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if ( !numTable.getText().toString().equals("") && listeProduits.size()>0) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                idCommande = db.ajouterCommande();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Commande cmd = new Commande(idCommande, idServeur, Integer.parseInt(numTable.getText().toString()), false, false, listeProduits);
                                    cmd.setEtat(EtatCmd.EN_ATTENTE);
                                    cmd.setNbSousCommandes(1);
                                    cmd.setNbSousCommandesEnCours(0);
                                    cmd.setDateCommande(new Timestamp(System.currentTimeMillis()));
                                    Hashtable<Integer, Commande> H = Serialisation.deserialiser();
                                    H.put(idCommande, cmd);
                                    System.out.println(H.size());
                                    Serialisation.serialisation(H);


                                    final Packet p = new Packet(TypePacket.NOUVELLE_CMD, cmd);
                                    Toast.makeText(MenuActivity.this, "Commande ajoutee!!!", Toast.LENGTH_LONG).show();

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                ObjectOutput OOS = new ObjectOutputStream(s.getOutputStream());
                                                OOS.writeObject(p);
                                                OOS.flush();
                                            } catch (IOException e) {

                                            }
                                        }
                                    }).start();
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    listeProduits.removeAllElements();

                                } else {
                                    Toast.makeText(MenuActivity.this,"Vous n'avez pas specifiez la table !!", Toast.LENGTH_LONG);
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    public void toCommandes(View view)
    {
        Intent i = new Intent(this,commandes.class);
        i.putExtra("idServeur",idServeur);
        startActivity(i);
    }

    public void toAuth(View view)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void onBackPressed() {



    }

    public void AfficherProduits(View view)
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.commandecourante, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        final ListView liste = (ListView) promptsView
                .findViewById(R.id.listeProduits);

        ArrayAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, new ArrayList<String>());



        for(int i=0; i<listeProduits.size() ; i++)
        {
            listAdapter.add(listeProduits.get(i).getIntitule()+"   -   Quantite : "+listeProduits.get(i).getQuantite());
        }

        liste.setAdapter(listAdapter);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


}
