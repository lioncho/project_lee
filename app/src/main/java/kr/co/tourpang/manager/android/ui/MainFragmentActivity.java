package kr.co.tourpang.manager.android.ui;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;
import kr.co.tourpang.manager.android.ui.adapter.SectionsPagerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentActivity extends ActionBarActivity implements ActionBar.TabListener {

	private ActionBar actionBar;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private boolean isStart;
	private boolean isFirstClick;
    private Menu menuManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(listener);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayUseLogoEnabled(false);

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getTitle(i)).setTabListener(this));
        }

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!isStart) {
			listener.onPageSelected(0);
			isStart = true;
		}
	}


    @Override
	protected void onPause() {
		super.onPause();
	}

	public void reloadTitle() {
		listener.onPageSelected(mViewPager.getCurrentItem());
	}

	private ViewPager.SimpleOnPageChangeListener listener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {

            actionBar.setSelectedNavigationItem(position);

            actionBar.setIcon(mSectionsPagerAdapter.getPageIcon(position));
            actionBar.setTitle(mSectionsPagerAdapter.getPageTitle(position));
            actionBar.setSubtitle(mSectionsPagerAdapter.getPageDesc(position));
			actionBar.setDisplayHomeAsUpEnabled(mSectionsPagerAdapter.hasDepth(position));

            if (menuManager != null) {
                menuManager.clear();
                mSectionsPagerAdapter.getMenu(position, getMenuInflater(), menuManager);
            }

		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuManager = menu;
        this.menuManager.clear();
        mSectionsPagerAdapter.getMenu(mViewPager.getCurrentItem(), getMenuInflater(), menuManager);
        return true;
    }

    @Override
	public void onBackPressed() {
		int position = mViewPager.getCurrentItem();
		if (mSectionsPagerAdapter.hasDepth(position)) {
			((IMainFragment) mSectionsPagerAdapter.getItem(position)).back();
			return;
		} else if (!isFirstClick) {
			Toast.makeText(this, "정말 종료?", Toast.LENGTH_SHORT).show();
			isFirstClick = true;
			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					isFirstClick = false;
				}
			}, 3000);
			return;
		}
		super.onBackPressed();
	}

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
