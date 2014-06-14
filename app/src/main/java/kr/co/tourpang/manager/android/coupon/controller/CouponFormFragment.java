package kr.co.tourpang.manager.android.coupon.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CouponFormFragment extends Fragment implements View.OnClickListener {

    private View rview;
    private TextView dialogCaller;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private OnCouponFormListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rview = inflater.inflate(R.layout.fragment_coupon_form, container, false);

        rview.findViewById(R.id.start_date).setOnClickListener(this);
        rview.findViewById(R.id.close_date).setOnClickListener(this);

        rview.findViewById(R.id.confirm_button).setOnClickListener(this);
        rview.findViewById(R.id.cancel_button).setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        ((TextView) rview.findViewById(R.id.start_date)).setText(sdf.format(cal.getTime()));
        cal.add(Calendar.MONTH, 1);
        ((TextView) rview.findViewById(R.id.close_date)).setText(sdf.format(cal.getTime()));


        ((Spinner) rview.findViewById(R.id.usable_spinner)).setAdapter(new UsableAdapter(getActivity()));


        return rview;
    }

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

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), mDateSetListener, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
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

                params.put("password", ((EditText) rview.findViewById(R.id.password)).getText().toString() );
                params.put("start_at", ((TextView) rview.findViewById(R.id.start_date)).getText().toString() );
                params.put("close_at", ((TextView) rview.findViewById(R.id.close_date)).getText().toString() );

                params.put("title", ((EditText) rview.findViewById(R.id.title)).getText().toString() );
                params.put("discount", ((EditText) rview.findViewById(R.id.discount)).getText().toString() );
                params.put("description", ((EditText) rview.findViewById(R.id.description)).getText().toString() );

                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(2000);
                client.post(getActivity(), "http://tourcoupon.net/api/coupon/create", params, onPostLoadComplete);

                if (listener != null) listener.showProgress();

                break;
            }
            case R.id.cancel_button:
                if (listener != null) listener.registerOK();
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
                    listener.registerOK();
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

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (dialogCaller == null) return;

            dialogCaller.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
            dialogCaller = null;
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnCouponFormListener) activity;
        }
        catch(Exception ex) {}
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface  OnCouponFormListener {
        void showProgress();
        void hideProgress();
        void registerOK();

    }


}
