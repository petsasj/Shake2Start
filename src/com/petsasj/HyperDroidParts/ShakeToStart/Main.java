package com.petsasj.HyperDroidParts.ShakeToStart;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Main extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
}