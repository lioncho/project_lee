package kr.co.tourpang.manager.android.ui.adapter;

import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;

public interface IMainFragment {

	String getTitle();

    int getPageIcon();
	String getPageTitle();
    String getPageDesc();

	boolean hasDepth();
	void back();

    void getMenu(MenuInflater inflater, Menu menu);
	
}
