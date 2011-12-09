package com.stay.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ui.DebugUtil;

public class GifEncoder {
	public Context mContext;
	static GifEncoder giffle = null;
	private int height;
	private int width;
	private int length;
	private int[] pixels;
	private Bitmap bitmap;
	private static final int COLOR = 256;
	private static final int QUALITY = 10;

	static {
		System.loadLibrary("gifflen");
	}

	public void encode(Context mContexts, int[] images, int delay, String name) {
		bitmap = BitmapFactory.decodeResource(mContexts.getResources(), images[0]);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		length = images.length;
		bitmap.recycle();
		int state =	init(name, width, height, COLOR, QUALITY, delay / 10);
		if (state != 0) {
			return;
		}
		pixels = new int[width * height];
		for (int i = 0; i < images.length; i++) {
			bitmap = BitmapFactory.decodeResource(mContexts.getResources(), images[i]);
			DebugUtil.debug(bitmap.toString());
			if (width < bitmap.getWidth() || height < bitmap.getHeight()) {
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}
			bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
			addFrame(pixels);
			bitmap.recycle();
		}
		close();
	}

	public native int addFrame(int[] pixels);

	/**
	 * @param name
	 * @param width
	 * @param height
	 * @param color
	 * @param quality
	 * @param delay
	 * @return 0-->ok -1--->OutOfMemoryError already thrown -2--->file not exist
	 */
	public native int init(String name, int width, int height, int color, int quality, int delay);

	public native void close();
}
