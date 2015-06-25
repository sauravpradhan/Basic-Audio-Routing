package com.example.soundamplitudetestapp;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	public static Boolean isWiredHeadsetOn = false;
	public static Boolean isBTConnected = false;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		//Toast.makeText(context, "Saurav!"+action, Toast.LENGTH_SHORT).show();
		
		if(action.equals(Intent.ACTION_HEADSET_PLUG)){
			int state = intent.getIntExtra("state",3);
			if(state == 1)
			{
				isWiredHeadsetOn = true;
				MainActivity.mAm.setMode(AudioManager.MODE_IN_CALL);
				MainActivity.mAm.setSpeakerphoneOn(false);
				//Toast.makeText(context, "HeadSet Plugged!", Toast.LENGTH_SHORT).show();
				//MainActivity.mAm.setSpeakerphoneOn(false);
				
			}
			else 
			{
				
			}
				//Toast.makeText(context, "HeadSet Not Plugged!", Toast.LENGTH_SHORT).show();
			
		}
		else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
			//Toast.makeText(context, "BT Connected!", Toast.LENGTH_LONG).show();
			isBTConnected = true;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Log.d("S@urav","Inside ACL_Connected");
			Toast.makeText(context, "BT Connected!", Toast.LENGTH_SHORT).show();
			//MainActivity.mAm.setMode(AudioManager.MODE_IN_CALL);
			MainActivity.mAm.setBluetoothScoOn(true);
			MainActivity.mAm.startBluetoothSco();
			MainActivity.mAm.setSpeakerphoneOn(false);
		}
		else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
			Toast.makeText(context, "BT Disconnected", Toast.LENGTH_SHORT).show();
			//MainActivity.mAm.setMode(AudioManager.MODE_NORMAL);
			MainActivity.mAm.setBluetoothScoOn(false);
			MainActivity.mAm.stopBluetoothSco();
			MainActivity.mAm.setSpeakerphoneOn(true);
		}
		else if(action.equals(Intent.ACTION_PACKAGE_ADDED))
		{
			Toast.makeText(context, "Current Mode is:"+MainActivity.mAm.getMode(), Toast.LENGTH_LONG).show();
		}
	}

}
