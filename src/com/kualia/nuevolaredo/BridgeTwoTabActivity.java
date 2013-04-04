package com.kualia.nuevolaredo;

import java.util.Random;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BridgeTwoTabActivity extends BaseActivity {
	public static final String TAG = "KUALIA";
	public static final Boolean DEVELOPER_MODE = false;
	String url_Bridge2 = "http://www.ci.laredo.tx.us/bridgesys/hugeb1cam2.jpg";
	Random r = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bridge_layout);
		if (isNetworkAvailable()) {
			new GetBridgeImg().execute(url_Bridge2);
		} else {
			Toast.makeText(
					this,
					R.string.ErrorMsgNetworkConn,
					Toast.LENGTH_LONG).show();
		}
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
						R.string.ErrorMsgNetworkConn,
						Toast.LENGTH_LONG).show();
			}
			//Log.w(TAG, "item Refresh");
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

}
