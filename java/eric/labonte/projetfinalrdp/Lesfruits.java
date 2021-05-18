package eric.labonte.projetfinalrdp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
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

    private final int COL_NUM = 5;

    Vector<Integer>vImages = new Vector<Integer>();
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
                iv.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
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
                    Toast.makeText(this, R.string.repok, Toast.LENGTH_LONG).show();
                    TableRow parent = (TableRow)view.getParent();
                    parent.removeView(view);
                    counter++;
                }
                else{
                    Toast.makeText(this, R.string.repno, Toast.LENGTH_LONG).show();
                    view.setVisibility(View.VISIBLE);
                }
                break;
            case ACTION_DRAG_ENDED :
                v.setBackground(getDrawable(R.color.white));
                if(counter == 10){
                   CustomDialog cd = new CustomDialog(Lesfruits.this);
                   ImageView cdimageView = findViewById(R.id.cdimageView);
                   cdimageView.setBackground(getResources().getDrawable(R.drawable.tournete));
                   TextView cdtextView = findViewById(R.id.cdtextView);
                   Button cdbutton = findViewById(R.id.cdButton);
                   cdbutton.setOnClickListener(v1 -> {

                   });
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
}