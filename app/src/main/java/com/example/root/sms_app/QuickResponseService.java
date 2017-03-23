package com.example.root.sms_app;

import android.content.Intent;
import android.os.IBinder;

import android.app.Service;

/**
 * Created by root on 3/22/17.
 */

public class QuickResponseService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        return super.onStartCommand(intent,flags,startID);
    }
}
