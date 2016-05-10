package com.example.kinect;

import com.example.mycloset.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity implements OnClickListener {
	private String val;// 用来接收传过来的flag
	private Button tryon, shop;
	private ImageView photo;
	private TextView name,namecontent,detail,detailcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		initView();
		show();
	}

	private void show() {
		val = getIntent().getStringExtra("flag");// 接收按钮传来的价值对flag
		switch (val) {
		case "10":
			photo.setImageLevel(0);//
			break;

		default:
			break;
		}
		
	}

	private void initView() {
		tryon = (Button) findViewById(R.id.tryon);
		shop = (Button) findViewById(R.id.shop);
		photo=(ImageView) findViewById(R.id.photo);
		name=(TextView) findViewById(R.id.name);
		namecontent=(TextView) findViewById(R.id.namecontent);
		detail=(TextView) findViewById(R.id.detail);
		detailcontent=(TextView) findViewById(R.id.detailcontent);
		tryon.setOnClickListener(this);
		shop.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tryon:
			try {
				// 当用户按下按钮之后，将用户输入的数据封装成Message
				// 然后发送给子线程Handler
				Message msg = new Message();
				msg.what = 0x345;
				msg.obj = "6";
				KinectActivity.getClientThread().revHandler.sendMessage(msg);
			} catch (Exception e) {

			}
			break;

		case R.id.shop:

			break;
		}

	}
}
