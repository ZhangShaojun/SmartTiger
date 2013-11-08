package com.smarttiger.demos.fragment;

import com.smarttiger.demos.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment1 extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_test_fragment1,null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Button button1=(Button)getActivity().findViewById(R.id.fragment1_btn1);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView)getActivity().findViewById(R.id.fragment2_text)).setText("aa");
			}
		});
		
		Button button2=(Button)getActivity().findViewById(R.id.fragment1_btn2);
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView)getActivity().findViewById(R.id.fragment2_text)).setText("bb");
			}
		});
		
	}
}
