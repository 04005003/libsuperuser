package uk.en.dis.ownapps;

import android.app.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import eu.chainfire.libsuperuser.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;


public class MainActivity extends Activity {		

	Button enable_button, disable_button;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		openSUDialog();
		
		loadESApp();
		
        //((Button)findViewById(R.id.enable_es)).
		Button enable_button = (Button)findViewById(R.id.enable_es);
		enable_button.setText("enable");//R.string.disable_es);
        enable_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				rootSession.addCommand(new String[]{
					//this is ES file manager that packageNAME
					"pm enable com.estrongs.android.pop.cupcake"
				});
				finish();
			}
		});
	
		Button disable_button = (Button)findViewById(R.id.disable_es);
        disable_button.setText("disable");//R.string.disable_es);
        disable_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				rootSession.addCommand(new String[]{
					"pm disable com.estrongs.android.pop.cupcake",
				});
				finish();
			}
		});
		
	}
	
	private static Shell.Interactive rootSession;
	private void reportError(String error){
		List<String> errorInfo = new ArrayList<String>();
		errorInfo.add(error);
		rootSession = null;
	}
	private void openSUDialog(){
		if (rootSession != null){
			//SU();
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
	/*private void SU()
	{
		rootSession.addCommand(new String[]
		{
			"su"
		});
	}*/
	
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
			 }
			 if(label== null){   
			 label = (info.nonLocalizedLabel!= null)?
			 info.nonLocalizedLabel:
			 info.packageName;
			 }   
			 //this displays app icon that were using 
			if (info.icon!= 0) {
				Drawable icon = res.getDrawable (info.icon);
				ImageView image = (ImageView) findViewById (R.id.es_icon);
				image.setVisibility (View.VISIBLE);
				image.setImageDrawable (icon);
			}
		}
		catch(Exception e) {
			e.printStackTrace ();
		}
	}
	
	public void loadESApp(){
		new Handler().postDelayed(new Runnable(){
			//this is wer it starts
			//check
			@Override
			public void run(){
				//added on check if on device
				if(check() == true){
					//finish();
					showESIcon("/data/app/com.estrongs.android.pop.cupcake-1.apk");
				}
				else {//=false
				
				}
				//end on check
			}

		},0);
	}

	public boolean check()
	{
		try
		{
			ApplicationInfo info= getPackageManager().getApplicationInfo("com.estrongs.android.pop.cupcake", 0 );//-1
			return true;
		}
		catch(PackageManager.NameNotFoundException e ){
			//ApplicationInfo info= getPackageManager().getApplicationInfo("test.paint", 0 );

			return false;
		}
	}



}
