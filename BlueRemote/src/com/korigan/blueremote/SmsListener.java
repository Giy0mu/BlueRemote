package com.korigan.blueremote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {
		
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			String msg_from = null;
			
			if(bundle != null){
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				for(int i=0; i<msgs.length;i++){
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_from = msgs[i].getOriginatingAddress();
					String msgBody = msgs[i].getMessageBody();
					Log.e("SmsListener", msg_from + ": " + msgBody);
					//TODO send
				}
			}
		}
		
	}

}
