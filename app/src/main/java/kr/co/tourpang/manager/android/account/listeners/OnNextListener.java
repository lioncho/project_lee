package kr.co.tourpang.manager.android.account.listeners;

/**
 * Created by mixon on 2014. 6. 2..
 */
public interface OnNextListener {

    void showProgress();
    void hideProgress();
    void nextView();
    void prevView();
    void setRID(int id);

}
