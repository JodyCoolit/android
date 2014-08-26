package com.qplusplus.delivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.qplusplus.delivery.admin.AdminActivity;
import com.qplusplus.delivery.courier.CourierActivity;
import com.qplusplus.delivery.staff.StaffActivity;
import com.qplusplus.delivery.user.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class LoginActivity extends Activity{
	private Button btn_signup;
	private Button btn_signin;
	private EditText et_username;
	private EditText et_password;
	private ProgressBar pb;
	private SharedPreferences loginPref;
	private Editor loginEditor;
	private CheckBox cb_rememberMe;
	private Intent i_signup,i_user,i_admin,i_staff,i_courier;
	private boolean doubleBackToExitPressedOnce;
	public void onBackPressed(){
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	}
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signin = (Button)findViewById(R.id.btn_signin);
        et_username = (EditText)findViewById(R.id.et_login_username);
        et_password = (EditText)findViewById(R.id.et_login_password);
        pb = (ProgressBar)findViewById(R.id.pb_login);
        cb_rememberMe = (CheckBox)findViewById(R.id.cb_rememberMe);
        pb.setVisibility(View.GONE);
        loginPref = getSharedPreferences("login", Context.MODE_PRIVATE);
    	loginEditor = loginPref.edit();
    	if(loginPref.getBoolean("loginSession",false)){
    		et_username.setText(loginPref.getString("username", ""));
    		et_password.setText(loginPref.getString("password", ""));
    		cb_rememberMe.setChecked(true);
    		new signinTask().execute(et_username.getText().toString(),et_password.getText().toString());
    	}
        i_signup = new Intent(this,SignupActivity.class);
        i_user = new Intent(this,UserActivity.class);
        i_admin = new Intent(this,AdminActivity.class);
        i_staff = new Intent(this,StaffActivity.class);
        i_courier = new Intent(this,CourierActivity.class);

        btn_signup.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		startActivity(i_signup);
        	}
        });
        btn_signin.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		if(et_username.getText().toString().matches("")||et_password.getText().toString().matches("")){
        			Toast.makeText(getApplicationContext(), "Please fill all blank",
        				     Toast.LENGTH_SHORT).show();
        		}else{
        			new signinTask().execute(et_username.getText().toString(),et_password.getText().toString());
        		}
        	}
        });
    }
    
    private class signinTask extends AsyncTask<String, Integer, String> {
    	String username;
    	String password;
    	String result = "";
        protected void onProgressUpdate(Integer... progress){
        	pb.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(String result){
            if(result.equals("admin")){
            	loginEditor.putString("username", username);
            	loginEditor.putString("password", password);
            	loginEditor.putString("role", "admin");
            	startActivity(i_admin);
            }else if(result.equals("staff")){
            	loginEditor.putString("username", username);
            	loginEditor.putString("password", password);
            	loginEditor.putString("role", "staff");
            	startActivity(i_staff);
            }else if(result.equals("courier")){
            	loginEditor.putString("username", username);
            	loginEditor.putString("password", password);
            	loginEditor.putString("role", "courier");
            	startActivity(i_courier);
            }else if(result.equals("user")){
            	loginEditor.putString("username", username);
            	loginEditor.putString("password", password);
            	loginEditor.putString("role", "user");
            	startActivity(i_user);
            }else{
            	Toast.makeText(getApplicationContext(), "User doesnot exist or invalid password",
   				     Toast.LENGTH_SHORT).show();
            }
            if(cb_rememberMe.isChecked()){
            	loginEditor.putBoolean("loginSession", true);
            }else{
            	loginEditor.putBoolean("loginSession", false);
            }
            loginEditor.commit();
            pb.setVisibility(View.GONE);
        }

		protected String doInBackground(String... params){
			username = params[0];
			password = params[1];
			Log.i("username",username);
			Log.i("password",password);
			HttpClient httpclient = new DefaultHttpClient();
			BufferedReader in = null;
			HttpPost httppost = new HttpPost("http://192.168.1.157:8080/delivery/validation.php");
			try {
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("username", username));
		        nameValuePairs.add(new BasicNameValuePair("password", password));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		        String line = "";
		        while ((line = in.readLine()) != null){
		        	Log.i("login role : ",line);
	                result = line;
	            }
		    } catch (ClientProtocolException e){
		    	e.printStackTrace();
		    } catch (IOException e){
		    	e.printStackTrace();
		    }
			return result;
		}
    }

	
}
