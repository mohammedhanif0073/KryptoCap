package com.example.cryptotokenbaba.Model;

public class NewsModel {
public String image;
public String category;
public String headline;
public String summary;
public String url;
public NewsModel(String image, String category,String headline,String summary,String url){
    this.image = image;
    this.category = category;
    this.summary = summary;
    this.url = url;

}
    public String getImage(String image) {
        return this.image;
    }

    public String getCategory(String category){
        return this.category;
    }

    public String getHeadline(String headline){
    return this.headline;
    }

    public String getSummary(String summary){
        return this.summary;
    }

    public String getUrl(String url){
        return this.url;
    }


}
