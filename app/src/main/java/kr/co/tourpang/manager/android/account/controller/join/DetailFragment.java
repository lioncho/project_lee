package kr.co.tourpang.manager.android.account.controller.join;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.listeners.OnNextListener;

/**
 * Created by mixon on 2014. 6. 2..
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    private Integer id;
    private View rootView;
    private OnNextListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_join_store_d2, container, false);
            rootView.findViewById(R.id.prev_button).setOnClickListener(this);
            rootView.findViewById(R.id.next_button).setOnClickListener(this);
        }
        return rootView;
    }

    public void setOnNextListener(OnNextListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.prev_button:
                if (this.listener != null) {
                    this.listener.prevView();
                }
                break;
            case R.id.next_button:
                if (this.listener != null && validate()) {

                    int sid = ((RadioGroup) rootView.findViewById(R.id.wifi_usable)).getCheckedRadioButtonId();


                    RequestParams params = new RequestParams();
                    params.put("id", id.toString());
                    params.put("company_name", gt(rootView, R.id.company_name));
                    params.put("address", gt(rootView, R.id.address));
                    params.put("phone", gt(rootView, R.id.phone));
                    params.put("working_time", gt(rootView, R.id.working_time));
                    params.put("stay_time", gt(rootView, R.id.stay_time));
                    params.put("website", gt(rootView, R.id.website));
                    params.put("wifi_usable", rootView.findViewById(sid).getTag().toString());
                    params.put("support_language", gt(rootView, R.id.support_language));
                    params.put("support_sns", gt(rootView, R.id.support_sns));

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(2000);
                    client.post(getActivity(), "http://tourcoupon.net/api/mgr_authorize/signup_d2", params, onPostLoadComplete);

                    this.listener.showProgress();
                }
                break;
        }

    }

    private AsyncHttpResponseHandler onPostLoadComplete = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {
            System.out.println(response);
            try {
                JSONObject object = new JSONObject(response);
                if (!object.getBoolean("success")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    listener.hideProgress();
                }
                else {
                    id = object.getInt("data");
                    listener.nextView();
                }
            }
            catch (Exception ex) {
                Toast.makeText(getActivity(), "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                listener.hideProgress();
                ex.printStackTrace();
            }

        }

        @Override
        public void onFailure(Throwable arg0, String arg1) {
            Toast.makeText(getActivity(), "서버와 통신이 올바르지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            listener.hideProgress();
        }

    };


    private boolean validate() {

        if (gt(rootView, R.id.company_name) == null || gt(rootView, R.id.company_name).isEmpty()) {
            Toast.makeText(getActivity(), "기업명을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.address) == null || gt(rootView, R.id.address).isEmpty()) {
            Toast.makeText(getActivity(), "주소를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.phone) == null || gt(rootView, R.id.phone).isEmpty()) {
            Toast.makeText(getActivity(), "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.working_time) == null || gt(rootView, R.id.working_time).isEmpty()) {
            Toast.makeText(getActivity(), "업무시간을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.stay_time) == null || gt(rootView, R.id.stay_time).isEmpty()) {
            Toast.makeText(getActivity(), "휴무일을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.website) == null || gt(rootView, R.id.website).isEmpty()) {
            Toast.makeText(getActivity(), "웹사이트를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public String gt(View view, int id) {
        CharSequence cs = ((TextView) view.findViewById(id)).getText();
        return (cs == null ? null : cs.toString());
    }

    public Integer getRID() {
        return id;
    }

    public void setRID(Integer id) {
        this.id = id;
    }
}
