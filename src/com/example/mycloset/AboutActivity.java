package com.example.mycloset;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AboutActivity extends Activity {
	private ImageView big;
	private Animation loadAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutus);
		initView();
	}

	private void initView() {
		big = (ImageView) findViewById(R.id.big);
//		loadAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
//		big.startAnimation(loadAnimation);
		TranslateAnimation translate = new TranslateAnimation(0, 0,
				-25,25);
		translate.setDuration(500);
		translate.setRepeatCount(Animation.INFINITE);
		translate.setRepeatMode(Animation.REVERSE);
		big.startAnimation(translate);

	}

}
