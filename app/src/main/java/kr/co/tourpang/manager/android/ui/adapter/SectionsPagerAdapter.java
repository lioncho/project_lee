package kr.co.tourpang.manager.android.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import kr.co.tourpang.manager.android.analytics.controller.AnalyticsMainFragment;
import kr.co.tourpang.manager.android.company.controller.CompanyMainFragment;
import kr.co.tourpang.manager.android.coupon.controller.CouponMainFragment;
import kr.co.tourpang.manager.android.setting.controller.SettingMainFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageButton;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments = new ArrayList<Fragment>();

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
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

	@Override
	public CharSequence getPageTitle(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return null;
		else
			return fragment.getPageTitle();
	}

	public int getPageIcon(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return -1;
		else
			return fragment.getIcon();
	}

	public boolean hasDepth(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return false;
		else
			return fragment.hasDepth();
	}

	public List<ImageButton> getButtons(int position) {
		IMainFragment fragment = (IMainFragment) fragments.get(position);
		if (fragment == null)
			return null;
		else
			return fragment.getButtons();

	}

}
