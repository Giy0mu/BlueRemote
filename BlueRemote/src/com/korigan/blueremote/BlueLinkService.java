package com.korigan.blueremote;

import com.korigan.activities.MainActivity;
import com.korigan.request.AbstractRequest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;

public class BlueLinkService extends Service{
	static private NotificationManager mNotifMng;
	static private NotificationCompat.Builder mNotificationBuilder;
	static private ConnectThread mConnectThread;
	static private int NOTIFICATION_ID = 46;
	private final String TAG = "BlueLinkService";
	static final int MSG_START_CONNECTION = 1;
	static final int MSG_ADD_OBJECT_TO_SEND = 2;
	static final int MSG_STOP_CONNECTION = 3;
	
	private SmsListener mSmsListener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}
	
	@Override
	public void onCreate(){
		//Register the phone receiver and link it to this service
		mSmsListener = new SmsListener();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		registerReceiver(mSmsListener, filter);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		//Create the notification for this service
		mNotifMng = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Context context = getApplicationContext();
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);//Make sure the notification relaunch the SAME activity that is currently running
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		
		mNotificationBuilder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.icon_blue_link)
		.setContentTitle("BlueLink is on")
		.setContentText("State: Disconnected")
		.setContentIntent(pIntent);
		
		//Start this service as a foreground service with the previous notification
		startForeground(NOTIFICATION_ID, mNotificationBuilder.build());
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy(){
		//Destroy the notification for this service
		mNotifMng.cancel(NOTIFICATION_ID);
		if(mConnectThread != null){
			mConnectThread.interrupt();
			mConnectThread.stopRunning();
		}
		//Unregister the phone receiver
		unregisterReceiver(mSmsListener);
	}
	
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
    static class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_CONNECTION:
                	BluetoothDevice device = (BluetoothDevice) msg.obj;
                	mConnectThread = new ConnectThread(device);
            		mConnectThread.start();
            		mNotificationBuilder.setContentText("State: Connected");
            		mNotifMng.notify(NOTIFICATION_ID, mNotificationBuilder.build());
                    break;
                case MSG_ADD_OBJECT_TO_SEND:
                	AbstractRequest req = (AbstractRequest) msg.obj;
                	mConnectThread.write(req.serialize().array(), req.serialize().array().length);
                    break;
                case MSG_STOP_CONNECTION:
                	if(mConnectThread != null){
        				mConnectThread.interrupt();
        				mConnectThread.stopRunning();
        				mNotificationBuilder.setContentText("State: Disconnected");
        				mNotifMng.notify(NOTIFICATION_ID, mNotificationBuilder.build());
        			}
                	break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
