package kr.co.tourpang.manager.android.company.controller;

import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class CompanyMainFragment extends Fragment implements IMainFragment {

	private View convertView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_company, container, false);

        AppConfiguration conf = AppConfiguration.getInstance();

        RequestParams params = new RequestParams();
        params.put("username", conf.getUid());
        params.put("password", conf.getPassword());
        params.put("lang_code", "ko");

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(2000);
        client.post(getActivity(), "http://tourcoupon.net/api/mgr_authorize/get", params, onPostLoadComplete);

        return convertView;
	}

    private AsyncHttpResponseHandler onPostLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {
            System.out.println(response);

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    System.out.println(object);
                    JSONObject data = object.getJSONObject("data");
                    ct(R.id.company_name, data.getString("company_name"));
                    ct(R.id.address, data.getString("address"));
                    ct(R.id.phone, data.getString("phone"));
                    ct(R.id.working_position, data.getString("working_position"));
                    ct(R.id.working_time, data.getString("working_time"));
                    ct(R.id.stay_time, data.getString("stay_time"));
                    ct(R.id.website, data.getString("website"));
                    ct(R.id.support_language, data.getString("support_language"));
                    ct(R.id.support_sns, data.getString("support_sns"));
                    ct(R.id.description, data.getString("description"));
                    ct(R.id.catchprice, data.getString("catchprice"));
                    ct(R.id.sales_percent, data.getString("sales_percent"));
                    ct(R.id.other_message, data.getString("other_message"));
                }
            }
            catch (Exception ex) {
                Toast.makeText(getActivity(), "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            Toast.makeText(getActivity(), "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    public int getPageIcon() {
        return R.drawable.logo_company;
    }

    @Override
	public String getTitle() {
		return "기업정보";
	}

	@Override
	public String getPageTitle() {
		return "기업정보";
	}

    @Override
    public String getPageDesc() {
        AppConfiguration conf = AppConfiguration.getInstance();
        return conf.getUsername();
    }

    @Override
    public void getMenu(MenuInflater inflater, Menu menu) {

    }

    @Override
	public boolean hasDepth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub

	}

    private void ct(int id, String text) {
        ((TextView) convertView.findViewById(id)).setText(text);
    }

}
