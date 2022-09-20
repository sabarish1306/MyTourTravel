package com.example.mytourtravel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FarmerUploadResponse {

@SerializedName("result")
@Expose
private String result;
@SerializedName("product")
@Expose
private List<Product> product = null;

public String getResult() {
return result;
}

public void setResult(String result) {
this.result = result;
}

public List<Product> getProduct() {
return product;
}

public void setProduct(List<Product> product) {
this.product = product;
}

}