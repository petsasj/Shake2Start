package com.petsasj.HyperDroidParts.ShakeToStart;

import com.petsasj.HyperDroidParts.*;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AppActivity extends Activity {

	String ichname = "";
	private ListView mListAppInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout for the main screen
		setContentView(R.layout.listview);        
		// load list application
		mListAppInfo = (ListView) findViewById(android.R.id.list);
		// create new adapter
		AppInfoAdapter adapter = new AppInfoAdapter(this, Utilities.getInstalledApplication(this), getPackageManager());
		// set adapter to list view
		mListAppInfo.setAdapter(adapter);
		// implement event when an item on list view is selected
		mListAppInfo.setOnItemClickListener(new OnItemClickListener() {
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// get the list adapter
				AppInfoAdapter appInfoAdapter = (AppInfoAdapter)parent.getAdapter();
				// get selected item on the list
				ApplicationInfo appInfo = (ApplicationInfo)appInfoAdapter.getItem(pos);
				// put the correct intents
				Utilities.saveApp(parent.getContext(), getPackageManager(), appInfo.packageName);
				try {
					// Save to shared Prefs
					savePrefs();
					Toast.makeText(getApplicationContext(), "Application saved! Shake2Start, baby!",Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					// if application is not launchable
					Toast.makeText(getApplicationContext(), "This Application doesn't seem Launchable, please select other",Toast.LENGTH_SHORT).show();
					Log.d ("AppActivity", "SavePrefs failed");
				}
			}
		});
	}

	public void savePrefs() {
		ichname = (Utilities.pakgName).toURI();
		SharedPreferences profileGroupPrefs = getSharedPreferences("shakeToStart", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = profileGroupPrefs.edit();
		prefsEditor.putString("ichname", ichname);
		prefsEditor.commit();
	}
}