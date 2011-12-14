package com.stay.main;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.stay.gif.GifDecode;
import com.stay.gif.GifEncoder;
import com.stay.test.DebugUtil;

/**
 * @Description: this demo is to support gif decode and encode in Android phone 
 *	you can see {@link GifDecode} and {@link GifEncoder} how it works
 * @author Stay
 *
 * @Date 2011-12-13 下午06:12:22
 * 
 */
public class GifMain extends Activity implements OnClickListener {
	protected static final int ENCODE = 0;
	protected static final int DECODE = 1;
	private int[] array = new int[] { R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3, R.drawable.girl_4,
			R.drawable.girl_5, R.drawable.girl_6, R.drawable.girl_7, R.drawable.girl_8, R.drawable.girl_9, R.drawable.girl_10,
			R.drawable.girl_11 };
//	private int[] array = new int[] { R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3, R.drawable.girl_4};
	private Button decodeBtn;
	private Button encodeBtn;

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
				startActivity(new Intent(GifMain.this,GifDisplayActivity.class));
				break;
			default:
				break;
			}

		}
	};

	private void encode() {
		GifEncoder giffle = new GifEncoder();
		giffle.encode(this, array, 500, Environment.getExternalStorageDirectory()+"/girl.gif");
		DebugUtil.toast(this, "finish");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.encode:
			mHandler.sendEmptyMessage(ENCODE);
			break;
		case R.id.decode:
			mHandler.sendEmptyMessage(DECODE);
			break;
		default:
			break;
		}
	}
}