package com.qplusplus.delivery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends Activity{
	private TextView tv_welcome;
	private SharedPreferences loginPref;
	private Editor loginEditor;
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.activity_user);
	     loginPref = getSharedPreferences("login", Context.MODE_PRIVATE);
	     tv_welcome = (TextView)findViewById(R.id.tv_welcome);
	     tv_welcome.setText("Welcome, "+loginPref.getString("username", "")+"!");
	}
}
