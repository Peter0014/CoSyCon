package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScenesActivity extends AppCompatActivity {

    private SharedPreferences preferencessetting;
    private SharedPreferences.Editor editor;
    EditText name;
    Button savebutton;
    Context context;
    Toolbar toolbar;

    String id;
    private long mLastClickTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenes_add);

        initializeVariables();
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                savebutton.setEnabled(false);
                if(name.getText().toString().trim().length()<=0)
                {
                    warn("adding requires name");
                }
                else
                {

                    if(savedata())
                    {
                        savebutton.setEnabled(true);
                        switchpage();
                    }

                    else
                    {
                        savebutton.setEnabled(true);
                        warn("this serial id is already exist. type in a new one");
                    }


                }

            }
        });

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.scenes_toolbar);
        setSupportActionBar(toolbar);

        preferencessetting = getSharedPreferences("Scenes", Context.MODE_PRIVATE);
        editor = preferencessetting.edit();


    }
    private boolean dataexist()
    {

        if(preferencessetting.getString("S"+id+"_name",null)!= null)
            return true;
        else
            return false;
    }
    private boolean savedata()
    {
        int idnum = preferencessetting.getInt("S_Array_len",0);
        id = "S" + idnum;
        if(!dataexist())
        {
            editor = preferencessetting.edit();
            editor.putString(id+"_name",name.getText().toString());
            editor.putInt("G_Array_len",++idnum);
            editor.commit();
            return true;
        }
        else
            return false;
    }
    public void warn(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void success(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        switchpage();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void switchpage()
    {
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}
