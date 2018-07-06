package perso.stockagedvd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

public class AddDVDActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap imgChoisie;
    private Context context;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dvd);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            spin = findViewById(R.id.genreFilmSpin);
            new RecupGenreBD(this, spin).execute();
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

        findViewById(R.id.selectImage).setTag("photo");
        findViewById(R.id.selectImage).setOnClickListener(new ActionBoutonAddDVD(this));

        findViewById(R.id.retourAddDVDtoMain).setTag("retour");
        findViewById(R.id.retourAddDVDtoMain).setOnClickListener(new ActionBoutonAddDVD(this));

        findViewById(R.id.ajouterDVD).setTag("ajouter");
        findViewById(R.id.ajouterDVD).setOnClickListener(new ActionBoutonAddDVD(this));
    }

    public int getPICK_IMAGE_REQUEST() {
        return this.PICK_IMAGE_REQUEST;
    }

    public Bitmap getImgChoisie() {
        return imgChoisie;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                imgChoisie = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.apercu);
                imageView.setImageBitmap(imgChoisie);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTitre(){
        EditText titre = findViewById(R.id.titreFilmET);
        return titre.getText().toString();
    }

    public String getGenre(){
        return spin.getSelectedItem().toString();
    }


    public String getResume(){
        EditText resum = findViewById(R.id.resumerFilmET);
        return resum.getText().toString();
    }

    public Context getContext() {
        return context;
    }
}


