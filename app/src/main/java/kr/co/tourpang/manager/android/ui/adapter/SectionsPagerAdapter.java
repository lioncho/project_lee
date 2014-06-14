package kr.co.tourpang.manager.android.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.analytics.controller.AnalyticsMainFragment;
import kr.co.tourpang.manager.android.company.controller.CompanyMainFragment;
import kr.co.tourpang.manager.android.coupon.controller.CouponMainFragment;
import kr.co.tourpang.manager.android.helpers.AppConfiguration;
import kr.co.tourpang.manager.android.setting.controller.SettingMainFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments = new ArrayList<Fragment>();

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);

        AppConfiguration conf = AppConfiguration.getInstance();

		fragments.add(new CouponMainFragment());
		fragments.add(new CompanyMainFragment());
		fragments.add(new AnalyticsMainFragment());
		fragments.add(new SettingMainFragment());
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public CharSequence getTitle(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return null;
		else
			return fragment.getTitle();
	}

    public int getPageIcon(int position) {
        IMainFragment fragment = (IMainFragment) fragments.get(position);
        if (fragment == null)
            return R.drawable.transparent;
        else
            return fragment.getPageIcon();
    }


    @Override
	public CharSequence getPageTitle(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return null;
		else
			return fragment.getPageTitle();
	}

    public CharSequence getPageDesc(int position) {
        IMainFragment fragment = (IMainFragment) fragments.get(position);
        if (fragment == null)
            return null;
        else
            return fragment.getPageDesc();
    }


	public boolean hasDepth(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return false;
		else
			return fragment.hasDepth();
	}

    public void getMenu(int position, MenuInflater inflater, Menu menu) {
        IMainFragment fragment = (IMainFragment) fragments.get(position);
        if (fragment == null) return;
        fragment.getMenu(inflater, menu);
    }

}
