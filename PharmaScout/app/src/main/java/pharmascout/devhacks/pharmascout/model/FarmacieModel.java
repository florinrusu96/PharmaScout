package pharmascout.devhacks.pharmascout.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by robert.damian on 11/19/2016.
 */

public class FarmacieModel{

    @SerializedName("Nume")
    private String nume;

    @SerializedName("F_ID")
    private int idFarmacie;

    @SerializedName("M_ID")
    private List<MedicamentModel> medicamente;

    @SerializedName("Adresa")
    private String adresaFarmacie;


    /**
     * Retine programul de functionare cu formatul "hh:mm-hh:mm"
     */
    @SerializedName("Program")
    private String program;

    @SerializedName("Site")
    private String webSiteURL;

    @SerializedName("Long_Harta")
    private float longitudine;

    @SerializedName("Lat_Harta")
    private float latitudine;

    public float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public String getWebSiteURL() {
        return webSiteURL;
    }

    public void setWebSiteURL(String webSiteURL) {
        this.webSiteURL = webSiteURL;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getAdresaFarmacie() {
        return adresaFarmacie;
    }

    public void setAdresaFarmacie(String adresaFarmacie) {
        this.adresaFarmacie = adresaFarmacie;
    }

    public List<MedicamentModel> getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(List<MedicamentModel> medicamente) {
        this.medicamente = medicamente;
    }

    public int getIdFarmacie() {
        return idFarmacie;
    }

    public void setIdFarmacie(int idFarmacie) {
        this.idFarmacie = idFarmacie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

}
