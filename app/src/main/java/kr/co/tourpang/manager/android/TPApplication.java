package kr.co.tourpang.manager.android;

import java.util.Random;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class TPApplication extends Application {

	public final static String API_URL = "http://api-content.musicdisk.fm/";
	
	private static Context appContext;
	public static DisplayImageOptions imgOption;
	public static Random random = new Random();
	
	@Override
	public void onCreate() {
		super.onCreate();

		appContext = this;
		random.nextInt();

		imgOption = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(false)
				.cacheOnDisc(true)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();

		if (!ImageLoader.getInstance().isInited()) {
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(appContext)
				.defaultDisplayImageOptions(imgOption)
				.build();
			ImageLoader.getInstance().init(config);
		}
	}
	
	public static Context getContext() {
		return appContext;
	}
	
	public static boolean isPhone() {
		return appContext.getResources().getString(R.string.screen_type).equals("phone");
	}
}
