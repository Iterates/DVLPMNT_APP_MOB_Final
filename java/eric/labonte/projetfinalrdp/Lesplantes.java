package eric.labonte.projetfinalrdp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

public class Lesplantes extends AppCompatActivity{

    private final int GRID_SIZE = 64;
    private final int COL_COUNT = 8;
    private int pointage = 0;

    Populate p = new Populate();
    Vector<Integer> plants = new Vector<Integer>();
    Vector<Integer> water = new Vector<Integer>();
    Vector<String>grid = new Vector<String>(GRID_SIZE);
    TableLayout parent;
    Ecouteur ec;
    Random rando;
    Button buttonStart;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesplantes);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        creerAlertDialog("Défrichez le lac avant qu'il ne soit envahi!");

        parent = findViewById(R.id.fcparent);
        buttonStart = findViewById(R.id.buttonStart);
        score = findViewById(R.id.score);

        score.setText(String.format("Plantes défrichées : %d", pointage));

        rando = new Random();

        ec = new Ecouteur();

        plants.add(R.drawable.plant1);
        plants.add(R.drawable.plant2);
        plants.add(R.drawable.plant3);
        plants.add(R.drawable.plant4);
        water.add(R.drawable.water1);
        water.add(R.drawable.water2);
        water.add(R.drawable.water3);
        water.add(R.drawable.water4);

        buttonStart.setOnClickListener(ec);

        for(int i = 0; i < parent.getChildCount(); i++){
            TableRow toAppend = (TableRow)parent.getChildAt(i);
           for(int j = 0; j < COL_COUNT; j++){
               ImageView iv = new ImageView(this);
               iv.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
               iv.setTag(String.format("%d,%d",i,j));
               iv.setBackground(getResources().getDrawable(water.elementAt(rando.nextInt(4))));
               iv.setOnClickListener(ec);
               toAppend.addView(iv);
               grid.add("w");
           }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public void creerAlertDialog(String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(Lesplantes.this);

        builder.setMessage(msg)
                .setTitle("Les plantes");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v instanceof Button){
                p.start();
            }
            else{
                int row = Integer.parseInt(String.valueOf(v.getTag().toString().charAt(0)));
                int col = Integer.parseInt(String.valueOf(v.getTag().toString().charAt(2)));
                if (!grid.elementAt((row * col) + row).equals("w")) {
                    v.setBackground(getResources().getDrawable(water.elementAt(rando.nextInt(4))));
                    grid.set((row * col) + row, "w");
                    pointage++;
                }
                score.setText(String.format("Plantes défrichées : %d", pointage));
                if(pointage == 25){
                    //Fin de partie
                    Drawable bg = getResources().getDrawable(R.drawable.pushup);
                    CustomDialog cd = new CustomDialog(Lesplantes.this, bg, "Faites 5 pompes");
                    cd.setCancelable(false);
                    cd.setCanceledOnTouchOutside(false);
                    cd.setOnDismissListener((d)->{
                      Intent retour = new Intent();
                      retour.putExtra("defi", "Les plantes");
                      retour.putExtra("categorie", "610");
                      setResult(RESULT_OK, retour);
                      finish();
                    });
                    cd.show();
                }
            }
        }
    }

    private class Populate extends Thread{

        Handler handler = new Handler();
        //Runnable afin de pouvoir modifier l'interface graphique en dehors du fil d'exécution
        public void run(){
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    int row = rando.nextInt(COL_COUNT);
                    int col = rando.nextInt(COL_COUNT);
                    while(grid.elementAt((row * col) + row).equals("p")){
                        row = rando.nextInt(COL_COUNT);
                        col = rando.nextInt(COL_COUNT);
                    }
                    grid.set((row * col) + row, "p");
                    TableRow tr = (TableRow) parent.getChildAt(row);
                    tr.getChildAt(col).setBackground(getResources().getDrawable(plants.elementAt(rando.nextInt(4))));
                }
            });
            handler.postDelayed(this, 1000);
        }
    }
}