package kr.co.tourpang.manager.android.coupon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.co.tourpang.manager.android.R;

/**
 * Created by mixon on 2014. 6. 2..
 */
public class UsableAdapter extends BaseAdapter {

    private Context context;
    private List<UsableVO> items;
    public UsableAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<UsableVO>();
        this.items.add(new UsableVO("IG", "이미지"));
        this.items.add(new UsableVO("QR", "QR Code"));
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public UsableVO getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_spinner_text, null, false);
        }

        ((TextView) convertView.findViewById(R.id.textView)).setText(items.get(i).getLabel());
        return convertView;
    }

    public class UsableVO {
        private String id;
        private String label;

        public UsableVO(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
