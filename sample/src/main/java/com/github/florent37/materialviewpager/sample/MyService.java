package com.github.florent37.materialviewpager.sample;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class MyService extends Service {
    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;
    private MediaPlayer player;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i("Gsk", "BG Service");

        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        Log.i("Gsk", "BG Service");

        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class SMSreceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Log.i("GSK", "Hi There SMS");
            Toast.makeText(context, "SMS Toast", Toast.LENGTH_SHORT).show();
            Bundle extras = intent.getExtras();

            String strMessage = "";

            if ( extras != null )
            {
                Object[] smsextras = (Object[]) extras.get( "pdus" );

                for ( int i = 0; i < smsextras.length; i++ )
                {
                    SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                    String strMsgBody = smsmsg.getMessageBody().toString();
                    String strMsgSrc = smsmsg.getOriginatingAddress();

                    strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

                    Log.i("GSK", strMessage);
                    Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
}


