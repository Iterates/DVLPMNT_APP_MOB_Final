package eric.labonte.projetfinalrdp;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_defis);
        boutonQr = findViewById ( R.id.boutonQr);
        listeDesThemes = findViewById ( R.id.listeDefis);
        listeDesThemes.setChoiceMode(2); // plusieurs choix
        //age
        categorieAge = getIntent().getStringExtra("categorie");
        Hashtable<String, Defi> table;
        if ( categorieAge.equals("25"))
           table =   ListeDefis.getInstance().getListeDeReussite25();
        else
            table =  ListeDefis.getInstance().getListeDeReussite610();
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
        }

    }

}