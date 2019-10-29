package com.master.qianyi.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.manager.service.OrderManService;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.order.form.OrderForm;
import com.master.qianyi.pojo.TbOrder;
import com.master.qianyi.pojo.TbOrderExample;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.pojo.TbUserExample;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderManServiceImpl implements OrderManService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private UserService userService;

    @Override
    public EasyUIDataGridResult getOrderList(int pageNum, int pageSize, OrderForm orderForm) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1");

        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria userCriteria = userExample.createCriteria();
        if(StringUtil.isNotEmpty(orderForm.getOrderId())){
            criteria.andOrderIdLike("%"+ orderForm.getOrderId() +"%");
        }
        if(StringUtil.isNotEmpty(orderForm.getUserId())){
            criteria.andOrderIdLike("%"+ orderForm.getUserId() +"%");
        }

        if(StringUtil.isNotEmpty(orderForm.getGoodBelongTo())){
            criteria.andGoodBelongToLike("%"+ orderForm.getUserId() +"%");
        }

        if(StringUtil.isNotEmpty(orderForm.getGoodId())){
            criteria.andGoodIdLike("%"+ orderForm.getGoodId() +"%");
        }
        if(StringUtil.isNotEmpty(orderForm.getGoodName())){
            criteria.andGoodNameLike("%"+ orderForm.getGoodName() +"%");
        }
        if(StringUtil.isNotEmpty(orderForm.getOrderStatus())){
            criteria.andOrderStatusEqualTo(orderForm.getOrderStatus());
        }
        example.setOrderByClause("order_status asc,order_create_time desc");
        PageHelper.startPage(pageNum,pageSize);
        List<TbOrder> orderList = tbOrderMapper.selectByExample(example);
        List<OrderForm> formList = new ArrayList<>();
        if(orderList.size()>0){
            //取userIdList
            List<String> userIds = new ArrayList<>();
            for (TbOrder order:orderList) {
                userIds.add(order.getUserId());
            }
            List<TbUser> usersByUserIds = userService.getUsersByUserIds(userIds);
            Map<String, TbUser> idUserMap = userService.getIdUserMap(usersByUserIds);
            //取masterIdList
            List<String> masterIds = new ArrayList<>();
            for (TbOrder order:orderList) {
                masterIds.add(order.getGoodBelongTo());
            }
            List<TbUser> mastersByUserIds = userService.getUsersByUserIds(masterIds);
            Map<String, TbUser> idMasterMap = userService.getIdUserMap(mastersByUserIds);
            OrderForm form = null;
            for (TbOrder order:orderList) {
                form = new OrderForm();
                BeanUtils.copyProperties(order,form);
                form.setUserName(idUserMap.get(order.getUserId()).getNickName());
                form.setMasterName(idMasterMap.get(order.getGoodBelongTo()).getNickName());
                formList.add(form);
            }

        }
        PageInfo<TbOrder> pageInfo = new PageInfo<>(orderList);
        result.setTotal(pageInfo.getTotal());
        result.setRows(formList);
        return result;
    }
}
