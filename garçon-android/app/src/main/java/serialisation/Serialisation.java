package serialisation;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Hashtable;

import PrincipauxComposants.Commande;

public class Serialisation {

    public static Hashtable<Integer, Commande> deserialiser()
    {
        String sFileName = "listecommandes";
        File root = new File(Environment.getExternalStorageDirectory(), "dataServeur");
        if (!root.exists()) {
            root.mkdirs();
        }

        try {
            ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(new File(root, sFileName)));
            return (Hashtable<Integer,Commande>)OIS.readObject();



        } catch (IOException | ClassNotFoundException e) {
            return new Hashtable<Integer,Commande>();
        }






    }

    public static void serialisation(Hashtable<Integer,Commande> H)
    {
        String sFileName = "listecommandes";
        File root = new File(Environment.getExternalStorageDirectory(), "dataServeur");
        if (!root.exists()) {
            root.mkdirs();
        }

        try {
            ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(new File(root, sFileName)));
            OOS.writeObject(H);



        } catch (IOException e) {
        }
    }
}
