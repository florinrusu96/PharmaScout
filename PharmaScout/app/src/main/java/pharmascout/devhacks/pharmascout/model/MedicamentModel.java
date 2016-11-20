package pharmascout.devhacks.pharmascout.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by robert.damian on 11/19/2016.
 */

public class MedicamentModel {
    @SerializedName("nume")
    private String nume;

    @SerializedName("pret")
    private String pret;

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

}
