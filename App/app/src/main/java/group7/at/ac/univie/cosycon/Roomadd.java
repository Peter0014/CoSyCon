package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Roomadd extends AppCompatActivity {

    private SharedPreferences preferencessetting;
    private SharedPreferences.Editor editor;
    EditText gname;
    Spinner itemtype;
    RadioButton issensor;
    Button addbutton;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomadd);

        initializeVariables();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(gname.getText().toString().trim().length()<=0)
                {
                    warn("adding requires name");
                }
                else
                {
                    if(savedata())
                        success("successfully adding object");
                    else
                        warn("this serial id is already exist. type in a new one");

                }

            }
        });


    }

    private void initializeVariables()
    {
        gname = (EditText)findViewById(R.id.gname);
        itemtype = (Spinner)findViewById(R.id.itemtyp);
        issensor = (RadioButton)findViewById(R.id.issensor);
        addbutton = (Button)findViewById(R.id.addbutton);
        preferencessetting = getSharedPreferences("Rooms", Context.MODE_PRIVATE);

    }
    /*
    
     */
    private boolean dataexist()
    {

        if(preferencessetting.getString("G"+id+"_name",null)!= null)
            return true;
        else
            return false;
    }
    private boolean savedata()
    {
        int idnum = preferencessetting.getInt("G_Array_len",0);
        id = "G" + idnum;
        if(!dataexist())
        {
            editor = preferencessetting.edit();
            editor.putString(id+"_name",gname.getText().toString());
            editor.putString(id+"_itemtype",itemtype.getSelectedItem().toString());
            editor.putBoolean(id+"_issensor",issensor.isChecked());
            editor.commit();
            editor.putInt("G_Array_len",++idnum);
            editor.commit();
            return true;
        }
        else
            return false;
    }
    public void switchpage()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
};

