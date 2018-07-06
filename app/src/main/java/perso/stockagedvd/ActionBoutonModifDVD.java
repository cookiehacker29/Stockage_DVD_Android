package perso.stockagedvd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ActionBoutonModifDVD implements View.OnClickListener {

    private ModifierDVDActivity context;
    private int id;

    ActionBoutonModifDVD(ModifierDVDActivity context, int id){
        this.context = context;
        this.id = id;
    }

    @Override
    public void onClick(View view) {
        Button b =(Button) view;

        if (b.getTag().equals("photo")) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("ID", String.valueOf(id));
            context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), context.getPICK_IMAGE_REQUEST());
        }

        else if (b.getTag().equals("retour")){
            Intent in = new Intent(context, DVDActivity.class);
            in.putExtra("ID", String.valueOf(context.getId()));
            context.startActivity(in);
        }

        else if (b.getTag().equals("modifier")){
            DVD val = new DVD(context.getId(), context.getTitre().getText().toString(), context.getResumer().getText().toString(), context.getGenre().getSelectedItem().toString(), context.getImgChoisie());
            new ModificationDVDBD(context, val).execute();
        }
    }
}
