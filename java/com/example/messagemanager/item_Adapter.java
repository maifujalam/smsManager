package com.example.messagemanager;


public class item_Adapter
{
 int img;
 String title1,body1,time1;

    public item_Adapter(int img, String title1, String body1, String time1) {
        this.img = img;
        this.title1 = title1;
        this.body1 = body1;
        this.time1 = time1;
    }

    public int getImg() {
        return img;
    }

    public String getTitle1() {
        return title1;
    }

    public String getBody1() {
        return body1;
    }

    public String getTime1() {
        return time1;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public void setBody1(String body1) {
        this.body1 = body1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }
}
