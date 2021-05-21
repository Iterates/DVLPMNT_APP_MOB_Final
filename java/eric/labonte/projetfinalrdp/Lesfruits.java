package eric.labonte.projetfinalrdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import static android.view.DragEvent.ACTION_DRAG_ENDED;
import static android.view.DragEvent.ACTION_DRAG_ENTERED;
import static android.view.DragEvent.ACTION_DRAG_EXITED;
import static android.view.DragEvent.ACTION_DRAG_STARTED;
import static android.view.DragEvent.ACTION_DROP;

public class Lesfruits extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener{

    private final int COL_NUM = 5;

    Integer[]listeFruits = new Integer[] {R.drawable.citron, R.drawable.ananas, R.drawable.cerise,
                                        R.drawable.abricot, R.drawable.banane, R.drawable.pomme,
                                        R.drawable.fraise, R.drawable.framboise, R.drawable.poire,
                                        R.drawable.raisin};
    String[]listeOrigines = new String[] {"Asie", "Amérique du Sud", "Europe et Asie", "Asie Centrale",
                                         "Malaysie", "Asie Centrale", "Europe", "Asie de l'Est",
                                         "Chine", "Moyen-Orient"};
    TableLayout parentFruit, parentOrigine;
    Chronometer timer;
    int counter = 0, timeCounter = 180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesfruits);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        parentFruit = findViewById(R.id.parentFruit);
        parentOrigine = findViewById(R.id.parentOrigine);
        timer = findViewById(R.id.timer);

        creerAlertDialog(R.string.fdesc);

        timer.setOnChronometerTickListener(chronometer -> onChronometerTickHandler());

        for(int i = 0; i < 2; i++){
            TableRow toAppendOne = (TableRow)parentFruit.getChildAt(i);
            TableRow toAppendTwo = (TableRow)parentOrigine.getChildAt(i);
            for(int j = 0; j < COL_NUM; j++){
                int index = (i * COL_NUM) + j;
                ImageView iv = new ImageView(this);
                TextView tv = new TextView(this);
                tv.setTextSize(12);
                tv.setGravity(11);
                iv.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                tv.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                iv.setTag(listeOrigines[index]);
                tv.setTag(listeOrigines[index]);
                iv.setBackground(getDrawable(listeFruits[index]));
                tv.setText(listeOrigines[index]);
                iv.setOnTouchListener(this);
                tv.setOnDragListener(this);
                toAppendOne.addView(iv);
                toAppendTwo.addView(tv);
            }
        }

    }

    private void onChronometerTickHandler() {
        timer.setText(String.format("%02d : %02d", (int)Math.floor(timeCounter/60), timeCounter%60));
        timeCounter--;
        if(timeCounter == 0){
            Intent i = new Intent(Lesfruits.this, ListeDefis.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        View view = (View)event.getLocalState();

        switch(event.getAction()){
            case ACTION_DRAG_STARTED :
                break;
            case ACTION_DRAG_ENTERED :
                v.setBackground(getResources().getDrawable(R.drawable.background_tv));
                break;
            case ACTION_DRAG_EXITED :
                v.setBackground(getDrawable(R.color.white));
                break;
            case ACTION_DROP :
                if(view.getTag() == v.getTag()){
                    counter++;
                    Toast.makeText(this, R.string.repok, Toast.LENGTH_SHORT).show();
                    //Si c'est une bonne réponse, retrait de l'écouteur sur la vue correpondante
                    view.setOnTouchListener(null);
                    view.setBackground(getDrawable(R.color.grey));
                }
                else{
                    //Sinon affichage d'un message
                    Toast.makeText(this, R.string.repno, Toast.LENGTH_SHORT).show();
                    view.setVisibility(View.VISIBLE);
                }
                break;
            case ACTION_DRAG_ENDED :
                v.setBackground(getDrawable(R.color.white));
                view.setVisibility(View.VISIBLE);
                //Fin de l'activité
                if(counter >= 10){
                    Drawable bg = getResources().getDrawable(R.drawable.tournetete);
                    CustomDialog cd = new CustomDialog(Lesfruits.this, bg, "Faites 5 rotations de la tête");
                    cd.setCancelable(false);
                    cd.setCanceledOnTouchOutside(false);
                    cd.show();
                    cd.setOnDismissListener((d)->{
                        Intent retour = new Intent();
                        retour.putExtra("defi", "Les fruits");
                        retour.putExtra("categorie", "610");
                        setResult(RESULT_OK, retour);
                        finish();
                    });
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
        v.startDragAndDrop(null, dsb, v, 0);
        v.setVisibility(View.INVISIBLE);
        return true;
    }

    public void creerAlertDialog(int msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Lesfruits.this);
        builder.setMessage(msg)
                .setTitle(R.string.ftitle);
        builder.setPositiveButton(R.string.OK, null);
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(d->timer.start());
        dialog.show();
    }

}