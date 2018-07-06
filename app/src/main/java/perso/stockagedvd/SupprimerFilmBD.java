package perso.stockagedvd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SupprimerFilmBD extends AsyncTask<Void, Void, Void>{

    private ProgressDialog progressDialog;

    private int id;

    private Context context;

    SupprimerFilmBD(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Connexion mySQL", "Suppression du film...", false, false);
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

            PreparedStatement ps = mysql.prepareStatement("DELETE FROM DVD WHERE id=?");
            ps.setInt(1, id);

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
        Intent in = new Intent(context, ShowDVDActivity.class);
        context.startActivity(in);
    }
}
