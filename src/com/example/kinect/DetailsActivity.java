package com.example.kinect;

import com.example.mycloset.R;
import com.example.mycloset.WebViewActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class DetailsActivity extends Activity implements OnClickListener {
	private Button tryon, shop;
	private ImageView photo;
	private TextView name, namecontent, detail, detailcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details);
		initView();
		show();
	}

	private void show() {
		// shirt类详情页面显示的图片，level为0~7
		if (ShirtActivity.myFlag >= 10 && ShirtActivity.myFlag <= 17) {
			photo.setImageLevel(ShirtActivity.myFlag - 10);
		}
		photo.setScaleType(ScaleType.CENTER_CROP);
		Log.v("ang", "show");
	}

	private void initView() {
		tryon = (Button) findViewById(R.id.tryon);
		shop = (Button) findViewById(R.id.shop);
		photo = (ImageView) findViewById(R.id.photo);
		name = (TextView) findViewById(R.id.name);
		namecontent = (TextView) findViewById(R.id.namecontent);
		detail = (TextView) findViewById(R.id.detail);
		detailcontent = (TextView) findViewById(R.id.detailcontent);
//		textChange();
		tryon.setOnClickListener(this);
		shop.setOnClickListener(this);
	}

	private void textChange() {
		switch (ShirtActivity.myFlag) {
		case 10:

			break;

		case 11:
			namecontent.setText("11111");
			break;
		}

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
				if (ShirtActivity.myFlag == 10 || ShirtActivity.myFlag == 20
						|| ShirtActivity.myFlag == 30
						|| ShirtActivity.myFlag == 40) {
					msg.obj = "0";
				}

				if (ShirtActivity.myFlag == 11 || ShirtActivity.myFlag == 21
						|| ShirtActivity.myFlag == 31
						|| ShirtActivity.myFlag == 41) {
					msg.obj = "1";
				}

				if (ShirtActivity.myFlag == 12 || ShirtActivity.myFlag == 22
						|| ShirtActivity.myFlag == 32) {
					msg.obj = "2";
				}

				if (ShirtActivity.myFlag == 13 || ShirtActivity.myFlag == 23
						|| ShirtActivity.myFlag == 33) {
					msg.obj = "3";
				}

				if (ShirtActivity.myFlag == 14 || ShirtActivity.myFlag == 24
						|| ShirtActivity.myFlag == 34) {
					msg.obj = "4";
				}

				if (ShirtActivity.myFlag == 15 || ShirtActivity.myFlag == 25
						|| ShirtActivity.myFlag == 35) {
					msg.obj = "5";
				}
				if (ShirtActivity.myFlag == 16) {
					msg.obj = "6";
				}
				if (ShirtActivity.myFlag == 17) {
					msg.obj = "7";
				}
				synchronized (this.getClass()) {
					KinectActivity.getClientThread().revHandler
							.sendMessage(msg);
				}

			} catch (Exception e) {

			}
			break;

		case R.id.shop:
			Intent i = new Intent(this, WebViewActivity.class);
			i.putExtra("flag", ShirtActivity.myFlag + "");// flag:"10"~"17"
			startActivity(i);
			break;
		}

	}
}
