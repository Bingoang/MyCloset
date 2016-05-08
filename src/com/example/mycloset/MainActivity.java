package com.example.mycloset;

import com.example.kinect.KinectActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {
	private ImageView clothesbtn, shopbtn, kinectbtn, aboutbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();// 初始化
	}

	public void initView() {
		clothesbtn = (ImageView) findViewById(R.id.cbtn);
		shopbtn = (ImageView) findViewById(R.id.sbtn);
		kinectbtn = (ImageView) findViewById(R.id.kbtn);
		aboutbtn = (ImageView) findViewById(R.id.abtn);
		clothesbtn.setOnClickListener(this);
		shopbtn.setOnClickListener(this);
		kinectbtn.setOnClickListener(this);
		aboutbtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cbtn:
			Intent i = new Intent(this, MyClothesActivity.class);
			i.putExtra("flag", "1");// 键值对.值为string类型
			startActivity(i);
			break;

		case R.id.sbtn:
			Intent i2 = new Intent(this, ShopActivity.class);
			i2.putExtra("flag", "2");
			startActivity(i2);
			break;

		case R.id.kbtn:
			 Intent i3 = new Intent(this, KinectActivity.class);
			 i3.putExtra("flag", "3");
			 startActivity(i3);
//			Toast toast = null;
//			toast = Toast.makeText(getApplicationContext(), "嘿嘿，即将上线，敬请期待！",
//					Toast.LENGTH_SHORT);
//			toast.setGravity(Gravity.CENTER, 0, 0);
//			LinearLayout toastView = (LinearLayout) toast.getView();
//			ImageView imageCodeProject = new ImageView(getApplicationContext());
//			imageCodeProject.setImageResource(R.drawable.nice);
//			toastView.addView(imageCodeProject, 0);
//			toast.show();
			break;

		case R.id.abtn:
			Intent i4 = new Intent(this, AboutActivity.class);
			i4.putExtra("flag", "4");
			startActivity(i4);
			break;

		}
	}

}
