package kr.co.tourpang.manager.android.account.controller;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.controller.adpaters.LangAdapter;
import kr.co.tourpang.manager.android.account.controller.adpaters.RegionAdapter;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.ui.AnimActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class LoginActivity extends AnimActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setTitle("기업회원 로그인");
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		findViewById(R.id.login_button).setOnClickListener(this);

        ((Spinner) findViewById(R.id.region_spinner)).setAdapter(new RegionAdapter(this));
        ((Spinner) findViewById(R.id.language_spinner)).setAdapter(new LangAdapter(this));
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

            RequestParams params = new RequestParams();
            params.put("username", gt(R.id.username));
            params.put("password", gt(R.id.password));

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(2000);
            client.post(this, "http://tourcoupon.net/api/mgr_authorize/signin", params, onPostLoadComplete);

			break;
		}

	}


    private AsyncHttpResponseHandler onPostLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {

            try {
                System.out.println(response);
                JSONObject object = new JSONObject(response);
                if (!object.getBoolean("success")) {
                    Toast.makeText(LoginActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    visibleProgressDialog(false);
                }
                else {
                    AppConfiguration conf = AppConfiguration.getInstance();
                    conf.setUid(gt(R.id.username));
                    conf.setPassword(gt(R.id.password));
                    conf.setUsername(object.getJSONObject("data").getString("display_name"));
                    conf.save();

                    LoginActivity.this.visibleProgressDialog(false);
                    LoginActivity.this.finish();
                }
            }
            catch (Exception ex) {
                Toast.makeText(LoginActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                visibleProgressDialog(false);
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            Toast.makeText(LoginActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            visibleProgressDialog(false);
        }

    };


    public String gt(int id) {
        CharSequence cs = ((TextView) findViewById(id)).getText();
        return (cs == null ? null : cs.toString());
    }
}
