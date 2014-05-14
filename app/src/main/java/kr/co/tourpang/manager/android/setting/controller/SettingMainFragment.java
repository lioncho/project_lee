package kr.co.tourpang.manager.android.setting.controller;

import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class SettingMainFragment extends Fragment implements IMainFragment {

	private View convertView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_setting, container, false);
		return convertView;
	}
	@Override
	public String getTitle() {
        return "설정";
	}

	@Override
	public String getPageTitle() {
		return "설정";
	}

	@Override
	public int getIcon() {
		return R.drawable.gray_set;
	}

	@Override
	public List<ImageButton> getButtons() {
		return null;
	}

	@Override
	public boolean hasDepth() {
		return false;
	}

	@Override
	public void back() {

	}

}
