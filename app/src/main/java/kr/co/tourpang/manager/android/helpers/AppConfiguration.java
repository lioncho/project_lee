package kr.co.tourpang.manager.android.helpers;

import kr.co.tourpang.manager.android.TPApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppConfiguration {

	private static AppConfiguration _instance = null;

	public static AppConfiguration getInstance() {
		if (_instance == null) _instance = new AppConfiguration();
		if (_instance.modified) _instance.save();
		return _instance;
	}

	private boolean modified;
	private Context context;
	private SharedPreferences pm;

	public AppConfiguration() {
		this.context = TPApplication.getContext();
		this.pm = PreferenceManager.getDefaultSharedPreferences(this.context);
		if (!pm.getBoolean("autologin", false)) this.settingRemoveAll();
		this.refresh();
	}

    private String id;
	private String uid;
	private String signname;
	private String username;
    private String password;
    private String langcode;
	private boolean autologin;

	private void refresh() {
        this.id = this.pm.getString("id", "");
		this.uid = this.pm.getString("uid", "");
		this.signname = this.pm.getString("signname", "");
		this.username = this.pm.getString("username", "");
        this.password = this.pm.getString("password", "");
        this.langcode = this.pm.getString("langcode", "");
		this.autologin = this.pm.getBoolean("autologin", false);
	}

	public void save() {
		SharedPreferences.Editor editor = pm.edit();

        editor.putString("id", id);
		editor.putString("uid", uid);
		editor.putString("signname", signname);
		editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("langcode", langcode);
		editor.putBoolean("autologin", autologin);

		editor.commit();
		this.modified = false;
	}

	public void settingRemoveAll() {
		SharedPreferences.Editor editor = pm.edit();
		
		editor.clear();
		editor.commit();

        this.id = null;
		this.uid = null;
		this.signname = null;
		this.username = null;
        this.password = null;
        this.langcode = null;
		this.autologin = false;

		this.modified = false;
	}

	public void removeString(String key) {
		SharedPreferences.Editor editor = pm.edit();
		editor.remove(key);
		editor.commit();
		this.modified = false;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.modified = true;
    }

    public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
		this.modified = true;
	}

	public String getSignname() {
		return signname;
	}

	public void setSignname(String signname) {
		this.signname = signname;
		this.modified = true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.modified = true;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.modified = true;
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public boolean isAutologin() {
		return autologin;
	}

	public void setAutologin(boolean autologin) {
		this.autologin = autologin;
		this.modified = true;
	}


}