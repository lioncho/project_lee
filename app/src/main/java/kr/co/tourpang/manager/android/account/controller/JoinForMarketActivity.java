package kr.co.tourpang.manager.android.account.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.adpaters.RegionAdapter;
import kr.co.tourpang.manager.android.ui.AnimActivity;

/**
 * Created by mixon on 2014. 6. 2..
 */
public class JoinForMarketActivity extends AnimActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_market);

        getSupportActionBar().setTitle("쿠폰영업 / 추천업체가입");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RegionAdapter adapter = new RegionAdapter(this);
        ((Spinner) findViewById(R.id.region_spinner)).setAdapter(adapter);

        findViewById(R.id.login_button).setOnClickListener(this);
    }

    private boolean validate() {

        if (gt(R.id.username) == null || "".equals(gt(R.id.username))) {
            Toast.makeText(this, "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(R.id.password) == null || "".equals(gt(R.id.password))) {
            Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(R.id.password_confirm) == null || "".equals(gt(R.id.password_confirm))) {
            Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!gt(R.id.password).equals(gt(R.id.password_confirm))) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(R.id.display_name) == null || "".equals(gt(R.id.display_name))) {
            Toast.makeText(this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_button:
                if (validate()) {

                    RequestParams params = new RequestParams();
                    params.put("username", gt(R.id.username));
                    params.put("password", gt(R.id.password));
                    params.put("display_name", gt(R.id.display_name));
                    params.put("address", gt(R.id.address));
                    params.put("phone", gt(R.id.phone));
                    params.put("email", gt(R.id.email));
                    params.put("website", gt(R.id.website));
                    params.put("member_type", findViewById(((RadioGroup) findViewById(R.id.member_type)).getCheckedRadioButtonId()).getTag().toString());

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(2000);
                    client.post(this, "http://tourcoupon.net/api/mgr_authorize/signup_manager", params, onPostLoadComplete);

                    visibleProgressDialog(true);
                }
                break;
        }

    }

    private AsyncHttpResponseHandler onPostLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {

            try {
                JSONObject object = new JSONObject(response);
                if (!object.getBoolean("success")) {
                    Toast.makeText(JoinForMarketActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    visibleProgressDialog(false);
                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinForMarketActivity.this)
                            .setMessage("영업/추천업체 회원 가입이 완료되었습니다. \n" +
                                    "관리자 가입 승인후 이용할 수가 있습니다.")
                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });

                    builder.create().show();
                }
            }
            catch (Exception ex) {
                Toast.makeText(JoinForMarketActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                visibleProgressDialog(false);
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            arg0.printStackTrace();
            System.out.println(arg1);
            Toast.makeText(JoinForMarketActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            visibleProgressDialog(false);
        }

    };

    public String gt(int id) {
        CharSequence cs = ((TextView) findViewById(id)).getText();
        return (cs == null ? null : cs.toString());
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
