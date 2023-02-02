package com.example.splitit.ui.dashBoard.dataModels;

import com.example.splitit.app.BaseViewModel;

public class ActivityDataModel {
    int activityImg;
    String activityDetailTxt;
    String activityTimeTxt;
    String moneyDetailTxt;

    public ActivityDataModel(int activityImg, String activityDetailTxt, String activityTimeTxt, String moneyDetailTxt) {
        this.activityImg = activityImg;
        this.activityDetailTxt = activityDetailTxt;
        this.activityTimeTxt = activityTimeTxt;
        this.moneyDetailTxt = moneyDetailTxt;
    }

    public int getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(int activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityDetailTxt() {
        return activityDetailTxt;
    }

    public void setActivityDetailTxt(String activityDetailTxt) {
        this.activityDetailTxt = activityDetailTxt;
    }

    public String getActivityTimeTxt() {
        return activityTimeTxt;
    }

    public void setActivityTimeTxt(String activityTimeTxt) {
        this.activityTimeTxt = activityTimeTxt;
    }

    public String getMoneyDetailTxt() {
        return moneyDetailTxt;
    }

    public void setMoneyDetailTxt(String moneyDetailTxt) {
        this.moneyDetailTxt = moneyDetailTxt;
    }
}
