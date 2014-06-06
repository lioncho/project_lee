package kr.co.tourpang.manager.android.coupon.controller.vo;

import org.json.JSONObject;

/**
 * Created by mixon on 2014. 6. 3..
 */
public class CouponJSON {

    private String id;
    private String store;
    private String password;
    private String title;
    private String start_at;
    private String close_at;
    private String discount;

    public CouponJSON(JSONObject object) throws Exception {
        if (object.has("id")) id = object.getString("id");
        if (object.has("store")) store = object.getString("store");
        if (object.has("password")) password = object.getString("password");
        if (object.has("title")) title = object.getString("title");
        if (object.has("start_at")) start_at = object.getString("start_at");
        if (object.has("close_at")) close_at = object.getString("close_at");
        if (object.has("discount")) discount = object.getString("discount");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getClose_at() {
        return close_at;
    }

    public void setClose_at(String close_at) {
        this.close_at = close_at;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
