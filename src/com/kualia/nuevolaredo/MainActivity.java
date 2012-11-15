package com.kualia.nuevolaredo;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		try {
			intent = new Intent().setClass(this, BridgeTwoTabActivity.class);
			spec = tabHost
					.newTabSpec("TabBridgeI")
					.setIndicator(this.getString(R.string.PunteI),
							res.getDrawable(R.drawable.ic_tab_artists))
					.setContent(intent);
			tabHost.addTab(spec);
			intent = new Intent().setClass(this, BridgeOneTabActivity.class);
			spec = tabHost
					.newTabSpec("TabBridgeII")
					.setIndicator(this.getString(R.string.PuenteII),
							res.getDrawable(R.drawable.ic_tab_albums))
					.setContent(intent);
			tabHost.addTab(spec);
			intent = new Intent(this, KualiaTabActivity.class);
			spec = tabHost
					.newTabSpec("TabMain")
					.setIndicator(this.getString(R.string.app_name),
							res.getDrawable(R.drawable.ic_tab_song))
					.setContent(intent);
			tabHost.addTab(spec);
			tabHost.setCurrentTab(2);
		} catch (Exception e) {
			Log.d("KUALIA", e.toString());
		}
	}
}