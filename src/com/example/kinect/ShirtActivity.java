package com.example.kinect;

import com.example.mycloset.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShirtActivity extends Activity implements OnClickListener {
	private Button s0, s1, s2, s3, s4, s5, s6, s7;
	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shirt);
		initView();// ��ʼ��
		shirtMode();// ��������ģʽ


	}

	private void shirtMode() {
		try {
			// ���û����°�ť֮�󣬽��û���������ݷ�װ��Message
			// Ȼ���͸����߳�Handler
			Message msg = new Message();
			msg.what = 0x345;
			msg.obj = "11";
			KinectActivity.getClientThread().revHandler.sendMessage(msg);
		} catch (Exception e) {

		}

	}

	private void initView() {
		s0 = (Button) findViewById(R.id.s0);
		s1 = (Button) findViewById(R.id.s1);
		s2 = (Button) findViewById(R.id.s2);
		s3 = (Button) findViewById(R.id.s3);
		s4 = (Button) findViewById(R.id.s4);
		s5 = (Button) findViewById(R.id.s5);
		s6 = (Button) findViewById(R.id.s6);
		s7 = (Button) findViewById(R.id.s7);
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
		i = new Intent(this, DetailsActivity.class);
		switch (v.getId()) {
		case R.id.s0:
			i.putExtra("flag", "10");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s1:
			i.putExtra("flag", "11");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s2:
			i.putExtra("flag", "12");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s3:
			i.putExtra("flag", "13");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s4:
			i.putExtra("flag", "14");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s5:
			i.putExtra("flag", "15");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s6:
			i.putExtra("flag", "16");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;
			
		case R.id.s7:
			i.putExtra("flag", "17");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;

		}

	}

}
