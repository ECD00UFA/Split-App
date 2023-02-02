package com.example.splitit.ui.dashBoard.dataModels;

public class ExpensesDataModel {
    int expenseImg;
    String groupNameTxt;
    String expensesTxt;


    public ExpensesDataModel(int expenseImg, String groupNameTxt, String expensesTxt) {
        this.expenseImg = expenseImg;
        this.groupNameTxt = groupNameTxt;
        this.expensesTxt = expensesTxt;
    }

    public int getExpenseImg() {
        return expenseImg;
    }

    public void setExpenseImg(int expenseImg) {
        this.expenseImg = expenseImg;
    }

    public String getGroupNameTxt() {
        return groupNameTxt;
    }

    public void setGroupNameTxt(String groupNameTxt) {
        this.groupNameTxt = groupNameTxt;
    }

    public String getExpensesTxt() {
        return expensesTxt;
    }

    public void setExpensesTxt(String expensesTxt) {
        this.expensesTxt = expensesTxt;
    }

}
