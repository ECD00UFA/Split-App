package com.example.splitit.ui.dashBoard.dataModels;

public class GroupsTypeDataModel {
    int typeImg;
    String typeTxt;
    boolean isSelected;

    public int getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(int typeImg) {
        this.typeImg = typeImg;
    }

    public String getTypeTxt() {
        return typeTxt;
    }

    public void setTypeTxt(String typeTxt) {
        this.typeTxt = typeTxt;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public GroupsTypeDataModel(int typeImg, String typeTxt) {
        this.typeImg = typeImg;
        this.typeTxt = typeTxt;
    }
}
