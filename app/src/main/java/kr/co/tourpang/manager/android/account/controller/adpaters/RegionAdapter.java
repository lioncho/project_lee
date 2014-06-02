package kr.co.tourpang.manager.android.account.controller.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.co.tourpang.manager.android.R;

/**
 * Created by mixon on 2014. 6. 2..
 */
public class RegionAdapter extends BaseAdapter {

    private Context context;
    private List<String> items;
    public RegionAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<String>();
        this.items.add("Korea");
//        this.items.add("China");
//        this.items.add("Japan");
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
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

        ((TextView) convertView.findViewById(R.id.textView)).setText(items.get(i));
        return convertView;
    }
}
