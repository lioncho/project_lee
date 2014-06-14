package kr.co.tourpang.manager.android.coupon.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.coupon.adapters.UsableAdapter;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.ui.AnimActivity;

/**
 * Created by mixon on 2014. 6. 3..
 */
public class CouponAddActivity extends AnimActivity implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private TextView dialogCaller;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_add);

        AppConfiguration conf = AppConfiguration.getInstance();

        boolean modify = getIntent().getBooleanExtra("modify", false);
        getSupportActionBar().setTitle(modify ? "쿠폰수정" : "쿠폰등록");
        getSupportActionBar().setSubtitle(conf.getUsername());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_coupon);

        if (modify) this.getFields(getIntent().getStringExtra("id"));

        findViewById(R.id.start_date).setOnClickListener(this);
        findViewById(R.id.close_date).setOnClickListener(this);

        findViewById(R.id.confirm_button).setOnClickListener(this);
        findViewById(R.id.cancel_button).setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        ((TextView) findViewById(R.id.start_date)).setText(sdf.format(cal.getTime()));
        cal.add(Calendar.MONTH, 1);
        ((TextView) findViewById(R.id.close_date)).setText(sdf.format(cal.getTime()));

        ((TextView) findViewById(R.id.password)).setTransformationMethod(PasswordTransformationMethod .getInstance());
        ((Spinner) findViewById(R.id.usable_spinner)).setAdapter(new UsableAdapter(this));

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
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean modify = getIntent().getBooleanExtra("modify", false);
        if (!modify) {
            getMenuInflater().inflate(R.menu.coupon_write, menu);
            menu.findItem(R.id.action_write).setOnMenuItemClickListener(this);
        }

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        Intent intent = new Intent(this, CouponListActivity.class);
        startActivityForResult(intent, 1234);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            getFields(data.getStringExtra("id"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getFields(String id) {

        RequestParams params = new RequestParams();
        params.put("region", "ko");

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(2000);
        client.post(this, "http://tourcoupon.net/api/coupon/ticket_data/" + id, params, onTicketLoadComplete);

        visibleProgressDialog(true);

    }


    private AsyncHttpResponseHandler onTicketLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {

            System.out.println(response);

            try {
                JSONObject object = new JSONObject(response);
                if (!object.getBoolean("success")) {
                    Toast.makeText(CouponAddActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    visibleProgressDialog(false);
                }
                else {
                    visibleProgressDialog(false);

                    JSONObject data = object.getJSONObject("data");

                    ((EditText) findViewById(R.id.password)).setText(data.getString("password"));
                    ((TextView) findViewById(R.id.start_date)).setText(data.getString("start_at").substring(0, 10));
                    ((TextView) findViewById(R.id.close_date)).setText(data.getString("close_at").substring(0, 10));

                    ((EditText) findViewById(R.id.title)).setText(data.getString("title"));
                    ((EditText) findViewById(R.id.discount)).setText(data.getString("discount"));
                    ((EditText) findViewById(R.id.description)).setText(data.getString("description"));

                }
            }
            catch (Exception ex) {
                Toast.makeText(CouponAddActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                visibleProgressDialog(false);
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            arg0.printStackTrace();
            System.out.println(arg1);
            Toast.makeText(CouponAddActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            visibleProgressDialog(false);
        }

    };

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.start_date || view.getId() == R.id.close_date) {

            String[] dates;
            try {
                dialogCaller = (TextView) view;
                dates = dialogCaller.getText().toString().split("-");
            }
            catch(Exception ex) {
                dates = sdf.format(new Date()).split("-");
            }

            DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
            dialog.show();

            return;
        }

        switch (view.getId()) {

            case R.id.confirm_button: {

                AppConfiguration conf = AppConfiguration.getInstance();

                RequestParams params = new RequestParams();

                params.put("region", "ko");
                params.put("u_username", conf.getUid());
                params.put("u_password", conf.getPassword());

                params.put("password", ((EditText) findViewById(R.id.password)).getText().toString() );
                params.put("start_at", ((TextView) findViewById(R.id.start_date)).getText().toString() );
                params.put("close_at", ((TextView) findViewById(R.id.close_date)).getText().toString() );

                params.put("title", ((EditText) findViewById(R.id.title)).getText().toString() );
                params.put("discount", ((EditText) findViewById(R.id.discount)).getText().toString() );
                params.put("description", ((EditText) findViewById(R.id.description)).getText().toString() );

                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(2000);

                boolean modify = getIntent().getBooleanExtra("modify", false);
                if (modify) {
                    client.post(this, "http://tourcoupon.net/api/coupon/modify/" + getIntent().getStringExtra("id"), params, onPostLoadComplete);
                }
                else {
                    client.post(this, "http://tourcoupon.net/api/coupon/create", params, onPostLoadComplete);
                }

                visibleProgressDialog(true);

                break;
            }
            case R.id.cancel_button:
                finish();
                break;

        }

    }


    private AsyncHttpResponseHandler onPostLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {

            System.out.println(response);

            try {
                visibleProgressDialog(false);
                JSONObject object = new JSONObject(response);
                if (!object.getBoolean("success")) {
                    Toast.makeText(CouponAddActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                }
                else {

                    boolean modify = getIntent().getBooleanExtra("modify", false);
                    String title, desc;
                    if (modify) {
                        title = "쿠폰수정 완료";
                        desc = "쿠폰이 수정되었습니다.\n" +
                                "번역을 원하시면, 번역신청(무료)을 해주세요.\n" +
                                "번역을 완료하여 반영한 다음\n" +
                                "쪽지로 알려드립니다\n";
                    }
                    else {
                        title = "쿠폰등록 완료";
                        desc = "쿠폰이 등록되었습니다.\n" +
                                "번역을 원하시면, 번역신청(무료)을 해주세요.\n" +
                                "번역을 완료하여 반영한 다음\n" +
                                "쪽지로 알려드립니다\n";
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(CouponAddActivity.this)
                            .setTitle(title)
                            .setMessage(desc)
                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).setPositiveButton("중국어 번역신청", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    visibleProgressDialog(true);
                                }
                            });

                    builder.create().show();

                }
            }
            catch (Exception ex) {
                Toast.makeText(CouponAddActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                visibleProgressDialog(false);
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            arg0.printStackTrace();
            System.out.println(arg1);
            Toast.makeText(CouponAddActivity.this, "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            visibleProgressDialog(false);
        }

    };


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (dialogCaller == null) return;

            dialogCaller.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
            dialogCaller = null;
        }
    };


}
