package perso.stockagedvd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;

public class ActionBoutonDVD implements View.OnClickListener {

    private ShowDVDActivity app;

    ActionBoutonDVD(ShowDVDActivity app){
        this.app = app;
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;

        if(b.getTag().equals("retour")){
            Intent in = new Intent(app.getApplicationContext(), MainActivity.class);
            app.startActivity(in);
        }
        else {
            System.out.println(b.getTag());
            Intent in = new Intent(app.getApplicationContext(), DVDActivity.class);
            DVD v = (DVD) b.getTag();
            in.putExtra("id", String.valueOf(v.getIdDVD()));
            app.startActivity(in);
        }

    }
}
