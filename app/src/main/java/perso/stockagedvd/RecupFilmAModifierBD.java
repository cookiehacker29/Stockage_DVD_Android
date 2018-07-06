package perso.stockagedvd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Spinner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class RecupFilmAModifierBD extends AsyncTask<Void, Void, Void> {

    private List<String> lsGenre;
    private ProgressDialog progressDialog;

    private int id;

    private DVD recup;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private ModifierDVDActivity app;


    RecupFilmAModifierBD(Context c, ModifierDVDActivity app, int id) {
        this.context = c;
        this.app = app;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Connexion mySQL", "Recuperation des infos...", false, false);
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

            PreparedStatement ps = mysql.prepareStatement("SELECT * FROM DVD WHERE id = ?");
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();

            res.next();

            byte[] data = res.getBytes(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            recup = new DVD(res.getInt(1), res.getString(2),res.getString(3),res.getString(5), bitmap);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        int position = getIndexByString(app.getGenre(), recup.getNomType());
        app.getGenre().setSelection(position, true);
        app.getPic().setImageBitmap(recup.getPhoto());
        app.getResumer().setText(recup.getResumeDVD());
        app.getTitre().setText(recup.getTitreDVD());
        app.setImgChoisie(recup.getPhoto());
        progressDialog.dismiss();
    }

    private int getIndexByString(Spinner spinner, String string) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
