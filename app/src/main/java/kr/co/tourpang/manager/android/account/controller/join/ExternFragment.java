package kr.co.tourpang.manager.android.account.controller.join;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.listeners.OnNextListener;

public class ExternFragment extends Fragment implements View.OnClickListener {

    private Integer id;
    private View rootView;
    private OnNextListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_join_store_d3, container, false);
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

                    RequestParams params = new RequestParams();
                    params.put("id", id.toString());
                    params.put("description", gt(rootView, R.id.description));
                    params.put("catchprice", gt(rootView, R.id.catchprice));
                    params.put("sales_percent", gt(rootView, R.id.sales_percent));
                    params.put("working_position", gt(rootView, R.id.working_position));
                    params.put("other_message", gt(rootView, R.id.other_message));

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
