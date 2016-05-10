package com.example.mycloset;

import com.example.clothes.MyClothesActivity;
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

	private void startActivity(Activity activity) {
		Intent i = new Intent(this, activity.getClass());
		startActivity(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cbtn:
			startActivity(new MyClothesActivity());
			break;

		case R.id.sbtn:
			Intent i;
			i = new Intent(this, WebViewActivity.class);
			i.putExtra("flag", "shop");
			startActivity(i);
			break;

		case R.id.kbtn:
			startActivity(new KinectActivity());
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
			startActivity(new AboutActivity());
			break;

		}
	}

}
