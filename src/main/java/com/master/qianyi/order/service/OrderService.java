package com.master.qianyi.order.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.course.service.CourseService;
import com.master.qianyi.mapper.*;
import com.master.qianyi.order.form.MyOrder;
import com.master.qianyi.order.form.OrderDetails;
import com.master.qianyi.pojo.*;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.IDUtils;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    @Autowired
    private TbMessageMapper tbMessageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TbServiceMapper serviceMapper;

    @Autowired
    private TbCourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private TbEnrollMapper enrollMapper;

    /**
     * @param userId
     * @param orderStatus
     * @return
     */
    public ResultBean getOrderByUserId(String userId,String token, String orderStatus) {
        ResultBean bean = new ResultBean();
        ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        // 1. 查询订单表
        TbOrderExample tbOrderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = tbOrderExample.createCriteria();

        criteria.andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0")
                .andUserIdEqualTo(userId);
        if(StringUtil.isNotEmpty(orderStatus)){
            int index = orderStatus.indexOf(",");
            if(index!=-1){
                String[] split = orderStatus.split(",");
                List<String> orderStatusList = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    orderStatusList.add(split[i]);
                }
                criteria.andOrderStatusIn(orderStatusList);
            } else {
                if(!orderStatus.equals("0")){
                    criteria.andOrderStatusEqualTo(orderStatus);
                }
            }
        }

        tbOrderExample.setOrderByClause("order_status desc, order_create_time desc");
        List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);

        // 2. 根据goodsIds get goodsList
        // 2.1 筛选出courseIds
        List<String> courseIdList = new ArrayList<>();
        // 2.2 筛选出serviceIds
        List<String> serviceIdList = new ArrayList<>();
        if (tbOrderList != null && tbOrderList.size() > 0) {
            for (TbOrder order : tbOrderList) {
                if(order.getOrderType().equals("1")){
                    courseIdList.add(order.getGoodId());
                }
                if(order.getOrderType().equals("2")){
                    serviceIdList.add(order.getGoodId());
                }
            }
        }
        // 2.3 get target
        List<String> userIdList = new ArrayList<>();
        List<TbCourse> courseListByIds = new ArrayList<>();
        Map<String, TbCourse> idCourseMap = new HashMap<>();
        Map<String, List<TbCatalog>> courseIdCatalogListMap = new HashMap<>();
        Map<String, Long> stringTimesMap = new HashMap<>();

        if (courseIdList.size()>0) {
            courseListByIds = courseService.getCourseListByIds(courseIdList);
            idCourseMap = getIdCourseMap(courseListByIds);
            for(TbCourse course : courseListByIds){
                userIdList.add(course.getCourseBelongto());
            }
            //根据courseIds查询有效子目录条数
            List<TbCatalog> catalogList = courseService.getCatalogListByIds(courseIdList);
            courseIdCatalogListMap = courseService.courseIdCatalogListMap(catalogList);
            stringTimesMap = courseService.courseIdLearningTimesMap(catalogList);

        }

        List<TbService> serviceListByIds = new ArrayList<>();
        Map<String, TbService> idServiceMap = new HashMap<>();
        List<TbUser> userList = new ArrayList<>();

        Map<String, TbUser> idUserMap = new HashMap<>();
        if(serviceIdList.size()>0){
            serviceListByIds = courseService.getServiceListByIds(serviceIdList);
            idServiceMap = getIdServiceMap(serviceListByIds);
            for(TbService service:serviceListByIds){
                userIdList.add(service.getUserId());
            }
        }
        // 2.4 get users by userId
        if(userIdList.size()>0){
            userList = userService.getUsersByUserIds(userIdList);
            idUserMap = userService.getIdUserMap(userList);
        }
        MyOrder myOrder;
        List<MyOrder> formList = new ArrayList<>();
        if(tbOrderList.size()>0){
            for (TbOrder order : tbOrderList) {
                myOrder = new MyOrder();
                myOrder.setOrderId(order.getOrderId());
                myOrder.setOrderStatus(order.getOrderStatus());
                myOrder.setGoodsId(order.getGoodId());
                myOrder.setGoodsName(order.getGoodName());
                if(idCourseMap.containsKey(order.getGoodId())){
                    TbCourse course = idCourseMap.get(order.getGoodId());
                    myOrder.setIsCourse("1");
                    myOrder.setMasterName(idUserMap.get(course.getCourseBelongto()).getUserName());
                    myOrder.setGoodsImg(course.getCourseImg());
                    myOrder.setGoodsAmount(course.getCoursePrice());
                    myOrder.setCourseLearningFrequency(stringTimesMap.get(order.getGoodId()));
                    myOrder.setMasterIntroduction(idUserMap.get(course.getCourseBelongto()).getMasterIntroduction());
                    int video = 0;
                    if(courseIdCatalogListMap != null && courseIdCatalogListMap.get(course.getCourseId()) != null){
                        video = courseIdCatalogListMap.get(course.getCourseId()).size();
                    }
                    myOrder.setCourseVideoNum(video);
                } else {
                    TbService service = idServiceMap.get(order.getGoodId());
                    myOrder.setIsCourse("0");
                    myOrder.setMasterName(idUserMap.get(service.getUserId()).getUserName());
                    myOrder.setMasterIntroduction(idUserMap.get(service.getUserId()).getMasterIntroduction());
                    myOrder.setGoodsAmount(service.getServicePrice());
                    //服务的头像为名师头像
                    myOrder.setGoodsImg(idUserMap.get(service.getUserId()).getHeadImg());
                }
                formList.add(myOrder);
            }
        }

        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(formList);
        return bean;
    }



    private Map<String,TbCourse> getIdCourseMap(List<TbCourse> courses){
        Map<String,TbCourse> idCourseMap = new HashMap<>();
        for(TbCourse course:courses){
            if(!idCourseMap.containsKey(course.getCourseId())){
                idCourseMap.put(course.getCourseId(),course);
            }
        }
        return idCourseMap;
    }

    private Map<String,TbService> getIdServiceMap(List<TbService> services){
        Map<String,TbService> idServiceMap = new HashMap<>();
        for(TbService service:services){
            if(!idServiceMap.containsKey(service.getServiceId())){
                idServiceMap.put(service.getServiceId(),service);
            }
        }
        return idServiceMap;
    }

    /**
     * 根据订单id查询订单信息
     *
     * @param orderId
     * @return
     */
    public ResultBean getOrderDetailByOrderId(String orderId) {
        ResultBean bean = new ResultBean();
        // 1. 查询订单表
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        // 2. 查询用户表 -- 名师信息
        TbUser master = userMapper.selectByPrimaryKey(tbOrder.getGoodBelongTo());
        TbCourse course = new TbCourse();
        List<TbCatalog> catalogList = new ArrayList<>();
        TbService service = new TbService();
        if(tbOrder.getOrderType().equals("1")){
            // 1.课程购买
            course = tbCourseMapper.selectByPrimaryKey(tbOrder.getGoodId());
            // 2.查询目录表 -- 课程目录
            List<String> courseIdList = new ArrayList<>();
            courseIdList.add(tbOrder.getGoodId());
            catalogList = courseService.getCatalogListByIds(courseIdList);
        } else if(tbOrder.getOrderType().equals("2")){
            // 2.测算服务
            service = serviceMapper.selectByPrimaryKey(tbOrder.getGoodId());
        }
        OrderDetails details = new OrderDetails();
        details.setOrderStatus(tbOrder.getOrderStatus());
        details.setGoodsName(tbOrder.getGoodName());
        details.setMasterName(master.getUserName());
        details.setGoodsAmount(tbOrder.getOrderAmount());
        details.setModeOfPayment(tbOrder.getModeOfPayment());
        details.setOrderId(tbOrder.getOrderId());
        details.setOrderCreateTime(String.valueOf(tbOrder.getOrderCreateTime().getTime()));
        if(tbOrder.getOrderPayTime()!=null){
            details.setOrderPayTime(String.valueOf(tbOrder.getOrderPayTime().getTime()));
        }
        Long viewTimes = new Long(0);
        if(tbOrder.getOrderType().equals("1")){
            details.setGoodImgPath(course.getCourseImg());
            for(TbCatalog catalog : catalogList){
                viewTimes+=catalog.getLearningTimes();
            }
            details.setCourseLearningFrequency(viewTimes);
            details.setCourseVideoNum(catalogList.size());
            details.setIsCourse("1");
        } else {
            details.setGoodImgPath(master.getHeadImg());
            details.setIsCourse("0");
        }

        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(details);
        return bean;
    }

    /**
     * 下单（插入订单）
     *
     * @param userId    订单创建者id
     * @param token     订单创建者id
     * @param goodsId   购买课程id
     * @param goodsType 商品类型1.课程购买2.测算服务
     * @return
     */
    public ResultBean insertOrder(String userId, String token, String goodsId, String goodsType,
                                  String underLine,String name,String phoneNumber) {
        ResultBean bean = new ResultBean();
        ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        //check goodsType
        if (!goodsType.equals("1") && !goodsType.equals("2")) {
            bean.setCode(Constants.code_1);
            bean.setMsg("商品分类只能为1或者2");
            return bean;
        }

        //check goodsId
        TbService tbService = new TbService();
        TbCourse tbCourse = new TbCourse();
        if (StringUtil.isNotEmpty((goodsId))) {
            if (goodsType.equals("2")) {
                //search service
                tbService = serviceMapper.selectByPrimaryKey(goodsId);
                if (tbService == null) {
                    bean.setCode(Constants.code_1);
                    bean.setMsg("当前测算服务已失效");
                    return bean;
                }
            } else {
                //search course
                tbCourse = courseMapper.selectByPrimaryKey(goodsId);
                if (tbCourse == null) {
                    bean.setCode(Constants.code_1);
                    bean.setMsg("当前课程已失效");
                    return bean;
                }
            }
        }
        //underLine == 1,线下课程，需要处理报名表
        if(underLine.equals("1")){
            //判断name与phoneNumber是否存在
            if(StringUtil.isEmpty(name) || StringUtil.isEmpty(phoneNumber)){
                bean.setCode(Constants.code_1);
                bean.setMsg("姓名不可为空");
                return bean;
            }
            if(phoneNumber.length()!=11){
                bean.setCode(Constants.code_1);
                bean.setMsg("手机号格式不正");
                return bean;
            }
        }

        //goodsType == 1 课程购买,不能重复购买
        boolean buyOrNot = checkCourseBuyOrNot(userId, goodsId);
        if (buyOrNot) {
            bean.setCode(Constants.code_1);
            bean.setMsg("该课程已下单,请前往订单查看");
            return bean;
        }

        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty((goodsId))) {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setOrderId(IDUtils.genItemId());
            tbOrder.setUserId(userId);
            tbOrder.setGoodId(goodsId);
            tbOrder.setOrderStatus("1");
            tbOrder.setOrderType(goodsType);
            if (goodsType.equals("2")) {
                tbOrder.setGoodName(tbService.getServiceName());
                tbOrder.setGoodBelongTo(tbService.getUserId());
                tbOrder.setOrderAmount(tbService.getServicePrice());
            } else {
                tbOrder.setGoodName(tbCourse.getCourseName());
                tbOrder.setGoodBelongTo(tbCourse.getCourseBelongto());
                tbOrder.setOrderAmount(tbCourse.getCoursePrice());
            }
            tbOrder.setOrderCreateTime(new Date(System.currentTimeMillis()));
            tbOrder.setEffectFlag("1");
            tbOrder.setDeleteFlag("0");
            int insertFlag = tbOrderMapper.insertSelective(tbOrder);
            if (insertFlag != 1) {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
                return bean;
            }

            //underLine == 1,线下课程，需要处理报名表
            if(underLine.equals("1")){
                //插入报名表
                TbEnroll tbEnroll = new TbEnroll();
                tbEnroll.setEnrollId(IDUtils.genItemId());
                tbEnroll.setCourseId(goodsId);
                tbEnroll.setOrderId(tbOrder.getOrderId());
                tbEnroll.setEnrollName(name);
                tbEnroll.setPhonenumber(phoneNumber);
                int insertSelective = enrollMapper.insertSelective(tbEnroll);
                if(insertSelective<0){
                    bean.setCode(Constants.code_1);
                    bean.setMsg("报名失败");
                    return bean;
                }
            }
            Map<String,String> orderIdMap = new HashMap<>();
            orderIdMap.put("orderId",tbOrder.getOrderId());
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
            bean.setResult(orderIdMap);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            bean.setResult("用户id或商品id为空！");
        }
        return bean;
    }

    /**
     * 检查该课程购买与否
     *
     * @param userId
     * @param courseId
     * @return
     */
    private boolean checkCourseBuyOrNot(String userId, String courseId) {
        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();
        //order status list
        List<String> statusList = new ArrayList<>();
        statusList.add("1");//已下单
        statusList.add("2");//已付款
        statusList.add("3");//已评价
        statusList.add("4");//待审核
        criteria.andUserIdEqualTo(userId).andOrderStatusIn(statusList).andDeleteFlagEqualTo("0")
                .andGoodIdEqualTo(courseId).andEffectFlagEqualTo("1");

        List<TbOrder> orderList = tbOrderMapper.selectByExample(example);
        if (orderList != null && orderList.size() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 修改订单（支付）
     *
     * @param orderId
     * @param modeOfPayment
     * @param orderAmount
     * @return
     */
    @Transactional
    public ResultBean updateOrderState(String userId,String token,String orderId, String modeOfPayment) {
        ResultBean bean = new ResultBean();
        // 1.支付方式验证
        if(!modeOfPayment.equals("1") && !modeOfPayment.equals("2")){
            bean.setCode(Constants.code_1);
            bean.setMsg("支付方式参数错误");
            return bean;
        }
        // 2.验证当前用户
        ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria()
                .andOrderIdEqualTo(orderId)
                .andOrderStatusEqualTo("1")
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        // 3.get order
        List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);
        if(tbOrderList != null && tbOrderList.size()>0){
            TbOrder tbOrder = tbOrderList.get(0);
            // 4.get user info
            TbUser user = userMapper.selectByPrimaryKey(userId);
            // 4.1 get user amount and judge
            if(user.getUserAccountBalance() < tbOrder.getOrderAmount()){
                bean.setCode(Constants.code_1);
                bean.setMsg("账户余额不足，请充值");
                return bean;
            }
            // 4.2 账户扣款
            user.setUserAccountBalance(user.getUserAccountBalance()-tbOrder.getOrderAmount());
            int updateAmount = userMapper.updateByPrimaryKeySelective(user);
            if(updateAmount<=0){
                bean.setCode(Constants.code_1);
                bean.setMsg("账户扣款失败，请稍后重试");
                return bean;
            }
            // 5.订单状态由未付款改为已付款
            tbOrder.setOrderStatus("2");
            tbOrder.setModeOfPayment(modeOfPayment);
            tbOrder.setOrderPayTime(new Date());
            int updateFlag = tbOrderMapper.updateByPrimaryKeySelective(tbOrder);
            if (updateFlag < 1) {
                bean.setCode(Constants.code_1);
                bean.setMsg("订单状态变更失败");
            }
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg("该订单已付款");
        }

        return bean;
    }

    /**
     * 退款
     *
     * @param userId            用户id
     * @param orderId           订单id
     * @param orderRefundReason 退款原因
     * @return
     */
    public ResultBean refund(String userId, String token,String orderId, String orderRefundReason) {
        ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria()
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0")
                .andOrderIdEqualTo(orderId);
        List<TbOrder> orders = tbOrderMapper.selectByExample(tbOrderExample);
        ResultBean bean = new ResultBean();
        if (orders != null &&orders.size()>0) {
            TbOrder order = orders.get(0);
            //判断订单状态
            if(!order.getOrderStatus().equals("2")){
                bean.setCode(Constants.code_1);
                if(order.getOrderStatus().equals("4")){
                    bean.setMsg("该订单已发起退款");
                    return bean;
                }
                bean.setMsg("订单状态异常");
                return bean;
            }
            // 状态设置为4：退款中
            order.setOrderStatus("4");
            // 申请退款时间
            order.setOrderRefundStime(new Date());
            // 退款理由
            order.setOrderRefundReason(orderRefundReason);
            // 订单审核人为admin
            order.setOrderAuditor("admin");
            int updateFlag = tbOrderMapper.updateByExampleSelective(order, tbOrderExample);
            if (updateFlag > 0) {
//                // 消息内容
//                String messageContent = "用户" + userId + "发起退款。退款原因：" + orderRefundReason;
//                // 发消息给订单审核人admin
//                int insert = insertMessage(userId, order.getGoodId(), messageContent);
//                if (insert > 0) {
//                    bean.setCode(Constants.code_0);
//                    bean.setMsg(Constants.msg_success);
//                } else {
//                    bean.setCode(Constants.code_1);
//                    bean.setMsg(Constants.msg_failed);
//                }
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        return bean;
    }

    /**
     * 发送给admin的系统消息
     *
     * @param userId
     * @param courseId
     * @param messageContent
     * @return
     */
    private int insertMessage(String userId, String courseId, String messageContent) {
        // 发消息给订单审核人admin
        TbMessage message = new TbMessage();
        message.setMessageId(IDUtils.genItemId());
        message.setCourseId(courseId);
        // 1:系统消息
        message.setMessageType("1");
        message.setMessageSender(userId);
        // 消息接收者为admin
        message.setMessageReceiver("admin");
        message.setMessageContent(messageContent);
        message.setMessageDateTime(new Date(System.currentTimeMillis()));
        message.setIsRead("0");
        message.setEffectFlag("1");
        message.setDeleteFlag("0");
        int insert = tbMessageMapper.insert(message);
        if (insert > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 根据订单的idList,查询订单
     *
     * @param orderList
     * @return orders
     */
    public List<TbOrder> getOrderListByIds(List<String> orderList) {
        TbOrderExample orderExample = new TbOrderExample();
        orderExample.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0")
                .andOrderIdIn(orderList);
        List<TbOrder> orders = tbOrderMapper.selectByExample(orderExample);
        if (orders == null) {
            return new ArrayList<>();
        }

        return orders;
    }

    /**
     * ios金币购买
     * @param userId
     * @param token
     * @param orderId
     * @return
     */
    public ResultBean payOrderByCoins(String userId, String token, String orderId) {
        // 1.验证当前用户
        ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria()
                .andOrderIdEqualTo(orderId)
                .andUserIdEqualTo(userId)
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        List<TbOrder> orders = tbOrderMapper.selectByExample(tbOrderExample);
        ResultBean bean = new ResultBean();
        if(orders.size() == 1){
            TbOrder tbOrder = orders.get(0);
            if(!orders.get(0).getOrderStatus().equals("1")){
                bean.setCode(Constants.code_1);
                bean.setMsg("该订单已支付!");
                return bean;
            }
            // 4.get user info
            TbUser user = userMapper.selectByPrimaryKey(userId);
            // 4.1 get user amount and judge
            if(user.getUserAccountBalance() < tbOrder.getOrderAmount()){
                bean.setCode(Constants.code_1);
                bean.setMsg("账户余额不足，请充值");
                return bean;
            }
            // 4.2 账户扣款
            user.setUserAccountBalance(user.getUserAccountBalance()-tbOrder.getOrderAmount());
            int updateAmount = userMapper.updateByPrimaryKeySelective(user);
            if(updateAmount<=0){
                bean.setCode(Constants.code_1);
                bean.setMsg("账户扣款失败，请稍后重试");
                return bean;
            }
            // 5.订单状态由未付款改为已付款
            tbOrder.setOrderStatus("2");
            tbOrder.setOrderPayTime(new Date());
            int updateFlag = tbOrderMapper.updateByPrimaryKeySelective(tbOrder);
            if (updateFlag < 1) {
                bean.setCode(Constants.code_1);
                bean.setMsg("订单状态变更失败");
            }
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg("该订单不存在!");
            return bean;
        }
        return bean;
    }

    public EasyUIDataGridResult getRefundList(int page,int rows) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbOrderExample orderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andOrderStatusIn(Constants.orderRrfund_Status_LIST)
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1");
        orderExample.setOrderByClause("order_status asc");
        PageHelper.startPage(page,rows);
        List<TbOrder> orderList = tbOrderMapper.selectByExample(orderExample);

        result.setRows(orderList);
        PageInfo<TbOrder> pageInfo = new PageInfo<>(orderList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
