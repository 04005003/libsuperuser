package uk.en.dis.ownapps;

import android.app.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class about extends Activity {

		WebView myBrowser, changelog;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.about);

				Button changeLog = (Button) findViewById (R.id.ChangeLog);
				//changLog.setVisibility (View.VISIBLE);
				//changLog.setImageDrawable (icon);
				changeLog.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View v) {
									myBrowser = (WebView)findViewById(R.id.mybrowser);
									myBrowser.setVisibility(View.VISIBLE);
									myBrowser.loadUrl("file:///android_asset/changelog.html");
									/* myBrowser.canZoomIn();
									 myBrowser.canZoomOut();*/
									// myBrowser.zoomBy(20);
									myBrowser.zoomIn();
									myBrowser.zoomOut();
									Button close = (Button)findViewById(R.id.Close);
									close.setVisibility(View.VISIBLE);
									close.setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View v) {
														myBrowser = (WebView)findViewById(R.id.mybrowser);
														myBrowser.setVisibility(View.GONE);
														Button close = (Button)findViewById(R.id.Close);
														close.setVisibility(View.GONE);
													}
											});
								}

						});





				/*changelog = (WebView)findViewById(R.id.changelog);

				 changelog.loadUrl("file:///android_asset/mypage.html");*/
			}
	}
