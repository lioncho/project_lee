package kr.co.tourpang.manager.android.account.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import kr.co.tourpang.manager.android.R;
import kr.co.tourpang.manager.android.account.controller.join.BasicFragment;
import kr.co.tourpang.manager.android.account.controller.join.DetailFragment;
import kr.co.tourpang.manager.android.account.controller.join.ExternFragment;
import kr.co.tourpang.manager.android.account.controller.listeners.OnNextListener;
import kr.co.tourpang.manager.android.ui.AnimActivity;

/**
 * Created by mixon on 2014. 6. 2..
 */
public class JoinForStoreActivity extends AnimActivity implements OnNextListener {

    private int currentIndex;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_store);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        getSupportActionBar().setTitle("기업회원가입");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentIndex = 0;

        BasicFragment basicFragment   = (BasicFragment) getSupportFragmentManager().findFragmentById(R.id.depth01);
        basicFragment.setOnNextListener(this);

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.depth02);
        detailFragment.setOnNextListener(this);

        ExternFragment externFragment = (ExternFragment) getSupportFragmentManager().findFragmentById(R.id.depth03);
        externFragment.setOnNextListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        super.visibleProgressDialog(true);
    }

    @Override
    public void hideProgress() {
        super.visibleProgressDialog(false);
    }

    @Override
    public void nextView() {
        if (currentIndex == 2) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("기업회원 가입완료")
                    .setMessage("기업회원 가입이 완료되었습니다.\n" +
                            "번역을 원하시면, 번역신청(무료)을 해주세요.\n" +
                            "번역을 완료하여 반영한 다음\n" +
                            "쪽지로 알려드립니다")
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setPositiveButton("중국어 번역신청", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            visibleProgressDialog(true);
                        }
                    });

            builder.create().show();
        }
        else {
            super.visibleProgressDialog(false);
            viewFlipper.showNext();
            currentIndex++;
        }
    }

    @Override
    public void setRID(int id) {
        BasicFragment basicFragment   = (BasicFragment) getSupportFragmentManager().findFragmentById(R.id.depth01);
        basicFragment.setRID(id);

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.depth02);
        detailFragment.setRID(id);

        ExternFragment externFragment = (ExternFragment) getSupportFragmentManager().findFragmentById(R.id.depth03);
        externFragment.setRID(id);
    }

    @Override
    public void prevView() {
        super.visibleProgressDialog(false);
        viewFlipper.showPrevious();
        currentIndex--;
    }
}
