package perso.stockagedvd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DVDActivity extends AppCompatActivity {

    private DVD val;
    private TextView t, r, g;
    private ImageView p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);

        final Context context = this;

        int id = Integer.parseInt(getIntent().getStringExtra("id"));

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


            t = findViewById(R.id.titreFilmL);

            r = findViewById(R.id.resumeFilmL);

            g = findViewById(R.id.genreFilmL);

            p = findViewById(R.id.vueImageDVD);

            new RecupFilmSelectBD(this, this, id).execute();
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



        findViewById(R.id.retourDVDtoShowDVD).setTag("retour");
        findViewById(R.id.retourDVDtoShowDVD).setOnClickListener(new ActionBoutonDVDSelect(this, id));

        findViewById(R.id.supprimerDVD).setTag("supp");
        findViewById(R.id.supprimerDVD).setOnClickListener(new ActionBoutonDVDSelect(this, id));

        findViewById(R.id.modDVD).setTag("modifier");
        findViewById(R.id.modDVD).setOnClickListener(new ActionBoutonDVDSelect(this, id));

    }

    public void setVal(DVD v){
        this.val = v;
    }

    public TextView getT() {
        return t;
    }

    public TextView getR() {
        return r;
    }

    public TextView getG() {
        return g;
    }

    public ImageView getP() {
        return p;
    }
}
