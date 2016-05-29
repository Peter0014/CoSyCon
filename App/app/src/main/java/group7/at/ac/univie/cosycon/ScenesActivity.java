package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ScenesActivity extends AppCompatActivity {

    private SharedPreferences preferencessetting;
    private SharedPreferences.Editor editor;
    Context context;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenes_add);

        initializeVariables();

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.scenes_toolbar);
        setSupportActionBar(toolbar);

        preferencessetting = getSharedPreferences("Scenes", Context.MODE_PRIVATE);


    }

}
