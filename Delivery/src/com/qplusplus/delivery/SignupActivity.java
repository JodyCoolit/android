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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity{
	private Button btn_register;
	private EditText et_username;
	private EditText et_password;
	private EditText et_phone;
	private EditText et_email;
	private EditText et_address;
	private String [] regInfo = new String[5];
	private AlertDialog.Builder d;
	private Intent i;
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        i = new Intent(this,LoginActivity.class);
        d = new AlertDialog.Builder(this)
        .setTitle("Congrats!")
        .setMessage("Register successfully!")
        .setIcon(android.R.drawable.ic_dialog_info)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                startActivity(i);
            }});
        et_username = (EditText)findViewById(R.id.et_signup_username);
        et_password = (EditText)findViewById(R.id.et_signup_password);
        et_phone = (EditText)findViewById(R.id.et_signup_phone);
        et_email = (EditText)findViewById(R.id.et_signup_email);
        et_address = (EditText)findViewById(R.id.et_signup_address);
        btn_register = (Button)findViewById(R.id.btn_signup_register);
        btn_register.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		regInfo[0] = et_username.getText().toString();
                regInfo[1] = et_password.getText().toString();
                regInfo[2] = et_phone.getText().toString();
                regInfo[3] = et_email.getText().toString();
                regInfo[4] = et_address.getText().toString();
        		new signupTask().execute(regInfo);
        	}
        });
    }
	private class signupTask extends AsyncTask<String, Integer, Boolean>{
    	String username;
    	String password;
    	String phone;
    	String email;
    	String address;
    	boolean result;
        protected void onProgressUpdate(Integer... progress){
        	
        }

        protected void onPostExecute(Boolean result){
        	if(result){
        		d.show();
        	}
        }

		protected Boolean doInBackground(String... params){
			result = false;
			username = params[0];
			password = params[1];
			phone = params[2];
			email = params[3];
			address = params[4];
			System.out.println(username+" "+password);
			HttpClient httpclient = new DefaultHttpClient();
			BufferedReader in = null;
			HttpPost httppost = new HttpPost("http://192.168.1.157:8080/delivery/register.php");
			try {
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("username", username));
		        nameValuePairs.add(new BasicNameValuePair("password", password));
		        nameValuePairs.add(new BasicNameValuePair("phone", phone));
		        nameValuePairs.add(new BasicNameValuePair("email", email));
		        nameValuePairs.add(new BasicNameValuePair("address", address));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		        String line = "";
		        while ((line = in.readLine()) != null){
	                System.out.println(line);
	                if(line.equals("yes")){
	                	result = true;
	                }
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
