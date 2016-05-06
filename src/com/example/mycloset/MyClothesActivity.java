package com.example.mycloset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView;

public class MyClothesActivity extends Activity implements OnClickListener {

	private ImageView topsbtn, pantsbtn, skirtbtn, shoesbtn;
	private Intent i;// ������ʶ����ʶ�𵽵׵�����ĸ���ť
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clothes);
		initView();// ��ʼ��
	}

	public void initView() {
		topsbtn = (ImageView) findViewById(R.id.tops);
		pantsbtn = (ImageView) findViewById(R.id.pants);
		skirtbtn = (ImageView) findViewById(R.id.skirt);
		shoesbtn = (ImageView) findViewById(R.id.shoes);
		topsbtn.setOnClickListener(this);
		pantsbtn.setOnClickListener(this);
		skirtbtn.setOnClickListener(this);
		shoesbtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tops:
			i = new Intent(this, TopsActivity.class);
			i.putExtra("flag", "1");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;

		case R.id.pants:

			break;
		case R.id.skirt:

			break;
		case R.id.shoes:

			break;
		}

	}

}
