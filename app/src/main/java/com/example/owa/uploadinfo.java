package com.example.owa;

class uploadinfo {

    public String imageName;
    public String imageURL;

    public uploadinfo() {
    }

    public uploadinfo(String name, String url) {
        this.imageName = name;
        this.imageURL = url;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

}
