package com.example.kinect;

import com.example.mycloset.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShirtActivity extends Activity implements OnClickListener{
	Handler handler;
	private Button s0,s1,s2,s3,s4,s5,s6,s7;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shirt);
		initView();// 初始化

	}

	private void initView() {
		s0=(Button) findViewById(R.id.s0);
		s1=(Button) findViewById(R.id.s1);
		s2=(Button) findViewById(R.id.s2);
		s3=(Button) findViewById(R.id.s3);
		s4=(Button) findViewById(R.id.s4);
		s5=(Button) findViewById(R.id.s5);
		s6=(Button) findViewById(R.id.s6);
		s7=(Button) findViewById(R.id.s7);
		s0.setOnClickListener(this);
		s1.setOnClickListener(this);
		s2.setOnClickListener(this);
		s3.setOnClickListener(this);
		s4.setOnClickListener(this);
		s5.setOnClickListener(this);
		s6.setOnClickListener(this);
		s7.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.s0:
			try {
				// 当用户按下按钮之后，将用户输入的数据封装成Message
				// 然后发送给子线程Handler
				Message msg = new Message();
				msg.what = 0x345;
				msg.obj = "0";
				KinectActivity.getClientThread().revHandler.sendMessage(msg);
			} catch (Exception e) {

			}
			break;

		default:
			break;
		}
		
	}

}
