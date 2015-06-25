package com.example.soundamplitudetestapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	public static AudioManager mAm;
	private MyReceiver receiver;
	public String duration;
	private IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG); 
	IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
	IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
	IntentFilter filter3 = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
	IntentFilter filter4 = new IntentFilter(Intent.ACTION_PACKAGE_INSTALL);
	public static Context ctx;
	Button btn1;  
	Button btn2;  
	Button btn3;  
	Button btn4;  
	Button btn5;  
	AlertSoundPool spool = new AlertSoundPool();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		receiver = new MyReceiver();
		registerReceiver(receiver, receiverFilter);
		this.registerReceiver(receiver, filter1);
		this.registerReceiver(receiver, filter2);
		this.registerReceiver(receiver, filter3);
		this.registerReceiver(receiver, filter4);
		btn1 = (Button) findViewById(R.id.button1);
		/*btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);*/
		ctx = getApplicationContext();
		mAm = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		//mAm.setMode(AudioManager.MODE_IN_CALL);
		/*mAm.setMode(AudioManager.MODE_IN_CALL);
		mAm.setSpeakerphoneOn(false);*/

		spool.load();

		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spool.playsound(AlertSoundPool.soundId100);
				/*Toast.makeText(getApplicationContext(), "Duration:"+duration, Toast.LENGTH_SHORT).show();*/
			}
		});
		/*btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spool.playsound(AlertSoundPool.soundId40);
			}
		});
		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spool.playsound(AlertSoundPool.soundId50);
			}
		});
		btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spool.playsound(AlertSoundPool.soundId60);
			}
		});
		btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spool.playsound(AlertSoundPool.soundId70);
			}
		});*/

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//unregisterReceiver(receiver);
		mAm.setMode(AudioManager.MODE_NORMAL);
		mAm.setSpeakerphoneOn(true);
	}
}
