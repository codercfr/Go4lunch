package com.example.go4lunch.response;

import androidx.annotation.NonNull;

import com.example.go4lunch.model.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlacesResponse {

    @SerializedName("articles")
    @Expose
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @NotNull
    @Override
    public String toString() {
        return "BashboardNewsResponse{"+
                "article="+articles+'}';
    }
}
