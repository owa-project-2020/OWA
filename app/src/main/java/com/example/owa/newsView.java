package com.example.owa;

public class newsView {
    public String date;
    public String title;
    public String article;
    public String category;

    public newsView() {
    }

    public newsView(String date, String title, String article, String category) {
        this.date = date;
        this.title = title;
        this.article = article;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
