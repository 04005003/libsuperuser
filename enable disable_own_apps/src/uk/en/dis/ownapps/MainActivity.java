package uk.en.dis.ownapps;

import afzkl.development.colorpickerview.view.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import eu.chainfire.libsuperuser.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;


public class MainActivity extends Activity 
{		
    //es file manager
	Button enable_button, disable_button;
	//
	ImageButton load_fb;
	//theme change
		private int currentTheme;
		private int oldTheme;
	//
	//custom color
		private ColorPanelView			
		mOldColorPanelView;
		//
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
			//theme change in prefs
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
			String lister = sharedPref.getString("theme_preference", "1");//1
			oldTheme = Integer.parseInt(lister);
			// Following options to change the Theme must precede setContentView().
			toggleTheme();
			//
			openSUDialog();
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
	// load apps from here
	public void loadCustomApps()// change to loadCustomApps //changed from loadESApp as can add more checks into this
	{
		new Handler().postDelayed(new Runnable()
		{
			//check if app is on phone
			@Override
			public void run(){
				//added on check if on device
				if(checkes() == true){
					showESIcon("/data/app/com.estrongs.android.pop.cupcake-1.apk");
				}
				else {//=false

				}
				if(checkfb() == true){
						showFbIcon("/data/app/com.facebook.katana-1.apk");
				}
				else{
					
				}
				//end on check
			}
		},0);
	}
	public boolean checkes()
	{
		try
		{
			ApplicationInfo info= getPackageManager().getApplicationInfo(
			"com.estrongs.android.pop.cupcake", 0 );//-1
			
//			Intent intent = new Intent(this, MainActivity.class);
//			//startActivity(j);
//			String pkgName = intent.getData().getSchemeSpecificPart();
//			PackageManager pm = getPackageManager();
//			if(pkgName.equals("com.estrongs.android.pop.cupcake")){
//					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
//							Log.i("xixia", "default");
//                        }
//					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
//							Log.i("xixia", "enabled");
//                        }
//					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
//							Log.i("xixia", "disabled");
//                        }
//					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER){
//							Log.i("xixia", "disabled-user");
//                        }
//                }
			
			
			
			/*ApplicationInfo ai = 
			getPackageManager().getApplicationInfo("your_package",0);*/

			//boolean appStatus = info.enabled;
			//boolean appStatus = ai.disabled;
			
			//add next app same as above but different package
			//application facebook
			//ApplicationInfo fb= getPackageManager().getApplicationInfo(
			//"com.facebook.katana",0);//com.facebook.katana.platform.FacebookAuthenticationActivity
			
			/*String pkgName = intent.getData().getSchemeSpecificPart();
            PackageManager pm = getLauncher().getPackageManager();
			if(pkgName.equals("com.jiawa.xixia")){
					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
							Log.i("xixia", "default");
                        }
					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
							Log.i("xixia", "enabled");
                        }
					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
							Log.i("xixia", "disabled");
                        }
					if(pm.getApplicationEnabledSetting(pkgName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER){
							Log.i("xixia", "disabled-user");
                        }
                }*/
			return true;

		}
		
		catch(PackageManager.NameNotFoundException e ){
			//ApplicationInfo info= getPackageManager().getApplicationInfo("test.paint", 0 );
			return false;
			
		}
		
	}
	
	public boolean checkfb()
	{
		try
		{
			//ApplicationInfo info= getPackageManager().getApplicationInfo(
			//"com.estrongs.android.pop.cupcake", 0 );//-1
			//add next app same as above but different package
			//application facebook
			ApplicationInfo fb= getPackageManager().getApplicationInfo(
			"com.facebook.katana",0);//com.facebook.katana.platform.FacebookAuthenticationActivity
			return true;

		}

		catch(PackageManager.NameNotFoundException e ){
			//ApplicationInfo info= getPackageManager().getApplicationInfo("test.paint", 0 );
			return false;
		}
	}
	//
	//es file manager
	
	
	private  void showESIcon (String apkPath) {
		String PATH_PackageParser = "android.content.pm.PackageParser";
		String PATH_AssetManager = "android.content.res.AssetManager";
		try {
			Class pkgParserCls = Class.forName(PATH_PackageParser);
			Class [] typeArgs = new Class [1];
			typeArgs [0] = String.class;
			Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
			Object [] valueArgs = new Object [1];
			valueArgs [0] = apkPath;
			Object pkgParser = pkgParserCt.newInstance(valueArgs);   
			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();
			typeArgs = new Class [4]; typeArgs [0] = File.class; typeArgs [1] = String.class; typeArgs [2] = DisplayMetrics.class; typeArgs [3] = Integer.TYPE;
			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage", typeArgs);
			valueArgs = new Object [4];
			valueArgs [0] = new File (apkPath);
			valueArgs [1] = apkPath;
			valueArgs [2] = metrics;
			valueArgs [3] = 0;
			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);
			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");
			ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);

			Class assetMagCls = Class.forName(PATH_AssetManager);
			Constructor assetMagCt = assetMagCls.getConstructor((Class []) null);
			Object assetMag = assetMagCt.newInstance((Object []) null);
			typeArgs = new Class [1];
			typeArgs [0] = String.class;
			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath", typeArgs);
			valueArgs = new Object [1];
			valueArgs [0] = apkPath;
			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
			Resources res = getResources();
			typeArgs = new Class [3];
			typeArgs [0] = assetMag.getClass ();
			typeArgs [1] = res.getDisplayMetrics ().getClass ();
			typeArgs [2] = res.getConfiguration ().getClass ();
			Constructor resCt = Resources.class.getConstructor(typeArgs); //Class

			valueArgs = new Object [3];
			valueArgs [0] = assetMag;
			valueArgs [1] = res.getDisplayMetrics ();
			valueArgs [2] = res.getConfiguration ();
			res = (Resources) resCt.newInstance(valueArgs);
			// this displays our apps label name that is shown in launcher
			CharSequence label = null;
			 if (info.labelRes!= 0) {
			 label = res.getText (info.labelRes);
			 TextView estext = (TextView) findViewById(R.id.esLabel);
			 estext.setText(" "+ label);
					 estext.setVisibility (View.VISIBLE);
			 }
			 //fink dont beed as its localisation 
			/* if(label== null){   
			 label = (info.nonLocalizedLabel!= null)?
			 info.nonLocalizedLabel:
			 info.packageName;
			 }   */
			 //this displays app icon that were using 
			if (info.icon!= 0) {
				Drawable icon = res.getDrawable (info.icon);
				ImageView image = (ImageView) findViewById (R.id.es_icon);
				image.setVisibility (View.VISIBLE);
				image.setImageDrawable (icon);
				image.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent fbload = new Intent
						(Intent.ACTION_MAIN);
						fbload.setComponent(new ComponentName(//"com.facebook.katana","com.facebook.katana.platform.FacebookAuthenticationActivity"
						 "com.estrongs.android.pop.cupcake","com.estrongs.android.pop.cupcake.view.FileExplorerActivity"
						));
						startActivity(fbload);
					}
				});
				
				//removed esbuttons and placed them here to show if app is installed
					Button enable_button = (Button)findViewById(R.id.enable_es);
					enable_button.setText("enable");//R.string.disable_es);
					enable_button.setVisibility (View.VISIBLE);
					enable_button.setOnClickListener(new View.OnClickListener(){
								@Override
								public void onClick(View v) {
										/*PackageManager manager = getPackageManager();

										 //SharedPreferences.Editor edit = sp.edit();
										 //edit.putBoolean("integralLauncher", true);
										 manager.setComponentEnabledSetting(new ComponentName(MainActivity.this,"com.estrongs.android.pop.cupcake.view.FileExplorerActivity"), 
										 PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
										 */
										rootSession.addCommand(new String[]{
																	   //this is ES file manager that packageNAME
																	   "pm enable com.estrongs.android.pop.cupcake"
																   });
										//finish();// tgis is my test for my gif image 
									}
							});
					Button disable_button = (Button)findViewById(R.id.disable_es);
					disable_button.setVisibility (View.VISIBLE);
					disable_button.setText("disable");//R.string.disable_es);
					disable_button.setOnClickListener(new View.OnClickListener(){
								@Override
								public void onClick(View v) {
										/*PackageManager manager = getPackageManager();

										 //SharedPreferences.Editor edit = sp.edit();

										 //edit.putBoolean("integralLauncher", true);

										 //edit.putBoolean("integralLauncher", false);
										 manager.setComponentEnabledSetting(new ComponentName(MainActivity.this,"com.estrongs.android.pop.cupcake.view.FileExplorerActivity"),
										 PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);//, PackageManager.DONT_KILL_APP);
										 */
										rootSession.addCommand(new String[]{
																	   "pm disable com.estrongs.android.pop.cupcake"
																   });
										//finish();
									}
							});
				
				
			}
		}
		catch(Exception e) {
			e.printStackTrace ();
		}
	}
	
	//end es file manager 
	//load fackebook icon n label
		private  void showFbIcon (String apkPath) {
				String PATH_PackageParser = "android.content.pm.PackageParser";
				String PATH_AssetManager = "android.content.res.AssetManager";
				try {
						Class pkgParserCls = Class.forName(PATH_PackageParser);
						Class [] typeArgs = new Class [1];
						typeArgs [0] = String.class;
						Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
						Object [] valueArgs = new Object [1];
						valueArgs [0] = apkPath;
						Object pkgParser = pkgParserCt.newInstance(valueArgs);   
						DisplayMetrics metrics = new DisplayMetrics();
						metrics.setToDefaults();
						typeArgs = new Class [4]; typeArgs [0] = File.class; typeArgs [1] = String.class; typeArgs [2] = DisplayMetrics.class; typeArgs [3] = Integer.TYPE;
						Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage", typeArgs);
						valueArgs = new Object [4];
						valueArgs [0] = new File (apkPath);
						valueArgs [1] = apkPath;
						valueArgs [2] = metrics;
						valueArgs [3] = 0;
						Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);
						Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");
						ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);

						Class assetMagCls = Class.forName(PATH_AssetManager);
						Constructor assetMagCt = assetMagCls.getConstructor((Class []) null);
						Object assetMag = assetMagCt.newInstance((Object []) null);
						typeArgs = new Class [1];
						typeArgs [0] = String.class;
						Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath", typeArgs);
						valueArgs = new Object [1];
						valueArgs [0] = apkPath;
						assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
						Resources res = getResources();
						typeArgs = new Class [3];
						typeArgs [0] = assetMag.getClass ();
						typeArgs [1] = res.getDisplayMetrics ().getClass ();
						typeArgs [2] = res.getConfiguration ().getClass ();
						Constructor resCt = Resources.class.getConstructor(typeArgs); //Class

						valueArgs = new Object [3];
						valueArgs [0] = assetMag;
						valueArgs [1] = res.getDisplayMetrics ();
						valueArgs [2] = res.getConfiguration ();
						res = (Resources) resCt.newInstance(valueArgs);
						// this displays our apps label name that is shown in launcher
						CharSequence label = null;
						if (info.labelRes!= 0) {
								label = res.getText (info.labelRes);
								TextView estext = (TextView) findViewById(R.id.fbLabel);
								estext.setText(" "+ label);
							}
						/*if(label== null){   
								label = (info.nonLocalizedLabel!= null)?
									info.nonLocalizedLabel:
									info.packageName;
							}   */
						//this displays app icon that were using 
						if (info.icon!= 0) {
								Drawable icon = res.getDrawable (info.icon);
								//ImageView image = (ImageView) findViewById (R.id.fb_icon);
								ImageButton image = (ImageButton) findViewById (R.id.fb_icon);
								image.setVisibility (View.VISIBLE);
								image.setImageDrawable (icon);
								image.setOnClickListener(new View.OnClickListener(){
									@Override
									public void onClick(View v) {
										/*ActionBar actionBar = getActionBar();
										actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);//, ActionBar.DISPLAY_USE_LOGO); 
										actionBar.setDisplayShowTitleEnabled(true); 
										actionBar.setDisplayUseLogoEnabled(false);	
										actionBar.setDisplayHomeAsUpEnabled(false); 
										actionBar.setTitle("test");*/
										Intent fbload = new Intent
										(Intent.ACTION_MAIN);
											fbload.setComponent(new ComponentName("com.facebook.katana","com.facebook.katana.platform.FacebookAuthenticationActivity"));
											startActivity(fbload);
									}
								});
							}
					}
				catch(Exception e) {
						e.printStackTrace ();
					}
			}
		/*public void FBButtons(){
				//((Button)findViewById(R.id.enable_fb)).
				Button enable_button = (Button)findViewById(R.id.enable_fb);
				enable_button.setText("enable");//R.string.disable_es);
				enable_button.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View v) {
									rootSession.addCommand(new String[]{
																   //this is ES file manager that packageNAME
																""  // "pm enable com.estrongs.android.pop.cupcake"
															   });
									finish();// tgis is my test for my gif image 
								}
						});
				Button disable_button = (Button)findViewById(R.id.disable_fb);
				disable_button.setText("disable");//R.string.disable_fb);
				disable_button.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View v) {
									rootSession.addCommand(new String[]{
																  // "pm disable com.estrongs.android.pop.cupcake",
																   ""
															   });
									finish();
								}
						});
			}*/
	//end of facebook
	
	//added menu for preference screen
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){

			case (R.id.help):
			// Actions for Help page
			Intent i = new Intent(this, ColorPickerActivity.class);
			startActivity(i);
			return true;

			/*case(R.id.about):
			// Actions for About page
			Intent k = new Intent(this, Options.class);
			startActivity(k);
			return true;*/

			case(R.id.prefs):
			// Actions for preferences page
			Intent j = new Intent(this, Prefs.class);
			startActivity(j);
			return true;

			case (R.id.quit):
					/*ActionBar actionBar = getActionBar();
					actionBar.hide();*/
					/*ActionBar actionBar = getActionBar();
					actionBar.show();*/
			//finishUp();
			return true;
		}
		return false;
	}

	//change theme
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume(){
		super.onResume();
		toggleTheme();
	}
	public void toggleTheme()
	{
		// Following options to change the Theme must precede setContentView().
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

		String lister = sharedPref.getString("theme_preference", "1");


		currentTheme = Integer.parseInt(lister);
		if(currentTheme == 1 )
		{
			setTheme(R.style.HoloLightCustom);
			//setContentView(R.layout.activity_main);
			//openSUDialog();
			//loadESApp();
			//listpref();
			setContentView(R.layout.activity_main);
			

			loadCustomApps();//changed from loadEsApp//changing to loadCustomApps();
		}
		else if(currentTheme == 2){
			setTheme(R.style.HoloCustom);
				setContentView(R.layout.activity_main);


				loadCustomApps();//changed from loadEsApp//changing to loadCustomApps();
		}
		else if(currentTheme == 3){
			setTheme(R.style.wallpaper);
			setContentView(R.layout.activity_main2);
				loadCustomApps();//changed from loadEsApp//changing to loadCustomApps();
				/*if(Build.VERSION.SDK_INT < 19){
						FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
						ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
						layoutParams.height = 0;
					}
				if(Build.VERSION.SDK_INT >= 21){
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);;
					}		*/
		}
		else if(currentTheme == 4){
				setTheme(R.style.wallpaperRed);
				setContentView(R.layout.activity_main2);
				loadCustomApps();//changed from loadEsApp//changing to loadCustomApps();
		}
		else if(currentTheme == 5){
				setTheme(R.style.wallpaperPink);
				setContentView(R.layout.activity_main2);
				loadCustomApps();//changed from loadEsApp//changing to loadCustomApps();
		}
		//custom color theme
		else if(currentTheme == 6){
				setTheme(R.style.wallpaper2);
				setContentView(R.layout.readcolor);
				loadCustomApps();//changed from loadEsApp//changing to loadCustomApps();
				//init();
				// added the custom colorview bit 
				SharedPreferences color = PreferenceManager.getDefaultSharedPreferences(this);
				int initialColor = color.getInt("color_3", 0xFF000000 );
				mOldColorPanelView = (ColorPanelView) findViewById(R.id.color_panel_old);
				mOldColorPanelView.setColor(initialColor);
				
				ActionBar actionBar = getActionBar();
				actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);//, ActionBar.DISPLAY_USE_LOGO); 
				actionBar.setDisplayShowTitleEnabled(true); 
				actionBar.setDisplayUseLogoEnabled(false);	
				actionBar.setDisplayHomeAsUpEnabled(false); 
				actionBar.setTitle("Hidden apps");
		}
		
		if(oldTheme != currentTheme){

			oldTheme = currentTheme;

			Intent k = new Intent(this, MainActivity.class);
			k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(k);
				//Intent m = new Intent(this, Prefs.class);
				//m.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
	}
	public void finishUp(){
		finish();
	}
	
	
	
	
}
