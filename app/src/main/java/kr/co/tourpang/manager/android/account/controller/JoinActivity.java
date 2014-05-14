package kr.co.tourpang.manager.android.account.controller;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.AnimActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class JoinActivity extends AnimActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setTitle("기업회원가입");
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
}
