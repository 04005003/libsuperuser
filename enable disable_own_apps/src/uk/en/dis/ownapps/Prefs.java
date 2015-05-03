package uk.en.dis.ownapps;

//import java.util.List;

import android.os.*;
import android.preference.*;
import android.preference.PreferenceFragment;
//import android.preference.PreferenceManager;




public class Prefs extends PreferenceActivity// implements OnPreferenceClickListener, ColorPickerDialog.OnColorChangedListener 
{
		
	
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				
				addPreferencesFromResource(R.xml.fragmented_preferences );
				
		}
	
}
