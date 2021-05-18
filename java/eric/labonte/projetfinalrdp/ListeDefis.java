package eric.labonte.projetfinalrdp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class ListeDefis implements Serializable {

    // singleton
    private static ListeDefis instance;
    private Hashtable<String, Defi> listeDeReussite25;
    private Hashtable<String, Defi> listeDeReussite610;
    public static ListeDefis getInstance(Context context){
        if ( instance == null){
            try{
                FileInputStream fis = context.openFileInput("fichier.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                instance = (ListeDefis)ois.readObject();
                ois.close();
            }
            catch(Exception e){
                instance = new ListeDefis();
            }
        }
        return instance;
    }

    private ListeDefis ()
    {
     listeDeReussite25 = new Hashtable();
     // remplir
        listeDeReussite25.put ( "Les saisons", new Defi( "Les saisons", "25", "faites 5 étirements des bras"));
        listeDeReussite25.put ( "Les animaux", new Defi( "Les animaux", "25", "faites 5 étirements des jambes"));
        listeDeReussite25.put ( "Les pays", new Defi( "Les pays", "25", "mettez vos mains ur vos hanches et faites 5 rotations des hanches "));
        listeDeReussite25.put ( "Les fruits", new Defi( "Les fruits", "25", "faites 5 rotations de la tête"));
        listeDeReussite25.put ( "Les couleurs", new Defi( "Les couleurs", "25", "faites 5 pas en arrière"));
        listeDeReussite25.put ( "La musique", new Defi( "La musique", "25", "faites 5 sauts les jambes écartées"));
        listeDeReussite25.put ( "Les astres", new Defi( "Les astres", "25", "asseyez-vous sur le gazon et touché le bout de vos pieds avec vos\n" +
                "mains 5 fois"));
        listeDeReussite25.put ( "Les plantes", new Defi( "Les plantes", "25", "faites 5 pompes"));
        listeDeReussite25.put ( "Les formes", new Defi( "Les formes", "25", "faites 5 sautillements sur place"));
        listeDeReussite25.put ( "Les personnages connus", new Defi( "Les personnages connus", "25", "faites la course jusqu'à la prochaine station"));
     listeDeReussite610 = new Hashtable();
        listeDeReussite610.put ( "Les saisons", new Defi( "Les saisons", "610", "faites 5 étirements des bras"));
        listeDeReussite610.put ( "Les animaux", new Defi( "Les animaux", "610", "faites 5 étirements des jambes"));
        listeDeReussite610.put ( "Les pays", new Defi( "Les pays", "610", "mettez vos mains ur vos hanches et faites 5 rotations des hanches "));
        listeDeReussite610.put ( "Les fruits", new Defi( "Les fruits", "610", "faites 5 rotations de la tête"));
        listeDeReussite610.put ( "Les couleurs", new Defi( "Les couleurs", "610", "faites 5 pas en arrière"));
        listeDeReussite610.put ( "La musique", new Defi( "La musique", "610", "faites 5 sauts les jambes écartées"));
        listeDeReussite610.put ( "Les astres", new Defi( "Les astres", "610", "asseyez-vous sur le gazon et touché le bout de vos pieds avec vos\n" +
                "mains 5 fois"));
        listeDeReussite610.put ( "Les plantes", new Defi( "Les plantes", "610", "faites 5 pompes"));
        listeDeReussite610.put ( "Les formes", new Defi( "Les formes", "610", "faites 5 sautillements sur place"));
        listeDeReussite610.put ( "Les personnages connus", new Defi( "Les personnages connus", "610", "faites la course jusqu'à la prochaine station"));

    }

    public void ajouterDefiReussi( String nomDefi, String categorie )
    {
        Defi defiTrouve = null;
        if ( categorie.equals("25")) {
            defiTrouve = listeDeReussite25.get(nomDefi);
            defiTrouve.setReussi(true);
            listeDeReussite25.put(nomDefi, defiTrouve);
        }
        else
        {
            defiTrouve = listeDeReussite610.get(nomDefi);
            defiTrouve.setReussi(true);
            listeDeReussite610.put(nomDefi, defiTrouve);
        }

    }



    public Hashtable<String, Defi> getListeDeReussite25() {
        return listeDeReussite25;
    }

    public Hashtable<String, Defi> getListeDeReussite610() {
        return listeDeReussite610;
    }
}
