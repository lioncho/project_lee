package kr.co.tourpang.manager.android.ui;

import kr.co.tourpang.manager.android.R;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class AnimActivity extends ActionBarActivity {

	private int onStartCount = 0;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onStartCount = 1;
		if (savedInstanceState == null) {
			this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} 
		else 
			onStartCount = 2;
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (onStartCount > 1) {
			this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		} else if (onStartCount == 1) {
			onStartCount++;
		}

	}
	
	public void visibleProgressDialog(boolean visible) {
		if (dialog != null) {
			dialog.hide();
			dialog = null;
		}
		if (visible) {
			dialog = new ProgressDialog(this);
			dialog.setMessage("Please wait while loading...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}
	}
	
}
