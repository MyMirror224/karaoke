package com.example.minhnhat_bai14_tabselector2;

public class BaiHat {
    private String mabai;
    private String tenBai;
    private int Like;

    public String getMabai() {
        return mabai;
    }

    public void setMabai(String mabai) {
        this.mabai = mabai;
    }

    public String getTenBai() {
        return tenBai;
    }

    public void setTenBai(String tenBai) {
        this.tenBai = tenBai;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        this.Like = like;
    }

    public BaiHat() {
    }

    public BaiHat(String mabai, String tenBai, int like) {
        this.mabai = mabai;
        this.tenBai = tenBai;
        this.Like = like;
    }


}
