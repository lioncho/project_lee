package kr.co.tourpang.manager.android.ui;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.adapter.IMainFragment;
import kr.co.tourpang.manager.android.ui.adapter.SectionsPagerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentActivity extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener {

	private ActionBar actionBar;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private boolean isStart;
	private boolean isFirstClick;
    private List<RadioButton> radioButtons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(listener);

        actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);

        ((RadioGroup) findViewById(R.id.tab_group)).setOnCheckedChangeListener(this);

        radioButtons = new ArrayList<RadioButton>();
        radioButtons.add((RadioButton) findViewById(R.id.tab_01));
        radioButtons.add((RadioButton) findViewById(R.id.tab_02));
        radioButtons.add((RadioButton) findViewById(R.id.tab_03));
        radioButtons.add((RadioButton) findViewById(R.id.tab_04));

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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        mViewPager.setCurrentItem(radioButtons.indexOf(findViewById(i)));
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
            radioButtons.get(position).setChecked(true);
			actionBar.setTitle(mSectionsPagerAdapter.getPageTitle(position));
			actionBar.setDisplayHomeAsUpEnabled(mSectionsPagerAdapter.hasDepth(position));

		}
	};

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


}
