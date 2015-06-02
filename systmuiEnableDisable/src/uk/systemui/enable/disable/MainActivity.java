package uk.systemui.enable.disable;


import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import eu.chainfire.libsuperuser.*;
import java.util.*;




public class MainActivity extends Activity 
	{		
		//es file manager
		Button enable_button, disable_button;
		//
		//ImageButton load_fb;
		//theme change
		/*private int currentTheme;
		private int oldTheme;*/
		//
		//custom color
		
		//
		@Override
		public void onCreate(Bundle savedInstanceState){
				super.onCreate(savedInstanceState);
				
				setContentView(R.layout.main);
				openSUDialog();
				ESButtons();
				
				
				final String PREFS_NAME = "MyPrefsFile";

				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

				if (settings.getBoolean("my_first_time", true)) {
						//the app is being launched for first time, do something        
						Log.d("Comments", "First time");

						// first time task
						Toast.makeText(getApplicationContext(), 
									   "first time", Toast.LENGTH_LONG).show();
						
						// record the fact that the app has been started at least once
						settings.edit().putBoolean("my_first_time", false).commit(); 
					}
				
				
			}


//	superuser grant dialog

		private static Shell.Interactive rootSession;
		
		private void reportError(String error){
				List<String> errorInfo = new ArrayList<String>();
				errorInfo.add(error);
				rootSession = null;
			}
		private void openSUDialog(){
				if (rootSession != null){
						rootSession.addCommand(new String[]{
													   "su"
												   });
					}
				else{
						rootSession = new Shell.Builder().useSU().setWantSTDERR(true).setWatchdogTimeout(1).setMinimalLogging(true).open(new Shell.OnCommandResultListener(){
									@Override
									public void onCommandResult(int commandCode, int exitCode, List<String> output){ 
											if (exitCode != Shell.OnCommandResultListener.SHELL_RUNNING){
													reportError("Error opening root shell: exitCode " + exitCode);
												}
											//else {}
										}
								});
					}
			}
// end superuser grant dialog
		
		//
		//es file manager

		public void ESButtons(){
				//((Button)findViewById(R.id.enable_es)).
				Button enable_button = (Button)findViewById(R.id.systemui_enable);
				//enable_button.setText("enable");//R.string.disable_es);
				enable_button.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View v) {
									rootSession.addCommand(new String[]{
																"su",   "am startservice --user 0 -n com.android.systemui/.SystemUIService"
															   });
									//finish();// tgis is my test for my gif image 
								}
						});
				Button disable_button = (Button)findViewById(R.id.systemui_disable);
				//disable_button.setText("disable");//R.string.disable_es);
				disable_button.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View v) {
									rootSession.addCommand(new String[]{
																 "su", "-c", "service call activity 42 s16 com.android.systemui",
															   });
									//finish();
								}
						});
			}
			
			
			
		
			
	}
