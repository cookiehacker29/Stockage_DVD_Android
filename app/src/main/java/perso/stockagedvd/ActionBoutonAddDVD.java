package perso.stockagedvd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.mysql.jdbc.PacketTooBigException;

public class ActionBoutonAddDVD implements View.OnClickListener {

    private AddDVDActivity x;

    public ActionBoutonAddDVD(AddDVDActivity x) {
        this.x = x;
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        if (b.getTag().equals("photo")) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            x.startActivityForResult(Intent.createChooser(intent, "Select Picture"), x.getPICK_IMAGE_REQUEST());
        } else if (b.getTag().equals("retour")) {
            Intent intent = new Intent(x.getApplicationContext(), MainActivity.class);
            x.startActivity(intent);
        } else if (b.getTag().equals("ajouter")) {
            if (x.getTitre() != null && x.getGenre() != null && x.getImgChoisie() != null) {
                new AJouterUnDVDBD(x.getContext(), x.getTitre(), x.getResume(), x.getImgChoisie(), x.getGenre()).execute();
            } else {
                final AlertDialog ok = new AlertDialog.Builder(x.getContext())
                        .setTitle("Erreur !")
                        .setMessage("Les valeurs saisisent sont incorrecte !")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        }
    }
}
