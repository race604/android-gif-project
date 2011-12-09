package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.ui.GIFView;

public class GifActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GIFView(this,Environment.getExternalStorageDirectory()+"/girl.gif"));
	}
}
