package com.example.soundamplitudetestapp;


import java.util.Iterator;
import java.util.Set;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;


@SuppressLint("NewApi")
public class AlertSoundPool implements SoundPool.OnLoadCompleteListener {
	Handler tempHandler = new Handler();
	public static int soundId100;
	/*public static int soundId40;
	  public static int soundId50;
	  public static int soundId60;
	  public static int soundId70;*/
	public Boolean accessoryconnected = false;
	String duration;

	SoundPool spool = new SoundPool(1,AudioManager.STREAM_NOTIFICATION, 0);
	void playsound(int soundId)
	{
		float vol = (float) ((float)MainActivity.mAm.getStreamVolume(AudioManager.STREAM_NOTIFICATION) / (float) MainActivity.mAm
				.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
		Log.d("12345"," Volume is:"+vol);
		routeAudio();
		spool.play(soundId, vol, vol,
				1, 0, (float) 1.0);
		tempHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				//Toast.makeText(MainActivity.ctx, "Resetting the values!", Toast.LENGTH_LONG).show();
				//Reseting everything 
				restoreAudio();

			}
		}, Integer.parseInt(duration));

		//Toast.makeText(MainActivity.ctx, "Duration is:"+duration, Toast.LENGTH_LONG).show();
	}
	void load()
	{
		spool.setOnLoadCompleteListener(this);
		soundId100 = spool.load(MainActivity.ctx, R.raw.ambience, 1);
		GetAudioDuration();
		/* soundId40 = spool.load(MainActivity.ctx, R.raw.a40, 1);
	        soundId50 = spool.load(MainActivity.ctx, R.raw.a50, 1);
	        soundId60 = spool.load(MainActivity.ctx, R.raw.a60, 1);
	        soundId70 = spool.load(MainActivity.ctx, R.raw.a70, 1);*/
	}
	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		// TODO Auto-generated method stub
		Log.d("12345","Sound for Alert Loaded");
	}
	public void routeAudio()
	{
		isBTConnected();
		Log.d("12345","Do Routing..........isBTConnected"+ MyReceiver.isBTConnected);

		if(MyReceiver.isWiredHeadsetOn )
		{
			MainActivity.mAm.setSpeakerphoneOn(false);
			Log.d("12345","Wired Headse:t"+ MyReceiver.isWiredHeadsetOn);
		}
		else if(MyReceiver.isBTConnected )
		{
			Log.d("12345","BT Status "+ MyReceiver.isBTConnected);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MainActivity.mAm.setMode(AudioManager.MODE_IN_CALL);
			MainActivity.mAm.setBluetoothScoOn(true);
			MainActivity.mAm.startBluetoothSco();
			MainActivity.mAm.setSpeakerphoneOn(false);
		}
		else
		{
			Log.d("12345","Need to Route to Loudspeaker!");
			MainActivity.mAm.setMode(AudioManager.MODE_NORMAL);
		}
	}
	private void GetAudioDuration() {

		try {

			MediaMetadataRetriever retrver = new MediaMetadataRetriever();
			retrver.setDataSource(MainActivity.ctx, Uri.parse("android.resource://com.example.soundamplitudetestapp/" +/* resId*/R.raw.ambience));
			duration = retrver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
			retrver.release();
			Log.d("12345","Duration of the audio is:"+ duration);
		} catch (Exception e) {

			e.printStackTrace();
		}

		//}
	}
	public void restoreAudio()
	{
		Log.d("12345","Restoring the Audio!");
		MainActivity.mAm.setBluetoothScoOn(false);
		MainActivity.mAm.stopBluetoothSco();
		MainActivity.mAm.setSpeakerphoneOn(true);
		MainActivity.mAm.setMode(AudioManager.MODE_NORMAL);
		//MyReceiver.isBTConnected = false;
		//MyReceiver.isWiredHeadsetOn = false;
	}
	private void isBTConnected()
	{
		BluetoothManager bMgr = (BluetoothManager) MainActivity.ctx.getSystemService(Context.BLUETOOTH_SERVICE);
		Set<BluetoothDevice> bDevices = bMgr.getAdapter().getDefaultAdapter().getBondedDevices();
		Log.i("123456", "bDevices Size is :"+bDevices.size());
		Iterator it = bDevices.iterator();
		if(it.hasNext()){
			BluetoothDevice bt = (BluetoothDevice) it.next();
			String name =bt.getName();
			int state = bt.getBondState();
			Log.i("123456", "BT device :"+name+"  state :"+state);
		}
		if(bDevices.size() > 0)
		{
			/*Bt should be paired in the beginning for this logic to work! 
			**Sending a blind true even if a BT device is connected even if not paired
			*Need to write BT service connection logic later*/
			MyReceiver.isBTConnected = true;
			//return true;
		}
		else 
		{
			MyReceiver.isBTConnected = false;
			//return false;
		}
	}
}
