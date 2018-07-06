package perso.stockagedvd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;

public class ActionBoutonDVDSelect implements View.OnClickListener {

    private Context context;
    private int id;

    ActionBoutonDVDSelect(Context context, int id){
        this.context = context;
        this.id = id;
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;

        if(b.getTag().equals("retour")){
            Intent in = new Intent(context, ShowDVDActivity.class);
            context.startActivity(in);
        }
        else if (b.getTag().equals("supp")){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                new SupprimerFilmBD(context, id).execute();

            }
            else {
                final AlertDialog ok = new AlertDialog.Builder(context)
                        .setTitle("Erreur !")
                        .setMessage("La connexion à la base de donnée a été un echec !\n" +
                                "Veuillez verifier votre connexion internet")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        }
        else if(b.getTag().equals("modifier")){
            Intent in = new Intent(context, ModifierDVDActivity.class);
            in.putExtra("ID", String.valueOf(id));
            context.startActivity(in);
        }
    }
}
