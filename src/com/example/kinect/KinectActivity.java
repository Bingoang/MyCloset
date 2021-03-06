package com.example.kinect;

import com.example.mycloset.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KinectActivity extends Activity implements OnClickListener {
	public static TextView state;
	private Button connect, shirt, pantskirt, bag, dress, all;
	private Handler handler;
	// 定义与服务器通信的子线程
	private static ClientThread clientThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.kinect);
		initView();// 初始化
	}

	public static ClientThread getClientThread() {
		if (clientThread == null) {
			synchronized (KinectActivity.class) {
				if (clientThread == null) {
					clientThread = new ClientThread(new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// 如果消息来自ClientThread子线程,时时改变连接状态
							if (msg.what == 0x123) {
								state.setTextColor(Color.parseColor("#FF0033"));
								state.setText(msg.obj.toString());	
//								if(msg.obj.equals("网络连接超时！")){
//									Toast.makeText(this, "网络连接超时，请检查网络连接！", Toast.LENGTH_SHORT).show();
//							}
//								if(msg.obj.equals("已断开！")){
//									Toast.makeText(this, "网络连接超时，请检查网络连接！", Toast.LENGTH_SHORT).show();
//							}
							}
							if (msg.what == 0x111) {
								state.setText(msg.obj.toString());
								state.setTextColor(Color.parseColor("#66CC33"));
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
