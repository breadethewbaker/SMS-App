package com.example.root.sms_app;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by root on 3/21/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    String smsMessageStr = "";
    MainActivity inst;

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        Toast.makeText(context, "Message Received", Toast.LENGTH_SHORT).show();
        inst = MainActivity.instance();

        if (Build.VERSION.SDK_INT >= 23) {
            if (MainActivity.active) {
                inst.updateInbox(smsMessageStr);
            } else {
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                for (int i = 0; i < sms.length; i++) {
                    String format = intentExtras.getString("format");
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);

                    String smsBody = smsMessage.getMessageBody().toString();
                    String address = smsMessage.getOriginatingAddress();

                    smsMessageStr += inst.getContactName(context,address) + ":\n";
                    smsMessageStr += smsBody + "\n";
                }
            }
        } else {
            if (MainActivity.active) {
                MainActivity inst = MainActivity.instance();
                inst.updateInbox(smsMessageStr);
            } else {
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                for (int i = 0; i < sms.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    String address = smsMessage.getOriginatingAddress();

                    smsMessageStr += inst.getContactName(context,address) + ":\n";
                    smsMessageStr += smsBody + "\n";
                }
            }
        }
    }
}