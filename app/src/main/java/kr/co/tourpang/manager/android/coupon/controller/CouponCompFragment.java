package kr.co.tourpang.manager.android.coupon.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;

import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;

/**
 * Created by mixon on 2014. 6. 10..
 */
public class CouponCompFragment extends Fragment implements IMainFragment {

    @Override
    public String getTitle() {
        return "쿠폰관리";
    }

    @Override
    public int getPageIcon() {
        return 0;
    }

    @Override
    public String getPageTitle() {
        return null;
    }

    @Override
    public String getPageDesc() {
        return null;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public void back() {

    }

    @Override
    public void getMenu(MenuInflater inflater, Menu menu) {

    }
}
