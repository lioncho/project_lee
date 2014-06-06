package kr.co.tourpang.manager.android.analytics.controller;

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
import android.widget.TextView;

public class AnalyticsMainFragment extends Fragment implements IMainFragment {

	private View convertView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_analytics, container, false);

        AppConfiguration conf = AppConfiguration.getInstance();
        ((TextView) convertView.findViewById(R.id.display_name_lbl)).setText(conf.getUsername());

		return convertView;
	}

	@Override
	public String getTitle() {
		return "쿠폰통계";
	}

    @Override
    public int getPageIcon() {
        return R.drawable.logo_static;
    }

	@Override
	public String getPageTitle() {
		return "쿠폰통계";
	}

    @Override
    public String getPageDesc() {
        return null;
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

}
