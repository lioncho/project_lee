package kr.co.tourpang.manager.android.account.controller;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.ui.AnimActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class LoginActivity extends AnimActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setTitle("기업회원 로그인");
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		findViewById(R.id.login_button).setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			
			AppConfiguration.getInstance().setAutologin(((CheckBox) findViewById(R.id.cb_autologin)).isChecked());
			this.visibleProgressDialog(true);

			Handler mHandler = new Handler();
			mHandler.postDelayed(mRunnable, 2000);

			break;
		}

	}

	Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			
			AppConfiguration conf = AppConfiguration.getInstance();
			conf.setUid("a");
			conf.save();
			
			LoginActivity.this.visibleProgressDialog(false);
			LoginActivity.this.finish();
			
		}
	};
}
