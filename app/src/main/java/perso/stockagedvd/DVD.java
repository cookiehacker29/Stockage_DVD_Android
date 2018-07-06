package perso.stockagedvd;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class DVD implements Comparable<DVD>{
    private int idDVD;
    private String titreDVD;
    private  String resumeDVD;
    private  String nomType;
    private Bitmap photo;

    public DVD(int idDVD, String titreDVD, String resumeDVD, String nomType, Bitmap photo) {
        this.idDVD = idDVD;
        this.titreDVD = titreDVD;
        this.resumeDVD = resumeDVD;
        this.nomType = nomType;
        this.photo = photo;
    }

    public DVD(int idDVD, String titreDVD) {
        this.idDVD = idDVD;
        this.titreDVD = titreDVD;

    }

    public int getIdDVD() {
        return idDVD;
    }

    public String getTitreDVD() {
        return titreDVD;
    }

    public String getResumeDVD() {
        return resumeDVD;
    }

    public String getNomType() {
        return nomType;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    @Override
    public int compareTo(@NonNull DVD dvd) {
        return this.titreDVD.compareTo(dvd.getTitreDVD());
    }

    @Override
    public String toString(){
        return this.titreDVD;
    }
}
