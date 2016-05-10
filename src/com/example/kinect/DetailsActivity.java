package com.example.kinect;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.mycloset.R;
import com.example.mycloset.R.id;
import com.example.mycloset.R.layout;
import com.example.mycloset.WebViewActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class DetailsActivity extends Activity implements OnClickListener {
	private String val;// 用来接收传过来的flag
	private Button tryon, shop;
	private ImageView photo;
	private TextView name, namecontent, detail, detailcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		initView();
		val = getIntent().getStringExtra("flag");// 接收按钮传来的价值对flag
		Log.v("ang", val);
		show();
	}

	private void show() {
		
		if (ShirtActivity.myFlag.equals("10")) {
			photo.setImageLevel(0);
			photo.setScaleType(ScaleType.CENTER_CROP);
			Log.v("ang", "show");
		}
		if (val.equals("11")) {
			photo.setImageLevel(1);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val.equals("12")) {
			photo.setImageLevel(2);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val.equals("13")) {
			photo.setImageLevel(3);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val.equals("14")) {
			photo.setImageLevel(4);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val.equals("15")) {
			photo.setImageLevel(5);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val.equals("16")) {
			photo.setImageLevel(6);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val.equals("17")) {
			photo.setImageLevel(7);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}

	}

	private void initView() {
		tryon = (Button) findViewById(R.id.tryon);
		shop = (Button) findViewById(R.id.shop);
		photo = (ImageView) findViewById(R.id.photo);
		name = (TextView) findViewById(R.id.name);
		namecontent = (TextView) findViewById(R.id.namecontent);
		detail = (TextView) findViewById(R.id.detail);
		detailcontent = (TextView) findViewById(R.id.detailcontent);
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

				if (ShirtActivity.myFlag.equals("10") || val.equals("20") || val.equals("30")
						|| val.equals("40")) {
					msg.obj = "0";
				}

				if (val.equals("11") || val.equals("21") || val.equals("31")
						|| val.equals("41")) {
					msg.obj = "1";
				}

				if (val.equals("12") || val.equals("22") || val.equals("32")) {
					msg.obj = "2";
				}

				if (val.equals("13") || val.equals("23") || val.equals("33")) {
					msg.obj = "3";
				}

				if (val.equals("14") || val.equals("24") || val.equals("34")) {
					msg.obj = "4";
				}

				if (val.equals("15") || val.equals("25") || val.equals("35")) {
					msg.obj = "5";
				}
				if (val.equals("16")) {
					msg.obj = "6";
				}
				if (val.equals("17")) {
					msg.obj = "7";
				}
				synchronized (this.getClass()) {
					KinectActivity.getClientThread().revHandler.sendMessage(msg);
				}
				

			} catch (Exception e) {

			}
			break;

		case R.id.shop:
			Intent i = new Intent(this, WebViewActivity.class);
			if (val.equals("10")) {
				i.putExtra("flag", "10");
//				startActivityForResult(i, 10);
			}
			if (val.equals("11")) {
				i.putExtra("flag", "11");
//				startActivityForResult(i, 11);
			}
			if (val.equals("12")) {

			}
			if (val.equals("13")) {

			}
			if (val.equals("14")) {

			}
			if (val.equals("15")) {

			}
			if (val.equals("16")) {

			}
			if (val.equals("17")) {

			}
			startActivity(i);
		   
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        val = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i("ang", val);
		}
	}

