package kr.co.tourpang.manager.android.analytics.controller;

import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AnalyticsMainFragment extends Fragment implements IMainFragment {

	private View convertView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_analytics, container);
		return convertView;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "쿠폰통계";
	}

	@Override
	public String getPageTitle() {
		// TODO Auto-generated method stub
		return "쿠폰통계";
	}

	@Override
	public int getIcon() {
		return R.drawable.gray_statistics;
	}

	@Override
	public List<ImageButton> getButtons() {
		// TODO Auto-generated method stub
		return null;
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
