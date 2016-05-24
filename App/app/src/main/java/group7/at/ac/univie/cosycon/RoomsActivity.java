package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class RoomsActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.rooms_toolbar);
        setSupportActionBar(toolbar);

    }

}
