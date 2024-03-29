package com.petsasj.HyperDroidParts.ShakeToStart;

import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class Utilities {

	public static Context d;
	public static Intent pakgName;

	/*
	 * Get all installed application on mobile and return a list
	 * @param	c	Context of application
	 * @return	list of installed applications
	 */
	public static List<ApplicationInfo> getInstalledApplication(Context c) {
		return c.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
	}

	/*
	 * Launch an application
	 * @param	c	Context of application
	 * @param	pm	the related package manager of the context
	 * @param	pkgName	Name of the package to run
	 */
	public static boolean saveApp(Context c, PackageManager pm, String pkgName) {
		// query the intent for launching 
		Intent intent = pm.getLaunchIntentForPackage(pkgName);
		// if intent is available
		pakgName = (pm.getLaunchIntentForPackage(pkgName));
		d = c;
		if(intent != null) {
			try {
				// launch application
				//c.startActivity(intent);
				// if succeed
				return true;
				// if fail
			} catch(ActivityNotFoundException ex) {
				// quick message notification
				Toast toast = Toast.makeText(c, "Application Not Found", Toast.LENGTH_LONG);
				// display message
				toast.show();
			}
		}
		// by default, fail to launch
		return false;
	}
}
