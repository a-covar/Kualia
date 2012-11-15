package com.kualia.nuevolaredo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class KualiaTabActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
		textview.setText(R.string.MainMessage);
		setContentView(textview);
		
	}

}
