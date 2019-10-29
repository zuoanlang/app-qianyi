package com.master.qianyi.manager.service;

import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;

public interface InfoManService {

    ResultBean addNews(Long infoType,String infoTitle,String infoImgPath,String infoWriter,String infoContent);

    ResultBean editNews(String infoId,Long infoType,String infoTitle,String infoImgPath,
                        String infoWriter,String effectFlag,String infoContent);

    ResultBean deleteNews(String InfoIds);

    EasyUIDataGridResult getNewsInfoList(int pageNum, int pageSize,String infoId,String infoTitle,String infoWriter);

    ResultBean publish(String infoIds, int type);
}
