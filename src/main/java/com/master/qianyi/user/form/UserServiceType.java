package com.master.qianyi.user.form;

public class UserServiceType {

    private Long id;

    private String name;

    private String keyWords;

    private String imgPath;

    public UserServiceType() {
    }

    public UserServiceType(Long id, String name, String keyWords, String imgPath) {
        this.id = id;
        this.name = name;
        this.keyWords = keyWords;
        this.imgPath = imgPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
