package kr.co.tourpang.manager.android.coupon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.coupon.vo.CouponJSON;

/**
 * Created by mixon on 2014. 6. 3..
 */
public class CouponAdapter extends BaseAdapter {

    private Context context;
    private List<CouponJSON> coupons;

    public CouponAdapter(Context context) {
        this.context = context;
    }

    public void setCoupons(List<CouponJSON> coupons) {
        this.coupons = coupons;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (this.coupons == null ? 0 : this.coupons.size());
    }

    @Override
    public CouponJSON getItem(int i) {
        return (coupons == null ? null : coupons.get(i));
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(getItem(i).getId());
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_coupon, null, false);
        }

        ((TextView) convertView.findViewById(R.id.textView)).setText(coupons.get(i).getTitle());
        String date = coupons.get(i).getStart_at().substring(0, 10) + " ~ " + coupons.get(i).getClose_at().substring(0, 10);
        ((TextView) convertView.findViewById(R.id.date)).setText(date);
        ((TextView) convertView.findViewById(R.id.discount)).setText(coupons.get(i).getDiscount());
        return convertView;
    }
}
