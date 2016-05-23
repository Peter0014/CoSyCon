package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ashokvarma.bottomnavigation.*;

public class RoomActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    FloatingActionButton fab;
    BottomNavigationBar bottomNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        initializeVariables();

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.room_toolbar);
        setSupportActionBar(toolbar);

        // Set BottomNavigationBar
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.room_bottom_navigation_bar);

        // Set Style, Color and add Items
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.colorPrimaryDark)
                .setBarBackgroundColor("#FFFFFF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_star, "Scenes"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_home, "Rooms"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_play_clip, "TimeLine"))
                .initialise();

        // Set FloatingActionButton and set onClickListener to do something
        fab = (FloatingActionButton) findViewById(R.id.room_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something Beispiel:
                Snackbar.make(findViewById(android.R.id.content), "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
