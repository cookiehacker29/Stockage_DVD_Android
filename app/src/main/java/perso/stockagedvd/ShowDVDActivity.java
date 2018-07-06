package perso.stockagedvd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class ShowDVDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dvd);

        final Context context = this;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            new RecupFilmBD(this, (LinearLayout) findViewById(R.id.listFilm)).execute();
        } else {
            final AlertDialog ok = new AlertDialog.Builder(this)
                    .setTitle("Erreur !")
                    .setMessage("La connexion à la base de donnée a été un echec !\n" +
                            "Veuillez verifier votre connexion internet")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent in = new Intent(context, MainActivity.class);
                            startActivity(in);
                        }
                    }).show();
        }

        findViewById(R.id.retourShowDVDtoMain).setTag("retour");
        findViewById(R.id.retourShowDVDtoMain).setOnClickListener(new ActionBoutonDVD(this));


    }
}
