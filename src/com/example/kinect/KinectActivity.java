package com.example.kinect;

import java.net.Socket;

import com.example.mycloset.R;
import com.example.mycloset.R.id;
import com.example.mycloset.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class KinectActivity extends Activity implements OnClickListener {
	public static TextView state;
	private Button connect, shirt, pantskirt, bag, dress, all;
	private Handler handler;
	// 定义与服务器通信的子线程
	private static ClientThread clientThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kinect);
		initView();// 初始化
		Message msg=new Message();
		if (msg.what == 0x123) {
		// 将读取的内容追加显示在文本框中
		state.setText(msg.obj.toString());	}
	}
	

	public static ClientThread getClientThread() {
		if (clientThread == null) {
			synchronized (KinectActivity.class) {
				if (clientThread == null) {
					clientThread = new ClientThread(new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// 如果消息来自子线程
							if (msg.what == 0x123) {
								// 将读取的内容追加显示在文本框中
								// state.append("\n" + msg.obj.toString());
								state.setText(msg.obj.toString());
							}
					}
							
						}
					);
				}
			}
		}
		return clientThread;
	}

	public void initView() {
		state = (TextView) findViewById(R.id.state);
		connect = (Button) findViewById(R.id.connect);
		shirt = (Button) findViewById(R.id.shirt);
		pantskirt = (Button) findViewById(R.id.pantskirt);
		bag = (Button) findViewById(R.id.bag);
		dress = (Button) findViewById(R.id.dress);
		all = (Button) findViewById(R.id.all);
		connect.setOnClickListener(this);
		shirt.setOnClickListener(this);
		pantskirt.setOnClickListener(this);
		bag.setOnClickListener(this);
		dress.setOnClickListener(this);
		all.setOnClickListener(this);
		new Thread(KinectActivity.getClientThread()).start();

	}

	private void startActivity(Activity activity) {
		Intent i = new Intent(this, activity.getClass());
		startActivity(i);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.connect:
			new Thread(clientThread).start();
			break;

		case R.id.shirt:
			startActivity(new ShirtActivity());
			break;

		case R.id.pantskirt:
			startActivity(new PantSkirtActivity());
			break;

		case R.id.bag:
			startActivity(new BagActivity());
			break;

		case R.id.dress:
			startActivity(new DressActivity());
			break;

		case R.id.all:
			startActivity(new AllActivity());
			break;
		}

	}

}
