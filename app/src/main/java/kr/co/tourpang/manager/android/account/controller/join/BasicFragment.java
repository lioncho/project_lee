package kr.co.tourpang.manager.android.account.controller.join;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.controller.adpaters.RegionAdapter;
import kr.co.tourpang.manager.android.account.controller.listeners.OnNextListener;

public class BasicFragment extends Fragment implements View.OnClickListener {

    private Integer id;
    private View rootView;
    private OnNextListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_join_store_d1, container, false);
            rootView.findViewById(R.id.next_button).setOnClickListener(this);

            RegionAdapter adapter = new RegionAdapter(this.getActivity());
            ((Spinner) rootView.findViewById(R.id.region_spinner)).setAdapter(adapter);

        }
        return rootView;
    }

    public void setOnNextListener(OnNextListener listener) {
        this.listener = listener;
    }

    private boolean validate() {

        if (gt(rootView, R.id.username) == null || "".equals(gt(rootView, R.id.username))) {
            Toast.makeText(getActivity(), "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.password) == null || "".equals(gt(rootView, R.id.password))) {
            Toast.makeText(getActivity(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.password_confirm) == null || "".equals(gt(rootView, R.id.password_confirm))) {
            Toast.makeText(getActivity(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!gt(rootView, R.id.password).equals(gt(rootView, R.id.password_confirm))) {
            Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gt(rootView, R.id.display_name) == null || "".equals(gt(rootView, R.id.display_name))) {
            Toast.makeText(getActivity(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.next_button:
                if (this.listener != null && validate()) {

                    RequestParams params = new RequestParams();
                    if (id != null) params.put("id", id.toString());
                    params.put("type", "company");
                    params.put("username", gt(rootView, R.id.username));
                    params.put("password", gt(rootView, R.id.password));
                    params.put("region", String.valueOf(((Spinner) rootView.findViewById(R.id.region_spinner)).getSelectedItem()) );
                    params.put("display_name", gt(rootView, R.id.display_name));

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(2000);
                    client.post(getActivity(), "http://tourcoupon.net/api/mgr_authorize/signup_d1", params, onPostLoadComplete);

                    this.listener.showProgress();
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
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    listener.hideProgress();
                }
                else {
                    id = object.getInt("data");
                    listener.setRID(id);
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
