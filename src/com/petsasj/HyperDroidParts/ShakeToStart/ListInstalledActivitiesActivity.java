package com.petsasj.HyperDroidParts.ShakeToStart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.petsasj.HyperDroidParts.*;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListInstalledActivitiesActivity extends ListActivity {
	
	public static String wtfbbq;
	public static String wtfnoes;
	
	// Buffer used to store package and class information, and also determine the number of installed activities
	private ArrayList<String[]> _activitiesBuffer = null;
	
	// Buffers for package and class information
	private String[] _packages = null;
	private String[] _classes = null;
	
	// Index used to fill buffers
	private int _index = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get all installed activities (package and class information for every activity)
        getAllInstalledActivities();              
        
        // Set content to GUI
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, _classes));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        // Add listener
        lv.setOnItemClickListener(new OnItemClickListener() {
        	
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            
        		// When clicked, show a toast with the selected activity
        		/*Toast.makeText(
        			getApplicationContext(), 
        			((TextView) view).getText(), 
        			Toast.LENGTH_SHORT).show();*/
        		
        		// When clicked, start selected activity, if allowed or possible
        		try {
        			wtfbbq = (_packages[position]);
        			wtfnoes = (_classes[position]);
        			savePrefs();
        			Toast.makeText(getApplicationContext(), "Saved, Shake2Start.", Toast.LENGTH_SHORT);
        		} catch (Exception e) {
        			Toast.makeText(getApplicationContext(), "Unable to start selected application.", Toast.LENGTH_SHORT);
        		}
        		
          } // public void onItemClick(AdapterView<?> parent, View view, int position, long id)

        });
        
    } // public void onCreate(Bundle savedInstanceState)

    /*
     * Get all installed activities
     */
	private void getAllInstalledActivities() {
		
		// Initialize activities buffer
		_activitiesBuffer = new ArrayList<String[]>();
	
		// Get installed packages
		List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
		
		// Get activities for every package
		for (PackageInfo packageInfo : installedPackages) {
	
			// Get activities for current package
			ActivityInfo[] activities = packageInfo.activities;
			
			if (activities != null) {
				
				// For every activity save package and class information
				for (ActivityInfo activityInfo : activities) {
					
					String[] buf = new String[2];
					
					buf[0] = activityInfo.packageName;
					buf[1] = activityInfo.name;
									
					_activitiesBuffer.add(buf);
							
				} // for (ActivityInfo activityInfo : activities)
				
			} // if (activities != null)
			
		} // for (PackageInfo packageInfo : installedPackages)
				
		_packages = new String[_activitiesBuffer.size()];
        _classes = new String[_activitiesBuffer.size()];
        
        Iterator<String[]> iterator = _activitiesBuffer.iterator();
        while (iterator.hasNext()) {
        	
        	String[] buf = iterator.next();
        	
        	// Store package information
        	_packages[_index] = buf[0]; 
        	
        	// Store class information
        	_classes[_index] = buf[1];
        	
        	_index++;
        	
        } // while (iterator.hasNext())
       		
	} // private void getAllInstalledActivities()
	
	public void savePrefs() {
		//activitiesBuffer.ichname = (Utilities.pakgName).toURI();
		SharedPreferences profileGroupPrefs = getSharedPreferences("shakeToStart", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = profileGroupPrefs.edit();
		prefsEditor.putString("package", wtfbbq);
		prefsEditor.putString("class", wtfnoes);
		prefsEditor.commit();
	}	
}