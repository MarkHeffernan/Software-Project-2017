package markheffernan.safeeat;


/**
 * Created by Mark Heffernan on 04/12/2017.
 */

import android.app.Application;

import com.firebase.client.Firebase;

public class SafeEat extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    }
