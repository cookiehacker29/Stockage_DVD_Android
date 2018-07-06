package perso.stockagedvd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.addDVD).setTag("add");
        findViewById(R.id.addDVD).setOnClickListener(new ActionBoutonMain(this));

        findViewById(R.id.showDVD).setTag("show");
        findViewById(R.id.showDVD).setOnClickListener(new ActionBoutonMain(this));
    }
}
