package com.master.qianyi.fileUtils;

public interface IAliyunUpload {

    /**
     * @param
     * String filePathName 本地图片路径（D:/xxxx/xxxx....../xx/xx.jgp|xx.png|..）
     * String savePathName 将要保存到OSS上的路径地址
     * */
    String uploadFile(String filePathName,String savePathName);
}
