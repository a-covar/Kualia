package com.kualia.nuevolaredo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class BaseActivity extends Activity {
	public static final String TAG = "KUALIA";

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;

	}
	
	
	
	class GetBridgeImg extends AsyncTask<String, Void, Bitmap> {
		/* this is the AsyncTask to download a image from the web */
		Bitmap bmImg;
		HttpURLConnection conn;
		URL myfileUrl = null;
		ImageView imView = (ImageView) findViewById(R.id.imageView1);

		@Override
		protected void onCancelled() {
			super.onCancelled();
			Log.w(TAG, "onCancelled AsyncTask");
			conn.disconnect();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			Log.w(TAG, "onPostExecute");
			if (result != null) {
				Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
						.getDefaultDisplay();
				Bitmap scaled = Bitmap.createScaledBitmap(result,
						display.getWidth(), display.getHeight(), true);
				imView.setImageBitmap(scaled);
				Toast.makeText(BaseActivity.this, "Image Updated",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(BaseActivity.this,
						"Sorry we could't retreive image ", Toast.LENGTH_LONG)
						.show();
			}

		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Log.w(TAG, "doInBackground AsycTask");

			try {
				myfileUrl = new URL(params[0]);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				return null;
			}
			try {
				Log.w(TAG, "try catch HttpURLConnection");
				conn = (HttpURLConnection) myfileUrl.openConnection();
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setDefaultUseCaches(false);
			//	conn.setRequestProperty("Contro	", newValue)
				conn.connect();
				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is);
				conn.disconnect();
				return bmImg;
			} catch (IOException e) {
				Toast.makeText(BaseActivity.this, "Sorry there was en error ",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				return null;
			} catch (Exception e) {
				Toast.makeText(BaseActivity.this, "Sorry there was en error ",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				return null;
			} /*
			 * catch (NetworkOnMainThreadException e) { e.printStackTrace();
			 * Log.e(tag, e.getMessage()); return null; }
			 */

		}

	}

}
