package com.stay.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.stay.gif.GifDecode;

public class GifDisplayActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (true) {//java style to decode gif stream to image frames
			setContentView(new JavaGifDecode(this,Environment.getExternalStorageDirectory()+"/girl.gif"));
		}else {//google api to display gif,recommend this method
			setContentView(new GoogleGifDecode(this,Environment.getExternalStorageDirectory()+"/girl.gif"));
		}
	}
	
	/**
	 * @Description: switch gif to every frame images animate on UI based on google apiDemos {@link BitmapDecode}
	 *
	 * @author Stay
	 *
	 * @Date 2011-12-12 02:48:36 pm
	 * 
	 */
	class GoogleGifDecode extends ImageView {
		private long mMovieStart;
		private Movie mMovie;

		public GoogleGifDecode(Context context,String path) {
			super(context);
			try {
				mMovie = Movie.decodeStream(new FileInputStream(path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			long now = android.os.SystemClock.uptimeMillis();
			if (mMovieStart == 0) { // first time
				mMovieStart = now;
			}
			if (mMovie != null) {
				int dur = mMovie.duration();
				if (dur == 0) {
					dur = 1000;
				}
				int relTime = (int) ((now - mMovieStart) % dur);
				mMovie.setTime(relTime);
				mMovie.draw(canvas, (getWidth() - mMovie.width())/2, (getHeight() - mMovie.height())/2);
				invalidate();
			}
		}
	}
	
	/**
	 * @Description: decode gif to every frame images animate on UI 
	 *	you can see  {@link GifDecode} how it works
	 * @author Stay
	 *
	 * @Date 2011-12-12 02:48:36 pm
	 * 
	 */
	class JavaGifDecode extends View implements Runnable {
		private Bitmap bmb;
		private GifDecode decode;
		private int index;
		private int gifCount;

		public JavaGifDecode(Context context,String path) {
			super(context);
			decode = new GifDecode();
			decode.read(path);
			index = 0;
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
			index++;
			if (index == gifCount - 1) {
				index = 0;
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					this.postInvalidate();
					Thread.sleep(decode.getDelay(index));
				} catch (Exception ex) {

				}
			}

		}
	}
}
