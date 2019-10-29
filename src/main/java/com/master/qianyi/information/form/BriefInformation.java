package com.master.qianyi.information.form;

public class BriefInformation {

    private String infoId;

    private String infoTitle;

    private String infoWriter;

    private String infoImgPath;

    private Long infoViewTimes;

    private String publishedTime;


    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoWriter() {
        return infoWriter;
    }

    public void setInfoWriter(String infoWriter) {
        this.infoWriter = infoWriter;
    }

    public String getInfoImgPath() {
        return infoImgPath;
    }

    public void setInfoImgPath(String infoImgPath) {
        this.infoImgPath = infoImgPath;
    }

    public Long getInfoViewTimes() {
        return infoViewTimes;
    }

    public void setInfoViewTimes(Long infoViewTimes) {
        this.infoViewTimes = infoViewTimes;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }
}
