package perso.stockagedvd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ModificationDVDBD extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private DVD val;

    ModificationDVDBD(Context c, DVD val) {
        this.context = c;
        this.val = val;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Connexion mySQL", "Modification du film...", false, false);
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

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            val.getPhoto().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] im = stream.toByteArray();

            PreparedStatement ps = mysql.prepareStatement("UPDATE DVD SET titreDVD = ?, resumeDVD = ?, photoDVD = ?, nomType = ? where id = ?");
            ps.setString(1, val.getTitreDVD());
            ps.setString(2, val.getResumeDVD());
            ps.setBytes(3, im);
            ps.setString(4, val.getNomType());
            ps.setInt(5, val.getIdDVD());

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
        context.startActivity(new Intent(context, ShowDVDActivity.class));
    }
}
