package kr.co.tourpang.manager.android.ui.adapter;

import java.util.List;

import android.widget.ImageButton;

public interface IMainFragment {

	String getTitle();
	String getPageTitle();
	int getIcon();
	List<ImageButton> getButtons();
	boolean hasDepth();
	void back();
	
}
