package eric.labonte.projetfinalrdp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

public class ListeDefisActivity extends AppCompatActivity {
    ListView listeDesThemes;
    String categorieAge;
    Button boutonQr;
   // ArrayAdapter adaptateur;
    ArrayAdapterPerso adaptateur;
    ListeDefis instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_defis);
        instance = ListeDefis.getInstance(this);
        boutonQr = findViewById ( R.id.boutonQr);
        listeDesThemes = findViewById ( R.id.listeDefis);
        listeDesThemes.setChoiceMode(2); // plusieurs choix
        //age
        categorieAge = getIntent().getStringExtra("categorie");
        Hashtable<String, Defi> table;
        if ( categorieAge.equals("25"))
           table =   ListeDefis.getInstance(this).getListeDeReussite25();
        else
            table =  ListeDefis.getInstance(this).getListeDeReussite610();
        /*
        ListeDefis.getInstance().ajouterDefiReussi("Les pays", "610");
        */

        Collection<Defi> collection =  table.values();
        Vector<Defi> vecteur = new Vector();
        vecteur.addAll(collection);

        adaptateur = new ArrayAdapterPerso(this,vecteur );
        listeDesThemes.setAdapter(adaptateur);


        Ecouteur ec = new Ecouteur();

        boutonQr.setOnClickListener(ec);
    }

    @Override
    protected void onStop(){
        super.onStop();
        try{
            FileOutputStream fos = openFileOutput("fichier.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ListeDefis instance = ListeDefis.getInstance(this);
            oos.writeObject(instance);
            oos.flush();
            oos.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private class Ecouteur implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            new IntentIntegrator(ListeDefisActivity.this).initiateScan();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent retour )
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, retour);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Annulé", Toast.LENGTH_LONG).show();
            }
            else{
                String r = Normalizer.normalize(result.getContents(), Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");

//                Toast.makeText(this, r, Toast.LENGTH_LONG).show();
//                String className = result.getContents().replaceAll("\\s+", "");
                String className = r.replaceAll("\\s+", "");
                Class destinationClass = null;
                try {
                    destinationClass = Class.forName("eric.labonte.projetfinalrdp." + className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(ListeDefisActivity.this, destinationClass);
                startActivityForResult(i, 111);
            }
        }
        else{
            //Retour de l'activité du défi
            super.onActivityResult(requestCode, resultCode, retour);
            if(requestCode == 111 && resultCode == RESULT_OK){
               String categorie = retour.getStringExtra("categorie");
               String defi = retour.getStringExtra("defi");
               //Ajout du défi réussi dans le singleton
               instance.ajouterDefiReussi(defi, categorie);
               //Recherche du défi complété dans le singleton
               Defi d = categorie.equals("25") ? instance.getListeDeReussite25().get(defi) : instance.getListeDeReussite610().get(defi);
               //Recherche de la position du défi dans l'adaptateur, afin de cocher la case correspondante dans la view
               int pos = adaptateur.getPosition(d);
               listeDesThemes.setItemChecked(pos, true);

            }
        }

    }

}