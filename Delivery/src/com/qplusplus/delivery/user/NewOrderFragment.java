package com.qplusplus.delivery.user;

import com.qplusplus.delivery.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NewOrderFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user_neworder, container, false);
	}
}
