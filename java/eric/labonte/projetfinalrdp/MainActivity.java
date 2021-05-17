package eric.labonte.projetfinalrdp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    Button boutonVersListe;
    RadioGroup groupe;
    RadioButton bouton25, bouton610;
    ImageView imageFac;

    Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonVersListe = findViewById ( R.id.boutonVersListe);
        groupe = findViewById ( R.id.groupe);
        bouton25 = findViewById(R.id.radio25);
        bouton610 = findViewById(R.id.radio610);
        imageFac = findViewById(R.id.imageFac);


        ec = new Ecouteur ();
        boutonVersListe.setOnClickListener(ec);
        imageFac.setOnClickListener(ec);


        bouton25.setChecked(true);


    }

    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent intent=null;
            if ( v == boutonVersListe)
            {
                intent = new Intent(MainActivity.this, ListeDefisActivity.class);
                if ( bouton25.isSelected())
                    intent.putExtra("categorie", "25");
                else
                    intent.putExtra("categorie", "610");
            }
            else if (  v == imageFac)
            {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Initiative123GoRDP") );
            }

            startActivity(intent);


        }
    }
}