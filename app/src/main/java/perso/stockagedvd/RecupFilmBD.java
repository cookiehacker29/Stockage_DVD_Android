package perso.stockagedvd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class RecupFilmBD extends AsyncTask<Void, Void, Void> {

    private List<DVD> listDVD;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog progressDialog;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout layout;

    RecupFilmBD(Context context, LinearLayout layout){
        this.context = context;
        this.layout = layout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Connexion mySQL", "Recuperation des films...", false, false);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.listDVD = new ArrayList<>();

        Properties props = new Properties();
        try {
            props.load(context.getAssets().open("config.properties"));
            Class.forName(props.getProperty("jdbc.driver"));
            String user = props.getProperty("jdbc.username");
            String pass = props.getProperty("jdbc.password");
            String url = props.getProperty("jdbc.url");

            DriverManager.setLoginTimeout(5);
            Connection mysql = DriverManager.getConnection(url, user, pass);

            PreparedStatement ps = mysql.prepareStatement("SELECT id, titreDVD FROM DVD");
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                listDVD.add(new DVD(res.getInt(1), res.getString(2)));
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        Collections.sort(this.listDVD);

        for (int i = 0; i < this.listDVD.size(); i++) {
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            Button buttonView = new Button(context);
            buttonView.setText(listDVD.get(i).getTitreDVD());
            buttonView.setTag(listDVD.get(i));
            buttonView.setOnClickListener(new ActionBoutonDVD((ShowDVDActivity) context));
            layout.addView(buttonView, p);
        }

        progressDialog.dismiss();
    }
}
