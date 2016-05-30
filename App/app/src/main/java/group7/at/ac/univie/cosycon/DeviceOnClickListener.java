package group7.at.ac.univie.cosycon;

import android.view.View;

/**
 * Created by Nguyen on 26-May-16.
 */
public class DeviceOnClickListener implements View.OnClickListener {
    String id;
    String type;

    public DeviceOnClickListener(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public DeviceOnClickListener(String id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        // dont do shit here
    }
}
