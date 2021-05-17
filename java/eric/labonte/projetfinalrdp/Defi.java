package eric.labonte.projetfinalrdp;

import java.io.Serializable;

public class Defi implements Serializable {
    private String theme;
    private String categorie; // 25 ou 610
    private String exercicePhys;
    private boolean reussi;

    public Defi(String theme, String categorie, String exercicePhys) {
        this.theme = theme;
        this.categorie = categorie;
        this.exercicePhys = exercicePhys;
        this.reussi = false;
    }

    public String getTheme() {
        return theme;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getExercicePhys() {
        return exercicePhys;
    }

    public boolean isReussi() {
        return reussi;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }
}
