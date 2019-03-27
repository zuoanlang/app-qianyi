package com.master.qianyi.information.form;

import com.master.qianyi.pojo.TbComment;
import com.master.qianyi.pojo.TbInformation;

import java.util.List;

public class InfomationForm extends TbInformation {

    private List<TbComment> tbCommentList;

    public List<TbComment> getTbCommentList() {
        return tbCommentList;
    }

    public void setTbCommentList(List<TbComment> tbCommentList) {
        this.tbCommentList = tbCommentList;
    }
}
