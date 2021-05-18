package eric.labonte.projetfinalrdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

    //TODO 1.Finaliser intent de retour
    //     2.Placer ecouteur sur bouton du dialogue
    //     3.Redefinir methode onSaveInstanceState
    //     4.Serialiser singleton


    private final int COL_NUM = 5;

    ListeDefis ld = ListeDefis.getInstance();

    Integer[]listeFruits = new Integer[] {R.drawable.citron, R.drawable.ananas, R.drawable.cerise,
                                        R.drawable.abricot, R.drawable.banane, R.drawable.pomme,
                                        R.drawable.fraise, R.drawable.framboise, R.drawable.poire,
                                        R.drawable.raisin};
    String[]listeOrigines = new String[] {"Asie", "Amérique du Sud", "Europe et Asie", "Asie Centrale",
                                         "Malaysie", "Asie Centrale", "Europe", "Asie de l'Est",
                                         "Chine", "Moyen-Orient"};
    TableLayout parentFruit, parentOrigine;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesfruits);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        parentFruit = findViewById(R.id.parentFruit);
        parentOrigine = findViewById(R.id.parentOrigine);

        creerAlertDialog(R.string.fdesc);

        for(int i = 0; i < 2; i++){
            TableRow toAppendOne = (TableRow)parentFruit.getChildAt(i);
            TableRow toAppendTwo = (TableRow)parentOrigine.getChildAt(i);
            for(int j = 0; j < COL_NUM; j++){
                int index = (i * COL_NUM) + j;
                ImageView iv = new ImageView(this);
                TextView tv = new TextView(this);
                tv.setTextSize(15);
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
                    Toast.makeText(this, String.format("%d", counter), Toast.LENGTH_SHORT).show();
                    TableRow parent = (TableRow)view.getParent();
                    view.setOnTouchListener(null);
                    view.setBackground(getDrawable(R.color.grey));
//                    parent.removeView(view);
                }
                else{
                    Toast.makeText(this, R.string.repno, Toast.LENGTH_SHORT).show();
                    view.setVisibility(View.VISIBLE);
                }
                break;
            case ACTION_DRAG_ENDED :
                v.setBackground(getDrawable(R.color.white));
                if(counter >= 1){
                    ld.ajouterDefiReussi("Les fruits", "610");
                    Drawable bg = getResources().getDrawable(R.drawable.tournetete);
                    CustomDialog cd = new CustomDialog(Lesfruits.this, bg, "Faites 5 rotations de la tête");
                    cd.setCancelable(false);
                    cd.setCanceledOnTouchOutside(false);
                    cd.show();

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
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}