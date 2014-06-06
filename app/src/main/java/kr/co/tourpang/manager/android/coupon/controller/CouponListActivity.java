package kr.co.tourpang.manager.android.coupon.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.coupon.controller.adapters.CouponAdapter;
import kr.co.tourpang.manager.android.coupon.controller.vo.CouponJSON;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.ui.AnimActivity;

/**
 * Created by mixon on 2014. 6. 4..
 */
public class CouponListActivity extends AnimActivity implements AdapterView.OnItemClickListener {

    private CouponAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);

        getSupportActionBar().setTitle("기존쿠폰 재등록");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_coupon);

        adapter = new CouponAdapter(this);
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter);
        ((ListView) findViewById(R.id.listview)).setOnItemClickListener(this);

        AppConfiguration conf = AppConfiguration.getInstance();
        RequestParams params = new RequestParams();

        params.put("region", "ko");
        params.put("type", "all");
        params.put("u_username", conf.getUid());
        params.put("u_password", conf.getPassword());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(2000);
        client.post(this, "http://tourcoupon.net/api/coupon/mytickets", params, onPostLoadComplete);
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private AsyncHttpResponseHandler onPostLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {

            System.out.println(response);

            try {
                JSONObject object = new JSONObject(response);
                if (!object.getBoolean("success")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<CouponJSON> items = new ArrayList<CouponJSON>();
                JSONArray data = object.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    items.add(new CouponJSON(data.getJSONObject(i)));
                }
                adapter.setCoupons(items);

            }
            catch (Exception ex) {
                Toast.makeText(getActivity(), "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            Toast.makeText(getActivity(), "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }

    };

    private Activity getActivity() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("id", adapter.getItem(i).getId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}