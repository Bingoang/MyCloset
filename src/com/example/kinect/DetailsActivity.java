package com.example.kinect;

import com.example.mycloset.R;
import com.example.mycloset.R.id;
import com.example.mycloset.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
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
		val = getIntent().getStringExtra("f");// 接收按钮传来的价值对flag
		Log.v("ang",val+"");
//			Toast.makeText(this, "接收到" + val, Toast.LENGTH_SHORT)
//					.show();
//		show();
	}

	private void show() {
		val = getIntent().getStringExtra("flag");// 接收按钮传来的价值对flag
		if (val == "10") {
			photo.setImageLevel(0);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val == "11") {
			photo.setImageLevel(1);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val == "12") {
			photo.setImageLevel(2);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val == "13") {
			photo.setImageLevel(3);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val == "14") {
			photo.setImageLevel(4);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val == "15") {
			photo.setImageLevel(5);
			photo.setScaleType(ScaleType.CENTER_CROP);
		}
		if (val == "16") {
			photo.setImageLevel(6);
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
				msg.obj = "0";
				KinectActivity.getClientThread().revHandler.sendMessage(msg);
				// || val == "20" || val == "30" || val == "40"
//				if (val == "10") {
//					Toast.makeText(this, "接收到" + val, Toast.LENGTH_SHORT)
//							.show();
//				}
				// if (val == "11"||val =="21"||val =="31"||val =="41") {
				// msg.obj = "1";
				// }
				//
				// if (val == "12"||val =="22"||val =="32") {
				// msg.obj = "2";
				// }
				//
				// if (val == "13"||val =="23"||val =="33") {
				// msg.obj = "3";
				// }
				//
				// if (val == "14"||val =="24"||val =="34") {
				// msg.obj = "4";
				// }
				//
				// if (val == "15"||val =="25"||val =="35") {
				// msg.obj = "5";
				// }
				// if (val == "16") {
				// msg.obj = "6";
				// }
				// if (val == "17") {
				// msg.obj = "7";
				// }
				//

			} catch (Exception e) {

			}
			break;

		case R.id.shop:

			break;
		}

	}
}
