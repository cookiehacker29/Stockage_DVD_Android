package perso.stockagedvd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

public class ModifierDVDActivity extends AppCompatActivity {

    private EditText titre, resumer;
    private ImageView pic;
    private Spinner genre;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap imgChoisie;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_dvd);

        id = Integer.parseInt(getIntent().getStringExtra("ID"));

        this.titre = findViewById(R.id.titreModif);
        this.resumer = findViewById(R.id.resumerModif);
        this.pic = findViewById(R.id.photoModif);
        this.genre = findViewById(R.id.genreModif);

        new RecupGenreBD(this, genre).execute();
        new RecupFilmAModifierBD(this, this, id).execute();

        findViewById(R.id.retourModifierDVDtoDVDSelect).setTag("retour");
        findViewById(R.id.retourModifierDVDtoDVDSelect).setOnClickListener(new ActionBoutonModifDVD(this, id));

        findViewById(R.id.validerModificationDVD).setTag("modifier");
        findViewById(R.id.validerModificationDVD).setOnClickListener(new ActionBoutonModifDVD(this, id));

        findViewById(R.id.changePhoto).setTag("photo");
        findViewById(R.id.changePhoto).setOnClickListener(new ActionBoutonModifDVD(this, id));


    }

    public int getId(){
        return id;
    }

    public EditText getTitre() {
        return titre;
    }

    public EditText getResumer() {
        return resumer;
    }

    public ImageView getPic() {
        return pic;
    }

    public Spinner getGenre() {
        return genre;
    }

    public int getPICK_IMAGE_REQUEST() {
        return this.PICK_IMAGE_REQUEST;
    }

    public Bitmap getImgChoisie() {
        return imgChoisie;
    }

    public void setImgChoisie(Bitmap v){ this.imgChoisie = v; }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                imgChoisie = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                this.pic.setImageBitmap(imgChoisie);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

