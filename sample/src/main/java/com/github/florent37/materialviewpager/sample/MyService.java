package com.github.florent37.materialviewpager.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

                SMSTokenizer myTokenizer = new SMSTokenizer(strMessage, true);
                String logMessage = myTokenizer.getRewardItem().getNotificationMessage()
                        + " : " + myTokenizer.getRewardItem().getValue();

                Log.d("GSK", logMessage);
                Toast.makeText(context, logMessage, Toast.LENGTH_SHORT).show();
                if(myTokenizer.getRewardItem() != null) {
                    activityNotification(context, myTokenizer.getRewardItem());
                }

            }
        }

        private void activityNotification(Context context, HistoryItem rewardItem) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, Reward.class);
            notificationIntent.putExtra(Reward.REWARD_VALUE_KEY, rewardItem.getValue());
            notificationIntent.putExtra(Reward.NOTIFICATION_MSG_KEY, rewardItem.getNotificationMessage());
            notificationIntent.putExtra(Reward.CATEGORY_KEY, rewardItem.getCategory().toString());

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Content Title")
                    .setContentText("Content Text - This is the message")
                    .setContentIntent(pendingIntent).getNotification();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, notification);
        }

//        An amount of $52 has been debited from A/C no. XXXX0197 for payment at DunkinDonuts
        private class SMSTokenizer {
            HistoryItem rewardItem;

            public HistoryItem getRewardItem() {
                return rewardItem;
            }

            public SMSTokenizer(String sms, boolean isSimple) {

                if(isSimple) {
                    rewardItem = new HistoryItem(
                            12,
                            new Date(),
                            "Had a wonderful time at DunkinDonuts reward yourself too by tipping 12",
                            HistoryHelper.RewardCategory.LEISURELY
                    );
                } else {
                    String patternMoney = "\\$(\\d+)";
                    String patternStore = "(\\w+)$";

                    Pattern rM = Pattern.compile(patternMoney);
                    Pattern rS = Pattern.compile(patternStore);

                    Matcher mM = rM.matcher(sms);
                    Matcher mS = rS.matcher(sms);

                    float value = 0;
                    String store = "ABC";

                    if(mM.find())
                        value = Float.parseFloat(mM.group(0));
                    if(mS.find())
                        store = mS.group(0);

                    value *= 0.15;

                    Log.i("GSK", store + " : " + value);

                    if(isValidStore(store)) {
                        rewardItem = new HistoryItem(
                                value,
                                new Date(),
                                "Had a wonderful time at " + store + " reward yourself too by tipping" + value,
                                HistoryHelper.RewardCategory.LEISURELY
                        );
                    } else {
                        rewardItem = null;
                    }
                }
            }

            private boolean isValidStore(String store) {
                String[] storeList = {
                    "DunkinDonuts", "McDonalds"
                };

                for(String s: storeList) {
                    if(s.equals(store))
                        return true;
                }
                return false;
            }
        }
    }
}


