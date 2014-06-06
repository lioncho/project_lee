package kr.co.tourpang.manager.android.setting.controller;

import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    public int getPageIcon() {
        return R.drawable.logo_setting;
    }

	@Override
	public String getPageTitle() {
		return "설정";
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
		return false;
	}

	@Override
	public void back() {

	}

}
