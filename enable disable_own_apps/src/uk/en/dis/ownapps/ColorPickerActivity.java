package uk.en.dis.ownapps;

import afzkl.development.colorpickerview.view.*;
import afzkl.development.colorpickerview.view.ColorPickerView.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class ColorPickerActivity extends Activity implements OnColorChangedListener, View.OnClickListener
{

	private ColorPickerView			mColorPickerView;
	private ColorPanelView			mOldColorPanelView;
	private ColorPanelView			mNewColorPanelView;
	
	private Button					mOkButton;
	private Button					mCancelButton;
	
	
	
		private static final String LOGTAG = "colorPicker";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		
		setContentView(R.layout.activity_color_picker);
		
		init();
	}
	
	private void init() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int initialColor = prefs.getInt("color_3", 0xFF000000);
	
		mColorPickerView = (ColorPickerView) findViewById(R.id.color_picker_view);
		mOldColorPanelView = (ColorPanelView) findViewById(R.id.color_panel_old);
		mNewColorPanelView = (ColorPanelView) findViewById(R.id.color_panel_new);
		
		mOkButton = (Button) findViewById(R.id.okButton);
		mCancelButton = (Button) findViewById(R.id.cancelButton);
		
		
		((LinearLayout) mOldColorPanelView.getParent()).setPadding(
				mColorPickerView.getPaddingLeft(), 0, 
				mColorPickerView.getPaddingRight(), 0);
		
		
		mColorPickerView.setOnColorChangedListener(this);
		mColorPickerView.setColor(initialColor, true);
		mOldColorPanelView.setColor(initialColor);
		
		mOkButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		
	}

	@Override
	public void onColorChanged(int newColor) {
		mNewColorPanelView.setColor(mColorPickerView.getColor());		
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.okButton:			
			SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
			edit.putInt("color_3", mColorPickerView.getColor());
			edit.commit();
			Log.d(LOGTAG, "replace - with # then u got ur hex color \n "+mColorPickerView.getColor());
			Toast.makeText(this, "Set"+ mColorPickerView.getColor(), Toast.LENGTH_LONG).show();
			finish();			
			break;
		case R.id.cancelButton:
			Log.d(LOGTAG, "cancelled");
			Toast.makeText(this, "cancelled", Toast.LENGTH_LONG).show();
			finish();
			break;
		}
	}

}
