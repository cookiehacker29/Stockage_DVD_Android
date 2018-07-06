package perso.stockagedvd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.mysql.jdbc.PacketTooBigException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AJouterUnDVDBD extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private String titre;
    private String resume;
    private Bitmap photoDVD;
    private String nomType;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    AJouterUnDVDBD(Context context, String titre, String resume, Bitmap photoDVD, String nomType) {
        this.context = context;
        this.titre = titre;
        this.resume = resume;
        this.photoDVD = photoDVD;
        this.nomType = nomType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Connexion MySQL", "Ajout de votre DVD...", false, false);
    }


    @Override
    protected Void doInBackground(Void... voids) {

        Properties props = new Properties();
        try {
            props.load(context.getAssets().open("config.properties"));
            Class.forName(props.getProperty("jdbc.driver"));
            String user = props.getProperty("jdbc.username");
            String pass = props.getProperty("jdbc.password");
            String url = props.getProperty("jdbc.url");

            DriverManager.setLoginTimeout(5);
            Connection mysql = DriverManager.getConnection(url, user, pass);

            Statement s = mysql.createStatement();
            ResultSet count = s.executeQuery("SELECT COUNT(id) FROM DVD");

            count.next();

            int cpt = count.getInt(1);

            PreparedStatement ps;

            if(cpt!=0){
                ps = mysql.prepareStatement("INSERT INTO DVD(id, titreDVD, resumeDVD, photoDVD, nomType)" +
                        " SELECT MAX(id)+1, ?, ?, ?, ? FROM DVD");
            }
            else{
                ps = mysql.prepareStatement("INSERT INTO DVD VALUES(1, ?, ?, ?, ?)");
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoDVD.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] im = stream.toByteArray();

            ps.setString(1, this.titre);
            ps.setString(2, this.resume);
            ps.setBytes(3, im);
            ps.setString(4, this.nomType);

            ps.executeUpdate();


        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        Intent in = new Intent(context, MainActivity.class);
        context.startActivity(in);
    }
}
