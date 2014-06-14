package kr.co.tourpang.manager.android.account.adpaters;

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
public class LangAdapter extends BaseAdapter {

    private Context context;
    private List<LangVO> items;
    public LangAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<LangVO>();
        this.items.add(new LangVO("ko", "한국어"));
//        this.items.add("China (Simple)");
//        this.items.add("Japan");
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public LangVO getItem(int i) {
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


    public class LangVO {
        private String langcode;
        private String label;

        public LangVO(String langcode, String label) {
            this.langcode = langcode;
            this.label = label;
        }

        public String getLangcode() {
            return langcode;
        }

        public void setLangcode(String langcode) {
            this.langcode = langcode;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
