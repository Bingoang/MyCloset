package com.example.kinect;

import com.example.mycloset.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AllActivity extends Activity implements OnClickListener{
	private Button allback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all);
		initView();
		allMode();// ��������ģʽ


	}

	private void allMode() {
		try {
			// ���û����°�ť֮�󣬽��û���������ݷ�װ��Message
			// Ȼ���͸����߳�Handler
			Message msg = new Message();
			msg.what = 0x345;
			msg.obj = "00";
			KinectActivity.getClientThread().revHandler.sendMessage(msg);
		} catch (Exception e) {

		}

	}
	

	private void initView() {
		allback=(Button) findViewById(R.id.allback);
		allback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();	
	}

}
