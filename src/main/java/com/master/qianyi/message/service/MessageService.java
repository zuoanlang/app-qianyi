package com.master.qianyi.message.service;

import com.master.qianyi.mapper.TbMessageMapper;
import com.master.qianyi.message.form.CourseNotice;
import com.master.qianyi.pojo.TbMessage;
import com.master.qianyi.pojo.TbMessageExample;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.IDUtils;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private TbMessageMapper tbMessageMapper;

    @Autowired
    private UserService userService;

    /**
     * 用户反馈
     * @param userId
     * @param messageContent
     * @return
     */
    public ResultBean sendMessage(String userId,String token, String messageContent){
        // 1.验证当前用户
        ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        // 2.验证
        ResultBean bean = new ResultBean();
        TbMessage message = new TbMessage();
        message.setMessageId(IDUtils.genItemId());
        message.setMessageSender(userId);
        message.setMessageType("1");
        message.setMessageReceiver("admin");
        message.setMessageContent(messageContent);
        message.setMessageDateTime(new Date());

        int insertSelective = tbMessageMapper.insertSelective(message);
        if(insertSelective<=0){
            bean.setMsg("发送失败");
            bean.setCode(Constants.code_1);
            return bean;
        }
        bean.setMsg("发送成功");
        bean.setCode(Constants.code_0);
        return bean;
    }

    public ResultBean courseNotice(String userId) {
        ResultBean bean = new ResultBean();
        TbMessageExample example = new TbMessageExample();
        TbMessageExample.Criteria criteria = example.createCriteria();
        criteria.andMessageReceiverEqualTo(userId)
                .andMessageTypeEqualTo("2")
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1");
        //时间降序
        example.setOrderByClause("message_date_time desc");
        List<TbMessage> tbMessages = tbMessageMapper.selectByExample(example);
        List<CourseNotice> noticeList = new ArrayList<>();
        if (tbMessages.size()>0){
            CourseNotice notice = null;
            for (TbMessage message:tbMessages){
                notice = new CourseNotice();
                notice.setCourseId(message.getCourseId());
                notice.setMessageId(message.getMessageId());
                notice.setMessageTitle(message.getRemark1());
                notice.setMessageContent(message.getMessageContent());
                notice.setIsRead(message.getIsRead());
                notice.setMessageDateTime(String.valueOf(message.getMessageDateTime().getTime()));
                noticeList.add(notice);
            }
        }
        bean.setResult(noticeList);
        bean.setMsg(Constants.msg_success);
        bean.setCode(Constants.code_0);
        return bean;
    }

    public ResultBean read(String messageId) {
        List<String> ids = new ArrayList<>();
        if(!messageId.contains(",")){
            ids.add(messageId);
        } else {
            String[] split = messageId.split(",");
            for (String id:split){
                ids.add(id);
            }
        }
        ResultBean bean = new ResultBean();
        TbMessageExample example = new TbMessageExample();
        TbMessageExample.Criteria criteria = example.createCriteria();
        criteria.andMessageIdIn(ids);
        TbMessage message = new TbMessage();
        message.setMessageId(messageId);
        message.setIsRead("1");
        int update = tbMessageMapper.updateByExampleSelective(message,example);
        if(update<0){
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            return bean;
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;
    }
}
