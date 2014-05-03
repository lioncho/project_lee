package kr.co.tourpang.manager.android.ui;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.controller.JoinActivity;
import kr.co.tourpang.manager.android.account.controller.LoginActivity;
import kr.co.tourpang.manager.android.account.controller.PartnerJoinActivity;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class SplashActivity extends AnimActivity implements OnClickListener {
	
	private ViewFlipper viewFlipper;
	private boolean monitoring;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getSupportActionBar().hide();
		
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		
		View loading = LayoutInflater.from(this).inflate(R.layout.activity_splash_loading, null, false);
		viewFlipper.addView(loading);

		Handler mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 3000);

	}
	
	@Override
	public void onResume() {
		super.onResume();
		AppConfiguration conf = AppConfiguration.getInstance();
		if (monitoring && conf.getUid() != null && !conf.getUid().isEmpty()) {
			Intent intent = new Intent(SplashActivity.this, MainFragmentActivity.class);
			startActivity(intent);
			
			finish();
		}
	}

	Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			
			AppConfiguration conf = AppConfiguration.getInstance();
			if (conf.getUid() == null || conf.getUid().isEmpty()) {
				View index = LayoutInflater.from(SplashActivity.this).inflate(R.layout.activity_splash_index, null, false);
				viewFlipper.addView(index);

				viewFlipper.setInAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.push_left_in));
				viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.push_left_out));
				viewFlipper.showNext();
		        
				index.findViewById(R.id.cmd_login).setOnClickListener(SplashActivity.this);
				index.findViewById(R.id.cmd_join).setOnClickListener(SplashActivity.this);
				index.findViewById(R.id.cmd_partner_join).setOnClickListener(SplashActivity.this);
				index.findViewById(R.id.cmd_help_join).setOnClickListener(SplashActivity.this);
		        
		        getSupportActionBar().setTitle("투어쿠폰이란?");
				getSupportActionBar().show();
				monitoring = true;				
			}
			else {
				Intent intent = new Intent(SplashActivity.this, MainFragmentActivity.class);
				startActivity(intent);
				
				finish();
			}
			
		}
	};

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
			case R.id.cmd_login: {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				break;
			}
			
			case R.id.cmd_join: {
				Intent intent = new Intent(this, JoinActivity.class);
				startActivity(intent);
				break;
			}
			
			case R.id.cmd_partner_join: {
				Intent intent = new Intent(this, PartnerJoinActivity.class);
				startActivity(intent);
				break;
			}
		}
		
	}

}
