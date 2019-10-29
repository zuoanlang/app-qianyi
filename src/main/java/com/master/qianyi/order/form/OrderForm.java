package com.master.qianyi.order.form;

import com.master.qianyi.pojo.TbOrder;

/**
 * 后台课程列表
 */
public class OrderForm extends TbOrder {

    private String userName;

    private String masterName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

}
