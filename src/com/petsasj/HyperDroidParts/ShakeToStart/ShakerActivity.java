package com.petsasj.HyperDroidParts.ShakeToStart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class ShakerActivity extends Activity implements ShakerAdaptor.Callback {

	Intent wtf1;
	String clazz = "";
	String dudepakg = "";
	
	PackageManager pm;
	private ShakerAdaptor shaker = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shaker = new ShakerAdaptor(this, 1.50d, 500, this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		shaker.close();		
	}
	
	public void shakingStarted() {
		Log.d("ShakerDemo", "Shaking started!");
		Intent i1 = new Intent();
		SharedPreferences shakeToStartPrefs = this.getSharedPreferences("shakeToStart", MODE_WORLD_READABLE);
		String b = shakeToStartPrefs.getString("ichname", "");
		try {
			if ((Utilities.d != null)||(Utilities.pakgName != null)) {
				Utilities.d.startActivity(Utilities.pakgName);
				i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	
				finish();
			//} else if (String.valueOf(b) != null) {
				//wtf1 = Intent.getIntent(b);
				//startActivity(wtf1);
				//Log.i("ShakerDemo", "Successfully got intent from shared_prefs");
				//finish();
			} else { //if (String.valueOf(b).equals("")) {
				//SharedPreferences shakeToStartPrefs = this.getSharedPreferences("shakeToStart", MODE_WORLD_READABLE);
				String dudepakg = shakeToStartPrefs.getString("package", "");
				String clazz = shakeToStartPrefs.getString("class", "");
				Intent intent = new Intent().setClassName(dudepakg, clazz);
				startActivity(intent);
				finish();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Have you selected an app from the list?",Toast.LENGTH_SHORT).show();
			Log.d("ShakerDemo", "Shaking FAILED!!!!");
		}
	}

	public void shakingStopped() {
		Log.d("ShakerDemo", "Shaking stopped!");
	}
}