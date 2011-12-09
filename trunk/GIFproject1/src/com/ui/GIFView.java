package com.ui;

import com.main.R;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class GIFView extends View implements Runnable {
	private Bitmap bmb;
	private GIFDecode decode;
	private int ind;
	private int gifCount;

	public GIFView(Context context,String path) {
		super(context);
		decode = new GIFDecode();
		decode.read(path);
		ind = 0;
		// decode.
		gifCount = decode.getFrameCount();
		bmb = decode.getFrame(0);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmb, 0, 0, new Paint());
		bmb = decode.next();
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.postInvalidate();
				Thread.sleep(100);
			} catch (Exception ex) {

			}
		}

	}

}
