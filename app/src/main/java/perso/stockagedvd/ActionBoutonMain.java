package perso.stockagedvd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ActionBoutonMain implements View.OnClickListener {

    Context context;

    ActionBoutonMain(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;

        if (b.getTag().equals("show")) {
            Intent i = new Intent(context, ShowDVDActivity.class);
            context.startActivity(i);
        } else if (b.getTag().equals("add")) {
            Intent i = new Intent(context, AddDVDActivity.class);
            context.startActivity(i);
        }

    }
}
