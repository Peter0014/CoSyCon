package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class AddRoomActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText gname;
    Spinner itemtype;
    RadioButton issensor;
    Button addbutton;
    String id;
    private SharedPreferences preferencessetting;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_add);

        initializeVariables();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                addbutton.setEnabled(false);
                if (gname.getText().toString().trim().length() <= 0) {
                    warn("adding requires name");
                } else {

                    if (savedata()) {
                        addbutton.setEnabled(true);
                        //success("successfully adding object");
                        finish();
                    } else {
                        addbutton.setEnabled(true);
                        warn("this serial id is already exist. type in a new one");
                    }

                }

            }
        });


    }

    private void initializeVariables() {
        toolbar = (Toolbar) findViewById(R.id.add_room_toolbar);
        setSupportActionBar(toolbar);
        gname = (EditText) findViewById(R.id.gname);
        itemtype = (Spinner) findViewById(R.id.itemtyp);
        issensor = (RadioButton) findViewById(R.id.issensor);
        addbutton = (Button) findViewById(R.id.addbutton);
        preferencessetting = getSharedPreferences("Rooms", Context.MODE_PRIVATE);

    }

    private boolean dataexist() {
        return preferencessetting.getString("G" + id + "_name", null) != null;
    }

    private boolean savedata() {
        int idnum = preferencessetting.getInt("G_Array_len", 0);
        id = "G" + idnum;
        if (!dataexist()) {
            SharedPreferences.Editor editor = preferencessetting.edit();
            editor.putString(id + "_name", gname.getText().toString());
            editor.putString(id + "_itemtype", itemtype.getSelectedItem().toString());
            editor.putBoolean(id + "_issensor", issensor.isChecked());
            editor.putInt("G_Array_len", ++idnum);
            editor.apply();
            return true;
        } else
            return false;
    }

    public void warn(String message) {
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

    public void success(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}