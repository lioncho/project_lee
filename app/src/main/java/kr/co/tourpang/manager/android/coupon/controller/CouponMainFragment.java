package kr.co.tourpang.manager.android.coupon.controller;

import java.util.ArrayList;
import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.coupon.adapters.CouponAdapter;
import kr.co.tourpang.manager.android.coupon.vo.CouponJSON;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class CouponMainFragment extends Fragment implements IMainFragment, View.OnClickListener, MenuItem.OnMenuItemClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

	private View convertView;
    private CouponAdapter adapter;
    private String type = "avail";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_coupon, container, false);

        adapter = new CouponAdapter(getActivity());
        ((ListView) convertView.findViewById(R.id.listview)).setAdapter(adapter);
        ((ListView) convertView.findViewById(R.id.listview)).setOnItemClickListener(this);

        ((RadioGroup) convertView.findViewById(R.id.coupon_type)).setOnCheckedChangeListener(this);

		return convertView;
	}

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        type = convertView.findViewById(i).getTag().toString();
        this.loadRecord();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadRecord();
    }

    private void loadRecord() {

        convertView.findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);

        AppConfiguration conf = AppConfiguration.getInstance();
        RequestParams params = new RequestParams();

        params.put("region", "ko");
        params.put("type", type);
        params.put("u_username", conf.getUid());
        params.put("u_password", conf.getPassword());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(2000);
        client.post(getActivity(), "http://tourcoupon.net/api/coupon/mytickets", params, onPostLoadComplete);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), CouponAddActivity.class);
        intent.putExtra("modify", true);
        intent.putExtra("id", adapter.getItem(i).getId());
        startActivity(intent);
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

                convertView.findViewById(R.id.loading_indicator).setVisibility(View.GONE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.add_coupon: {
//            }
        }
    }

    @Override
    public int getPageIcon() {
        return R.drawable.logo_coupon;
    }

    @Override
	public String getTitle() {
		return "쿠폰관리";
	}

	@Override
	public String getPageTitle() {
		return "쿠폰관리";
	}

    @Override
    public String getPageDesc() {
        AppConfiguration conf = AppConfiguration.getInstance();
        return conf.getUsername();
    }

    @Override
    public void getMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.coupon, menu);
        menu.findItem(R.id.action_write).setOnMenuItemClickListener(this);
    }

    @Override
	public boolean hasDepth() {
		return false;
	}

	@Override
	public void back() {
	}

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent intent = new Intent(getActivity(), CouponAddActivity.class);
        startActivity(intent);
        return false;
    }
}
