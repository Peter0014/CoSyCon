package group7.at.ac.univie.cosycon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Roomadd extends AppCompatActivity {

    EditText gname, serialid;
    Spinner itemtype;
    RadioButton issensor;
    Button addbutton;
    G object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomadd);

        initializeVariables();
        //G object = new G(serialid.getText().toString(), gname.getText().toString(), ItemTyp.valueOf(itemtype.getSelectedItem().toString()),issensor.isChecked());

        object = new G(serialid.getText().toString(), gname.getText().toString(), ItemTyp.valueOf(itemtype.getSelectedItem().toString()),issensor.isChecked());
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(gname.getText().toString().trim().length()<=0)
                {
                    warn("adding requires serial-id");
                }
                else if(serialid.getText().toString().trim().length()<=0)
                {
                    warn("adding requires name");
                }
                else
                {
                    savedata(object);
                    success("successfully adding object");

                }

            }
        });


    }

    private void initializeVariables()
    {
        gname = (EditText)findViewById(R.id.gname);
        serialid = (EditText)findViewById(R.id.serialid);
        itemtype = (Spinner)findViewById(R.id.itemtyp);
        issensor = (RadioButton)findViewById(R.id.issensor);
        addbutton = (Button)findViewById(R.id.addbutton);
    }
    private boolean checkdata(G g)
    {
        return true;
    }
    private boolean savedata(G g)
    {
        if(checkdata(g))
        {
            // add in data layer
            return true;
        }
        else
            return false;
    }
    public void switchpage()
    {
        Intent intent = new Intent(this, Roomsetting.class);
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

