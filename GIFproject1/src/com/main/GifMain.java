package com.main;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.stay.gif.GifEncoder;
import com.ui.DebugUtil;

public class GifMain extends Activity implements OnClickListener {
	protected static final int ENCODE = 0;
	protected static final int DECODE = 1;
	private int[] array = new int[] { R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3, R.drawable.girl_4,
			R.drawable.girl_5, R.drawable.girl_6, R.drawable.girl_7, R.drawable.girl_8, R.drawable.girl_9, R.drawable.girl_10,
			R.drawable.girl_11 };
//	private int[] array = new int[] { R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3, R.drawable.girl_4};
	private int i = 0;
	private Button decodeBtn;
	private Button encodeBtn;
//	09-15 12:06:36.894: ERROR/InputDispatcher(163): channel '408d2d18 com.main/com.main.GifMain (server)' ~ Consumer closed input channel or an error occurred.  events=0x8

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		encodeBtn= (Button) this.findViewById(R.id.encode);
		encodeBtn.setOnClickListener(this);
		decodeBtn= (Button) this.findViewById(R.id.decode);
		decodeBtn.setOnClickListener(this);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ENCODE:
				encode();
				break;
			case DECODE:
				
				break;
			default:
				break;
			}

		}
	};

	private void encode() {
//		// try {
//		AnimatedGifEncoder1 e = new AnimatedGifEncoder1();
//		e.setRepeat(0);
//		e.start(Environment.getExternalStorageDirectory() + "/girl.gif");
////		for (int i = 0; i < array.length; i++) {
////			e.setDelay(500); // 1 frame per sec
////			e.addFrame(BitmapFactory.decodeResource(getResources(), array[i++]));
////		}
//		e.addFrame(BitmapFactory.decodeResource(getResources(), R.drawable.girl_1));
//		e.finish();
		GifEncoder giffle = new GifEncoder();
//		Bitmap[] bitmaps = new Bitmap[array.length];
//		for (int i = 0; i < array.length; i++) {
//			bitmaps[i] = BitmapFactory.decodeResource(getResources(), array[i++]);
//		}
		giffle.encode(this, array, 20, Environment.getExternalStorageDirectory()+"/girl.gif");
		DebugUtil.toast(this, "finish");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.encode:
			mHandler.sendEmptyMessage(ENCODE);
			break;
		case R.id.decode:
			DebugUtil.debug("decode");
			startActivity(new Intent(GifMain.this,GifActivity.class));
			break;
		default:
			break;
		}
	}
}