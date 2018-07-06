package perso.stockagedvd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RecupGenreBD extends AsyncTask<Void, Void, Void> {

    private List<String> lsGenre;
    private ProgressDialog progressDialog;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private Spinner spin;

    RecupGenreBD(Context c, Spinner spin) {
        this.context = c;
        this.spin = spin;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Connexion mySQL", "Recuperation des genres...", false, false);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        lsGenre = new ArrayList<>();

        Properties props = new Properties();
        try {
            props.load(context.getAssets().open("config.properties"));
            Class.forName(props.getProperty("jdbc.driver"));
            String user = props.getProperty("jdbc.username");
            String pass = props.getProperty("jdbc.password");
            String url = props.getProperty("jdbc.url");

            DriverManager.setLoginTimeout(5);
            Connection mysql = DriverManager.getConnection(url, user, pass);

            PreparedStatement ps = mysql.prepareStatement("SELECT nomType FROM TYPE");
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                lsGenre.add(res.getString(1));
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lsGenre);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
        progressDialog.dismiss();
    }
}

