package com.qplusplus.delivery.user;

import com.qplusplus.delivery.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity{
	private TextView tv_welcome;
	private SharedPreferences loginPref;
	private ListView menuList;
	private String[] menu;
	private DrawerLayout mainDrawLayout;
	private ImageButton ib_logo;
	private FragmentManager fm = getFragmentManager();
	private FragmentTransaction ft = fm.beginTransaction();
	private class drawerListener implements OnClickListener{
		public void onClick(View v) {
			if(mainDrawLayout.isDrawerOpen(Gravity.LEFT)){
				mainDrawLayout.closeDrawer(Gravity.LEFT);
			}else{
				mainDrawLayout.openDrawer(Gravity.LEFT);
			}
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		Fragment frag_home = new HomeFragment();
		ft.replace(R.id.content_frame, frag_home);
		ft.commit();
		loginPref = getSharedPreferences("login", Context.MODE_PRIVATE);
		tv_welcome = (TextView)findViewById(R.id.tv_welcome);
		tv_welcome.setText("Welcome, "+loginPref.getString("username", "")+"!");
		menuList = (ListView) findViewById(R.id.left_drawer);
		menu = getResources().getStringArray(R.array.menu_user);
		mainDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		menuList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu));
		ib_logo = (ImageButton)findViewById(R.id.ib_logo);
		ib_logo.setOnClickListener(new drawerListener());
		menuList.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
				showFragment(position);
				if(mainDrawLayout.isDrawerOpen(Gravity.LEFT)){
					mainDrawLayout.closeDrawer(Gravity.LEFT);
				}else{
					mainDrawLayout.openDrawer(Gravity.LEFT);
				}
			}
		});
	}
	protected void showFragment(int position) {
		switch(position){
			case 0: 
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_home = new HomeFragment();
				ft.replace(R.id.content_frame, frag_home);
				System.out.println(position);
				 break;
			case 1:
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_newOrder = new NewOrderFragment();
				ft.replace(R.id.content_frame, frag_newOrder);
				System.out.println(position);
				 break;
			case 2:
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_orderStatus = new OrderStatusFragment();
				ft.replace(R.id.content_frame, frag_orderStatus);
				System.out.println(position);
				 break;
			case 3:
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_message = new MessageFragment();
				ft.replace(R.id.content_frame, frag_message);
				System.out.println(position);
				 break;
			case 4:
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_promotion = new PromotionFragment();
				ft.replace(R.id.content_frame, frag_promotion);
				System.out.println(position);
				 break;
			case 5:
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_feedback = new FeedbackFragment();
				ft.replace(R.id.content_frame, frag_feedback);
				System.out.println(position);
				 break;
			case 6:
				ft = fm.beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				Fragment frag_aboutus = new AboutUsFragment();
				ft.replace(R.id.content_frame, frag_aboutus);
				System.out.println(position);
				 break;
		}
		ft.commit();
	}

}
