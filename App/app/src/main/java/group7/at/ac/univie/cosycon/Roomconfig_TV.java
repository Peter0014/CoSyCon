package group7.at.ac.univie.cosycon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Roomconfig_TV extends AppCompatActivity {

    private SharedPreferences preferencessetting;
    SharedPreferences.Editor editor;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,chplus,chminus,volplus,volminus;
    TextView title, currentcanal;
    String id = "", name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomconfig__tv);

        initializeVariables();

        // get id which get passed by from mainactivity
        Intent main = getIntent();
        Bundle extras = main.getExtras();
        if (extras != null) {
            id = extras.getString("GID");
        }
        preferencessetting = getPreferences(0);
        editor = preferencessetting.edit();
        name = preferencessetting.getString(id+"_name",id);
        title.setText(name);

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",0);
                currentcanal.setText("0");
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",1);
                currentcanal.setText("1");
                editor.commit();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",2);
                currentcanal.setText("2");
                editor.commit();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",3);
                currentcanal.setText("3");
                editor.commit();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",4);
                currentcanal.setText("4");
                editor.commit();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",5);
                currentcanal.setText("5");
                editor.commit();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",6);
                currentcanal.setText("6");
                editor.commit();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",7);
                currentcanal.setText("7");
                editor.commit();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",8);
                currentcanal.setText("8");
                editor.commit();
                editor.commit();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(id + "_canal",9);
                currentcanal.setText("9");
                editor.commit();
            }
        });
        chplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chnow = preferencessetting.getInt(id + "_canal",0);
                if(chnow == 9)
                    chnow = 0;
                else
                    chnow++;
                editor.putInt(id + "_canal",chnow);
                editor.commit();
                currentcanal.setText(String.valueOf(chnow));
            }
        });
        chminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chnow = preferencessetting.getInt(id + "_canal",0);
                if(chnow == 0)
                    chnow = 9;
                else
                    chnow--;
                editor.putInt(id + "_canal",chnow);
                editor.commit();
                currentcanal.setText(String.valueOf(chnow));
            }
        });
        volplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int volnow = preferencessetting.getInt(id + "_volume",0);
                editor.putInt(id + "_volume",++volnow);
                editor.commit();

            }
        });
        volminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int volnow = preferencessetting.getInt(id + "_volume",0);
                editor.putInt(id + "_volume",--volnow);
                editor.commit();

            }
        });


    }

    private void initializeVariables()
    {
        b0 = (Button)findViewById(R.id.button0);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b5 = (Button)findViewById(R.id.button5);
        b6 = (Button)findViewById(R.id.button6);
        b7 = (Button)findViewById(R.id.button7);
        b8 = (Button)findViewById(R.id.button8);
        b9 = (Button)findViewById(R.id.button9);
        chplus = (Button)findViewById(R.id.chplus);
        chminus = (Button)findViewById(R.id.chminus);
        volplus = (Button)findViewById(R.id.volplus);
        volminus = (Button)findViewById(R.id.volminus);
        title = (TextView)findViewById(R.id.title);
        currentcanal = (TextView)findViewById(R.id.currentcanal);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
