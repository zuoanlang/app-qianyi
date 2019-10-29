package com.master.qianyi.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.master.qianyi.manager.service.MessageManService;
import com.master.qianyi.mapper.TbMessageMapper;
import com.master.qianyi.pojo.TbMessage;
import com.master.qianyi.pojo.TbMessageExample;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageManServiceImpl implements MessageManService {

    @Autowired
    private TbMessageMapper tbMessageMapper;

    @Autowired
    private UserService userService;

    @Override
    public EasyUIDataGridResult getFeedbackList(int pageNum, int pageSize) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbMessageExample example = new TbMessageExample();
        TbMessageExample.Criteria criteria = example.createCriteria();
        criteria.andMessageTypeEqualTo("1").andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1");
        example.setOrderByClause("message_date_time desc,is_read asc");
        PageHelper.startPage(pageNum, pageSize);
        List<TbMessage> tbMessages = tbMessageMapper.selectByExample(example);
        PageInfo<TbMessage> pageInfo = new PageInfo<>(tbMessages);
        result.setTotal(pageInfo.getTotal());
        //处理用户相关字段
        //1.取用户idList
        List<String> userIdList = new ArrayList<>();
        if (tbMessages.size() > 0) {
            for (TbMessage message : tbMessages) {
                userIdList.add(message.getMessageSender());
            }
            //根据用户查询用户map
            List<TbUser> usersByUserIds = userService.getUsersByUserIds(userIdList);
            Map<String, TbUser> idUserMap = userService.getIdUserMap(usersByUserIds);
            for (TbMessage message : tbMessages) {
                TbUser tbUser = idUserMap.get(message.getMessageSender());
                if (tbUser != null) {
                    //userName
                    message.setRemark1(idUserMap.get(message.getMessageSender()).getUserName());
                    //user-handPhone
                    message.setRemark2(idUserMap.get(message.getMessageSender()).getHandphone());
                }

            }

        }
        result.setRows(tbMessages);
        return result;
    }

    @Override
    public Map<String, String> delete(String ids) {
        //1.分隔
        String[] split = ids.split(",");
        List<String> idList = new ArrayList<>();
        TbMessageExample example = new TbMessageExample();
        TbMessageExample.Criteria criteria = example.createCriteria();
        for (int i = 0; i < split.length; i++) {
            idList.add(split[i]);
        }
        criteria.andMessageIdIn(idList);

        int i = tbMessageMapper.deleteByExample(example);
        Map<String, String> result = new HashMap<>();
        if (i > 0) {
            result.put("status", "200");
        } else {
            result.put("status", "1");
        }

        return result;
    }
}
