package kr.co.tourpang.manager.android.account.controller;

import android.os.Bundle;
import android.view.MenuItem;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.ui.AnimActivity;

/**
 * Created by mixon on 2014. 6. 2..
 */
public class JoinForMarketActivity extends AnimActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_market);

        getSupportActionBar().setTitle("쿠폰영업 / 추천업체가입");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
