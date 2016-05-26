package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    public final static String G_ID = "this id will be passed to RoomConfig";
    Context context;
    Toolbar toolbar;
    FloatingActionButton fab;
    BottomNavigationBar bottomNavigationBar;
    RelativeLayout scenes, timeline, room;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

        // Hide Title in ToolBar to make place for own
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Listener for changes made in BottomNavigationBar
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                switch(position) {
                    case 0:
                        scenes.setVisibility(View.INVISIBLE);
                        timeline.setVisibility(View.INVISIBLE);
                        room.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        timeline.setVisibility(View.INVISIBLE);
                        room.setVisibility(View.INVISIBLE);
                        scenes.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        scenes.setVisibility(View.INVISIBLE);
                        room.setVisibility(View.INVISIBLE);
                        timeline.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }

        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Find Content Views
        room = (RelativeLayout) findViewById(R.id.main_rooms);
        timeline = (RelativeLayout) findViewById(R.id.main_timeline);
        scenes = (RelativeLayout) findViewById(R.id.main_scenes);

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.room_toolbar);
        setSupportActionBar(toolbar);

        // Set BottomNavigationBar
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.room_bottom_navigation_bar);

        // Set Style, Color and add Items
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar
                .setInActiveColor("#607D8B")
                .setBarBackgroundColor("#FFFFFF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_home, "Rooms").setActiveColor("#4DD0E1"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_star, "Scenes").setActiveColor("#9FA8DA"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_play_clip, "TimeLine").setActiveColor("#AED581"))
                .initialise();

        // Set FloatingActionButton and set onClickListener to do something
        fab = (FloatingActionButton) findViewById(R.id.room_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                switchPage();
            }
        });

    }
    public void switchPage()
    {
        Intent intent = null;
        // Check BottomBar position and open corresponding Activity
        if (bottomNavigationBar.getCurrentSelectedPosition() == 0)
            intent = new Intent(this, Roomadd.class);
        else if (bottomNavigationBar.getCurrentSelectedPosition() == 1)
            intent = new Intent(this, ScenesActivity.class);
        else if (bottomNavigationBar.getCurrentSelectedPosition() == 2)
            intent = new Intent(this, TimelineActivity.class);

        if (intent != null)
            startActivity(intent);
    }

}
