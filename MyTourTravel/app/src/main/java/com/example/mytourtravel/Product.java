package com.example.mytourtravel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

@SerializedName("pid")
@Expose
private String pid;
@SerializedName("loc_name")
@Expose
private String lname;
@SerializedName("img_desc")
@Expose
private String imgdesc;
@SerializedName("img_path")
@Expose
private String impath;
@SerializedName("img_name")
@Expose
private String imname;

public String getPid() {
return pid;
}

public void setPid(String pid) {
this.pid = pid;
}

public String getlname() {
return lname;
}

public void setlname(String llname) {
this.lname = lname;
}

public String getimdesc() {
return imgdesc;
}

public void setimdesc(String imdessc) {
this.imgdesc = imdessc;
}

public String getimpath() {
return imgdesc;
}

public void setimpath(String impathh) {
this.impath = impath;
}

public String getImgpath() {
return impath;
}

public void setimname(String imnamee) {
this.imname = imname;
}

}