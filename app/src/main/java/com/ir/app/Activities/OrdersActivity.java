package com.ir.app.Activities;

public class OrdersActivity {
    private int ImageRsc;
    private String mtext, mText1, mText2;

    public OrdersActivity(int imageRsc, String mtext, String mText1 , String mText2){
        this.ImageRsc=imageRsc;
        this.mtext=mtext;
        this.mText1=mText1;
        this.mText2=mText2;
    }

    public int getImageRsc()
    {
        return ImageRsc;
    }
    public String getMtext()
    {
        return mtext;
    }
    public  String getmText1() {return mText1;}
    public String getmText2() {
        return mText2;
    }
}
