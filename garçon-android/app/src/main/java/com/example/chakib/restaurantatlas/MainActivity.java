package com.example.chakib.restaurantatlas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;

import Constantes.Constantes;
import jdbc.GestionBaseDonnee;

public class MainActivity extends AppCompatActivity {

    EditText login, password;
    TextView error;
    GestionBaseDonnee db;
    int idServeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        error = (TextView)findViewById(R.id.error);



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

    public void onClick(View view)
    {
        final String log = login.getText().toString();
        final String pass = password.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("connecting to database.......");
                    db = new GestionBaseDonnee(Constantes.ip_database+":"+Constantes.port_database, Constantes.name_database, Constantes.login_database, Constantes.passwd_database);
                    System.out.println("**************************connceted !!!!!*****************************");
                    idServeur = db.authentification_serveur(log, pass);
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


if ((idServeur) > 0)
        {
            Intent ii = new Intent(this, MenuActivity.class);
            ii.putExtra("idServeur",idServeur);
            startActivity(ii);

        }
        else if(idServeur==-1)
        {
            error.setText("Erreur de connexion a la base !");
        }
        else
        {
            error.setText("Erreur d'authentification !");
        }

    }
}
