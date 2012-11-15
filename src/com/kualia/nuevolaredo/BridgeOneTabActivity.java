package com.kualia.nuevolaredo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class BridgeOneTabActivity extends Activity {

	public static final String TAG = "KUALIA";
	public static final Boolean DEVELOPER_MODE = false;

	ImageView imView;
	String url_Bridge2 = "http://www.ci.laredo.tx.us/bridgesys/hugeb2cam2.jpg";
	Random r = new Random();
	Bitmap bmImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bridge_layout);
		imView = (ImageView) findViewById(R.id.imageView1);
		if (isNetworkAvailable()) {
			try {
				new GetBridgeImg().execute(url_Bridge2);
			} catch (Exception e) {
			}

		} else {
			Toast.makeText(
					this,
					"Network not available. Plase make sure to conect your equipment to the network",
					Toast.LENGTH_LONG).show();
		}

		/*
		 * if (DEVELOPER_MODE) { StrictMode.setThreadPolicy(new
		 * StrictMode.ThreadPolicy.Builder()
		 * .detectNetwork().penaltyLog().build()); StrictMode.setVmPolicy(new
		 * StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build()); }
		 */

	}

	@Override
	protected void onResume() {
		super.onResume();
		// new GetBridgeImg().execute(url_Bridge2);
		Log.w(TAG, "onResume");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemRefresh:
			if (isNetworkAvailable()) {
				try {
					new GetBridgeImg().execute(url_Bridge2);
				} catch (Exception e) {
				}

			} else {
				Toast.makeText(
						this,
						"Network not available. Plase make sure to conect your equipment to the network",
						Toast.LENGTH_LONG).show();
			}
			Log.w(TAG, "item Refresh");
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;

	}

	class GetBridgeImg extends AsyncTask<String, Void, Bitmap> {
		/* this is the AsyncTask to download a image from the web */

		HttpURLConnection conn;
		URL myfileUrl = null;

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
				Toast.makeText(BridgeOneTabActivity.this, "Image Updated ",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(BridgeOneTabActivity.this,
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
				conn.connect();
				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is);
				conn.disconnect();
				return bmImg;
			} catch (IOException e) {
				Toast.makeText(BridgeOneTabActivity.this,
						"Sorry there was en error ", Toast.LENGTH_LONG).show();
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				return null;
			} catch (Exception e) {
				Toast.makeText(BridgeOneTabActivity.this,
						"Sorry there was en error ", Toast.LENGTH_LONG).show();
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
