package eric.labonte.projetfinalrdp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;
import java.util.Vector;

public class Lespersonnagescelebres extends AppCompatActivity implements View.OnClickListener{

    private final int COL_COUNT = 4;

    Button fcstartButton;
    TableLayout fcparent;
    Vector<ImagePerso> vImages = new Vector<ImagePerso>();
    ImageView selected;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lespersonnagescelebres);

        creerAlertDialog("Associez les paires de personnages célèbres!");

        fcstartButton = findViewById(R.id.fcstartButton);
        fcparent = findViewById(R.id.fcparent);

        fcstartButton.setOnClickListener(this);

        vImages.add(new ImagePerso(R.drawable.arrendt, "ar"));
        vImages.add(new ImagePerso(R.drawable.arrendt, "ar"));
        vImages.add(new ImagePerso(R.drawable.bismarck, "bi"));
        vImages.add(new ImagePerso(R.drawable.bismarck, "bi"));
        vImages.add(new ImagePerso(R.drawable.sbsp, "sb"));
        vImages.add(new ImagePerso(R.drawable.sbsp, "sb"));
        vImages.add(new ImagePerso(R.drawable.sittingbull, "si"));
        vImages.add(new ImagePerso(R.drawable.sittingbull, "si"));
        vImages.add(new ImagePerso(R.drawable.tintin, "ti"));
        vImages.add(new ImagePerso(R.drawable.tintin, "ti"));
        vImages.add(new ImagePerso(R.drawable.tereshkova, "te"));
        vImages.add(new ImagePerso(R.drawable.tereshkova, "te"));
        vImages.add(new ImagePerso(R.drawable.pp, "pp"));
        vImages.add(new ImagePerso(R.drawable.pp, "pp"));
        vImages.add(new ImagePerso(R.drawable.lebrun, "le"));
        vImages.add(new ImagePerso(R.drawable.lebrun, "le"));

        Collections.shuffle(vImages);

        for(int i = 0; i < fcparent.getChildCount(); i++){
            TableRow toAppend = (TableRow)fcparent.getChildAt(i);
            for(int j = 0; j < COL_COUNT; j++){
                int index = (i * 4) + j;
                ImageView iv = new ImageView(this);
                iv.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                iv.setTag(vImages.elementAt(index).getName() + String.format("%d,%d", i, j));
                iv.setBackground(getDrawable(vImages.elementAt(index).getImageId()));
                iv.setOnClickListener(this);
                toAppend.addView(iv);
                vImages.elementAt(index).setImage(iv);
            }
        }
    }

    public void creerAlertDialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Lespersonnagescelebres.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialogue, null);
        builder.setView(customLayout);
        TextView tv = customLayout.findViewById(R.id.dialogueTextView);
        tv.setText(msg);
        ImageView iv = customLayout.findViewById(R.id.dialogueImageView);
        iv.setBackground(getDrawable(R.drawable.arrendt));
//        builder.setMessage(msg)
        builder.setTitle("Les personnages célèbres");
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fcstartButton){
            for(ImagePerso img : vImages){
               img.getImage().setBackground(getDrawable(R.drawable.cover));
            }
        }
        //Si ce n'est pas le bouton c'est une carte
        else{
            if(selected == null){
               selected = (ImageView)v;
               for(ImagePerso img : vImages){
                   if(img.getImage().getTag() == selected.getTag()) {
                       img.getImage().setBackground(getDrawable(img.getImageId()));
                       img.setSelected(true);
                       break;
                   }
               }
            }
            //Si une carte a déjà été sélectionnée
            else{
                //La carte cliquée est affichée
                for(ImagePerso img : vImages){
                    if(img.getImage().getTag() == v.getTag()){
                        img.getImage().setBackground(getDrawable(img.getImageId()));
                        //Est-ce que la carte sélectionnée forme une paire avec la nouvelle paire
                        if(img.getName().equals(selected.getTag().toString().substring(0, 2))){
                           //Si elle forme une nouvelle paire, selected changé à true afin qu'elle reste affichée
                            img.setSelected(true);
                            //Remise à nul de l'image sélectionnée
                            selected = null;
                            //Sortie de la boucle
                            break;
                        }
                        //Si les cartes ne forment pas de paire, variable selected remise à null et changement de l'attribut selected de l'objet correspondant dans le vecteur
                        else{
                            for(ImagePerso i : vImages){
                              if(i.getImage().getTag() == selected.getTag()){
                                  i.setSelected(false);
                                  //Changement de l'image pour celle du revers de la carte
                                  i.getImage().setBackground(getDrawable(R.drawable.cover));
                                  //Sortie de la boucle
                                  break;
                              }
                           }
                           //Remise à null de l'image sélectionnée
                            selected = null;
                        }
                    }
                }
            }
            //Toutes les cartes qui ne forment pas de paire sont retournées, si toutes les cartes sont découvertes, la partie se termine
            int counter = 0;
            for(ImagePerso ip : vImages){
                if(ip.isSelected()){
                    ip.getImage().setBackground(getDrawable(ip.getImageId()));
                    counter++;
                }
                else{
                    ip.getImage().setBackground(getDrawable(R.drawable.cover));
                }
            }
            if(counter == 16){
                Drawable bg = getResources().getDrawable(R.drawable.course);
                CustomDialog cd = new CustomDialog(this, bg, "Faites la course jusqu'à la prochaine station");
                cd.setCancelable(false);
                cd.setCanceledOnTouchOutside(false);
                cd.setOnDismissListener((d)->{
                    Intent retour = new Intent();
                    retour.putExtra("defi", "Les personnages connus");
                    retour.putExtra("categorie", "25");
                    setResult(RESULT_OK, retour);
                    finish();
                });
                cd.show();
            }
        }
    }

    private class ImagePerso{

        private Integer imageId;
        private boolean isSelected = false;
        private ImageView image;
        private String name;

        ImagePerso(Integer imageId, String name){
            this.imageId = imageId;
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

        public Integer getImageId() {
            return imageId;
        }

        public void setImageId(Integer imageId) {
            this.imageId = imageId;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

    }

}

