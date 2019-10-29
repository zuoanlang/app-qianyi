package com.master.qianyi.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.comment.form.ServiceComment;
import com.master.qianyi.comment.service.CommentService;
import com.master.qianyi.manager.service.BaseService;
import com.master.qianyi.manager.service.impl.CourseServiceImpl;
import com.master.qianyi.mapper.*;
import com.master.qianyi.pojo.*;
import com.master.qianyi.user.form.*;
import com.master.qianyi.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/12/6.
 */
@Service
public class UserService {

    @Autowired
    private TbUserMapper tbuserMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    @Autowired
    private TbReceiptionMapper tbReceiptionMapper;

    @Autowired
    private TbCommentMapper commentMapper;

    @Autowired
    private TbServiceMapper serviceMapper;

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TbCategoryMapper tbCategoryMapper;

    @Autowired
    private TbLearningMapper tbLearningMapper;

    @Autowired
    private TbCatalogMapper tbCatalogMapper;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private TbMessageMapper messageMapper;

    @Autowired
    private BaseService baseService;

    @Autowired
    private TbServiceMapper tbServiceMapper;

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public TbUser getUserByUsername(String username) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(userExample);
        if (tbUsers.size()>0){
            return tbUsers.get(0);
        }
        return null;
    }

    /**
     * 查询所有普通用户
     *
     * @return
     */
    public EasyUIDataGridResult getOrdinaryUserList(int pageNum, int pageSize,String type) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        PageHelper.startPage(pageNum, pageSize);
        TbUserExample userExample = new TbUserExample();
        userExample.setOrderByClause("is_administrator desc,is_master desc,register_time asc");
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1");
        if(type.equals("ordinary")){
            criteria.andIsAdministratorEqualTo("0");
            criteria.andIsMasterEqualTo("0");
        }
        if(type.equals("admin")){
            criteria.andIsAdministratorEqualTo("1");
        }
        if(type.equals("master")){
            criteria.andIsMasterEqualTo("1");
        }
        List<TbUser> userList = tbuserMapper.selectByExample(userExample);
        result.setRows(userList);
        PageInfo<TbUser> pageInfo = new PageInfo<>(userList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * 根据条件查询名师列表
     *
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    public ResultBean getFamousTeachers(int pageNum, int pageSize, TbUser user) {
        ResultBean bean = new ResultBean();
        TbUserExample example = new TbUserExample();
        // 有效标志为1（有效），删除标志为0（未删除）,是名师
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0").andIsMasterEqualTo("1");
        // 检索条件4个
        // 一、1.全部名师,2.在线名师
        // 1.全部名师只有默认排序条件
        // 默认排序条件:官方(降序),订单数(降序)
        example.setOrderByClause("is_official desc");
        // 2.在线名师
        if (StringUtil.isNotEmpty(user.getIsOnline()) && "1".equals(user.getIsOnline())) {
            criteria.andIsOnlineEqualTo("1");
        }
        // 二、1.具体分类,3.官方名师,3.价格
        // 按名师专业(多个专业，搜索时只按一个专业搜索，只要包含在该名师专业中即可)
        // 1.具体分类
        if (StringUtil.isNotEmpty(user.getMajor())) {
            criteria.andMajorLike("%" + user.getMajor() + "%");
        }
        // 2.官方名师
        if (StringUtil.isNotEmpty(user.getIsOfficial()) && "1".equals(user.getIsOfficial())) {
            criteria.andIsOfficialEqualTo(user.getIsOfficial());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(example);

        List<Teacher> teacherList = new ArrayList<>();
        //名师列表字段筛选
        Teacher teacher = null;
        if (tbUsers != null && tbUsers.size() > 0) {
            //1.取得名师userId列表
            List<String> userIdList = new ArrayList<>();
            for (TbUser tbUser : tbUsers) {
                userIdList.add(tbUser.getUserId());
            }
            //2.取得相关名师评论类型:1（订单评论）的所有评论
            //2.1 根据用户ids取所有订单（订单类型为：2测算服务）
            List<TbOrder> orderList = getOrderListByMasterUserId(userIdList, "2");
            Map<String, List<TbComment>> userCommentMap = null;
            if (orderList.size() > 0) {
                List<String> orderIds = new ArrayList<>();
                for (TbOrder order : orderList) {
                    orderIds.add(order.getOrderId());
                }
                //2.2 根据所有订单取所有订单的评论
                List<TbComment> commentList = getCommentList(orderIds, "1");
                //2.3 构造Map<String,List<Comment>>
                userCommentMap = getUserCommentMap(orderList, commentList);
            } else {
                userCommentMap = new HashMap<>();
            }

            //3.取得相关名师的服务列表
            List<TbService> serviceList = getServiceList(userIdList);
            List<TbComment> commentListUnderUser;
            for (TbUser tbUser : tbUsers) {
                //1.取得评论数（根据userId）评论类型:3:服务评论
                int commentNum = 0;
                int totalScore = 0;
                if (userCommentMap.size() > 0) {
                    if (userCommentMap.containsKey(tbUser.getUserId())) {
                        commentListUnderUser = userCommentMap.get(tbUser.getUserId());
                        for (TbComment comment : commentListUnderUser) {
                            commentNum++;
                            totalScore += comment.getCommentScore();
                        }
                    }
                }

                //2.取得擅长方向（专业）列表
                List<String> masterMajors = getMasterMajors(tbUser.getMajor());
                //3.取评论表的平均评分（根据userId）评论类型:3:服务评论
                String commentScore = CalculateUtils.division(totalScore, commentNum);
                //4.取的名师算命服务的最低价格
                int floorPrice = 1000000000;
                for (TbService service : serviceList) {
                    if (service.getUserId().equals(tbUser.getUserId())) {
                        if (service.getServicePrice() < floorPrice) {
                            floorPrice = service.getServicePrice();
                        }
                    }
                }
                teacher = new Teacher(tbUser.getUserId(), tbUser.getUserName(), tbUser.getHeadImg(), tbUser.getMasterRank(),
                        commentScore, tbUser.getIsOfficial(), tbUser.getIsOnline(), tbUser.getMasterIntroduction(),
                        commentNum, floorPrice, masterMajors);
                teacherList.add(teacher);

            }

        }

        // 3.价格（-1:降序，0：不按价格检索    1:升序）
        if (user.getFloorPrice() != null && user.getFloorPrice() == -1) {
            // 价格检索 -1:降序
            getListByFloorPriceOrder(teacherList, -1);
        } else if (user.getFloorPrice() != null && user.getFloorPrice() == 1) {
            // 价格检索 1:升序
            getListByFloorPriceOrder(teacherList, 1);
        } else {
            // 不按价格检索

        }

        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(teacherList);
        return bean;
    }

    /**
     * 根据名师id获取     用户----评论表
     *
     * @return
     */
    public Map<String, List<TbComment>> getUserCommentMap(List<TbOrder> orderList, List<TbComment> commentList) {
        Map<String, List<TbComment>> userCommentMap = new HashMap<>();
        //1.goodBelongTo(userId)--orderId构成map
        Map<String, List<String>> userIdOrderIds = new HashMap<>();
        for (TbOrder order : orderList) {
            if (!userIdOrderIds.containsKey(order.getGoodBelongTo())) {
                List<String> orderIdList = new ArrayList<>();
                orderIdList.add(order.getOrderId());
                userIdOrderIds.put(order.getGoodBelongTo(), orderIdList);
            } else {
                userIdOrderIds.get(order.getGoodBelongTo()).add(order.getOrderId());
            }
        }

        //2.遍历map
        List<TbComment> comments = null;
        for (String userId : userIdOrderIds.keySet()) {
            List<String> orderIds = userIdOrderIds.get(userId);
            for (String orderId : orderIds) {
                for (TbComment comment : commentList) {
                    if (orderId.equals(comment.getCommentarySubjectId())) {
                        if (!userCommentMap.containsKey(userId)) {
                            comments = new ArrayList<>();
                            comments.add(comment);
                            userCommentMap.put(userId, comments);
                        } else {
                            userCommentMap.get(userId).add(comment);
                        }

                    }
                }
            }
        }
        return userCommentMap;
    }

    /**
     * 对结果进行价格排序
     *
     * @param teacherList
     * @param sortMode
     * @return
     */
    public List<Teacher> getListByFloorPriceOrder(List<Teacher> teacherList, int sortMode) {
        Collections.sort(teacherList, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                if (sortMode > 0) {
                    if (o1.getFloorPrice() > o2.getFloorPrice()) {
                        return 1;
                    }
                } else {
                    if (o1.getFloorPrice() > o2.getFloorPrice()) {
                        return -1;
                    }
                }

                return 0;
            }
        });
        return null;
    }

    /**
     * 获取服务列表取得价格最低的服务金额
     * userId  用户id
     *
     * @return
     */
    private List<TbService> getServiceList(List<String> userIdList) {
        TbServiceExample example = new TbServiceExample();
        example.createCriteria().andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1")
                .andUserIdIn(userIdList);
        List<TbService> tbServices = serviceMapper.selectByExample(example);
        if (tbServices == null) {
            return new ArrayList<>();
        }

        return tbServices;
    }

    /**
     * 取相关名师直接购买服务的评论列表
     * idsList  被评论主体的id:订单id
     * commentType  评论的类型
     *
     * @return
     */
    private List<TbComment> getCommentList(List<String> idsList, String commentType) {
        TbCommentExample example = new TbCommentExample();
        example.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0")
                .andCommentarySubjectIdIn(idsList).andCommentTypeEqualTo(commentType);
        List<TbComment> tbComments = commentMapper.selectByExample(example);
        if (tbComments != null) {
            return tbComments;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 将专业id转化成文字
     *
     * @param major
     * @return
     */
    private List<String> getMasterMajors(String major) {
        List<String> majorList = new ArrayList<>();
        //1.切割字符串
        if (StringUtil.isNotEmpty(major)) {
            String[] majorArr = major.split(",");
            for (int i = 0; i < majorArr.length; i++) {
                majorList.add(majorArr[i]);
            }

        }
        return majorList;
    }

    /**
     * 根据手机号查询用户
     *
     * @return
     */
    public TbUser getUserByPhoneNumber(String phoneNumber) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andHandphoneEqualTo(phoneNumber);
        List<TbUser> usersList = tbuserMapper.selectByExample(userExample);
        if (usersList != null && usersList.size() == 1) {
            return usersList.get(0);
        }
        return null;
    }

    /**
     * 用户表插入一条数据：根据已有值的字段
     *
     * @param user
     * @return
     */
    public boolean insertOneUserRecord(TbUser user) {
        int count = tbuserMapper.insertSelective(user);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据主键userId更新用户信息，（不为空的字段）
     *
     * @return
     */
    public boolean updateUserBySelective(TbUser user) {
        int count = tbuserMapper.updateByPrimaryKeySelective(user);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据openid查询用户
     *
     * @param openid
     * @param type
     * @return
     */
    public TbUser getUserByOpenId(String openid, String type) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        if (type.equals("1")) {
            criteria.andWxOpenidEqualTo(openid);
        } else {
            criteria.andQqOppenidEqualTo(openid);
        }
        List<TbUser> tbUsers = tbuserMapper.selectByExample(userExample);
        if (tbUsers.size() == 1) {
            return tbUsers.get(0);
        }
        return null;
    }

    /**
     * 根据id_card_no查询用户
     *
     * @param idCardNo
     * @param userName
     * @return
     */
    public TbUser getUserByIdCardNo(String idCardNo, String userName) {
        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUserIdNotEqualTo(idCardNo).andUserNameEqualTo(userName)
                .andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0");
        List<TbUser> tbUsers = tbuserMapper.selectByExample(userExample);
        if (tbUsers != null && tbUsers.size() == 1) {
            return tbUsers.get(0);
        }
        return new TbUser();
    }

    /**
     * 用户充值和提现
     *
     * @param userId      用户id
     * @param tradeType   交易类型：1充值，2提现
     * @param tradeAmount 交易金额
     * @param tradeSource 交易来源（去向）：1.支付宝，2微信
     * @return
     */
    @Transactional
    public ResultBean rechargeAndWithdraw(String userId, String tradeType, int tradeAmount) {
        // 1.查询,userId,主键查询和token
//        ResultBean userOperableBean = getUserOperable(userId, token);
//        if (userOperableBean.getCode() != 0) {
//            return userOperableBean;
//        }

        ResultBean bean = new ResultBean();
        // 若交易金额小于等于0，直接返回错误信息
        if (tradeAmount <= 0) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            bean.setResult("Trade Amount Less than or equal to 0!");
            return bean;
        }
        //提现
        if (tradeType.equals("2")) {
            TbUser user = tbuserMapper.selectByPrimaryKey(userId);
            if (user.getUserAccountBalance() == 0) {
                bean.setCode(Constants.code_1);
                bean.setMsg("账户余额为0时不可提现");
                return bean;
            }
            if (user.getUserAccountBalance() < tradeAmount) {
                bean.setCode(Constants.code_1);
                bean.setMsg("提现金额不得高于余额");
                return bean;
            }

        }
        TbReceiption tbReceiption = new TbReceiption();
        tbReceiption.setReceiptId(IDUtils.genItemId());
        tbReceiption.setUserId(userId);
        tbReceiption.setTradeType(tradeType);
        tbReceiption.setTradeAmount(tradeAmount);
        tbReceiption.setTradeDateTime(new Date(System.currentTimeMillis()));
        tbReceiption.setEffectFlag("1");
        tbReceiption.setDeleteFlag("0");
        int insertFlag = tbReceiptionMapper.insertSelective(tbReceiption);
        if (insertFlag > 0) {
            // 更新用户表
            int updateUser = updateUserAccountBalance(userId, tradeType, tradeAmount);
            if (updateUser < 0) {
                bean.setCode(Constants.code_1);
                bean.setMsg("更新余额失败，请稍后重试");
                return bean;
            }
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg("充值操作失败，请稍后重试");
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;
    }

    /**
     * 更新用户表账户余额
     *
     * @param userId      用户id
     * @param tradeType   交易类型：1充值，2提现
     * @param tradeAmount 交易金额
     * @return
     */
    private int updateUserAccountBalance(String userId, String tradeType, int tradeAmount) {
        int updateFlag = -1;
        TbUserExample example = new TbUserExample();
        example.createCriteria()
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1")
                .andUserIdEqualTo(userId);
        List<TbUser> users = tbuserMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            TbUser user = users.get(0);
            // 1.充值
            if ("1".equals(tradeType)) {
                user.setUserAccountBalance(user.getUserAccountBalance() + tradeAmount);
            }
            // 2.提现
            else if ("2".equals(tradeType)) {
                user.setUserAccountBalance(user.getUserAccountBalance() - tradeAmount);
            }
            updateFlag = tbuserMapper.updateByExampleSelective(user, example);
        } else {
            return updateFlag;
        }
        return updateFlag;
    }

    /**
     * 根据用户id查询收支明细
     *
     * @param userId 用户id
     * @return
     */
    public ResultBean getTradeDetail(String userId,int pageNum,int pageSize) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(userId)) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            return bean;
        }
        TbReceiptionExample example = new TbReceiptionExample();
        example.createCriteria()
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1")
                .andUserIdEqualTo(userId);
        PageHelper.startPage(pageNum,pageSize);
        example.setOrderByClause("trade_date_time desc");
        List<TbReceiption> receptions = tbReceiptionMapper.selectByExample(example);
        if(receptions.size()>0){
            List<TradeDetails> detailsList = new ArrayList<>();
            TradeDetails details = null;
            for(TbReceiption reception:receptions){
                details = new TradeDetails();
                details.setTradeName(reception.getTradeType().equals("1")?"充值":"提现");
                details.setTradeDataTime(String.valueOf(reception.getTradeDateTime().getTime()));
                details.setTradeAmount(reception.getTradeAmount());
                detailsList.add(details);
            }
            bean.setResult(detailsList);
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);

        return bean;
    }

    /**
     * 更新用户信息（个人资料编辑和入驻）
     *
     * @param user
     * @return
     */
    @Transactional
    public ResultBean updateUserInfo(String userId,String token,String userName,String major,String idCardNo,
                                     String profession,String idCardImg1,String idCardImg2,String masterIntroduction) {
        ResultBean bean = new ResultBean();
        // 1.查询,userId,主键查询和token
        ResultBean userOperableBean = getUserOperable(userId, token,idCardNo);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria()
                .andUserIdEqualTo(userId).andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0");
        List<TbUser> users = tbuserMapper.selectByExample(tbUserExample);
        if (users.size() != 1) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            return bean;
        }
        TbUser tbUser = users.get(0);
        // 真实姓名
        if (StringUtil.isNotEmpty(userName)) {
            tbUser.setUserName(userName);
            tbUser.setIdCardNo(idCardNo);
        }
        // 联系电话
//        if (StringUtil.isNotEmpty(telephone)) {
//            tbUser.setTelephone(telephone);
//        }
        // 职业
        if (StringUtil.isNotEmpty(profession)) {
            tbUser.setProfession(profession);
        }
        // 擅长分类
        if (StringUtil.isNotEmpty(major)) {
            tbUser.setMajor(major);
        }
        // 证件照片（正反面）
        if (StringUtil.isNotEmpty(idCardImg1) && StringUtil.isNotEmpty(idCardImg2)) {
            tbUser.setIdCardImg1(idCardImg1);
            tbUser.setIdCardImg2(idCardImg2);
        }
        // 名师简介（个人介绍）
        if (StringUtil.isNotEmpty(masterIntroduction)) {
            tbUser.setMasterIntroduction(masterIntroduction);
        }

        //2.发送商家入驻申请:将apply_status,apply_time赋值
        tbUser.setApplyStatus("1");
        tbUser.setApplyTime(new Date());

        int update = tbuserMapper.updateByExampleSelective(tbUser, tbUserExample);
        if (update <= 0) {
            bean.setCode(Constants.code_1);
            bean.setMsg("数据库连接失败");
            return bean;
        }



        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;
    }

    /**
     * 我的-个人信息修改
     *
     * @return
     */
    public ResultBean updateMyInfo(String userId, String token, String nickName, String headImg, String asign,
                                   String handphone, String mobileCode) {
        ResultBean bean = new ResultBean();
        // 1.查询,userId,主键查询和token
        ResultBean userOperableBean = getUserOperable(userId, token,null);
        if (userOperableBean.getCode() != 0) {
            return userOperableBean;
        }

        TbUser user = new TbUser();

        // 手机号
        if (StringUtil.isNotEmpty(handphone)) {
            TbUser userInDB = (TbUser) userOperableBean.getResult();
            if (Integer.parseInt(mobileCode) != userInDB.getSms()) {
                bean.setCode(Constants.code_1);
                bean.setMsg("验证码错误");
                return bean;
            }
            user.setHandphone(handphone);
        }
        user.setUserId(userId);
        // 头像
        if (StringUtil.isNotEmpty(headImg)) {
            user.setHeadImg(headImg);
        }
        // 昵称
        if (StringUtil.isNotEmpty(nickName)) {
            user.setNickName(nickName);
        }
        // 个性签名
        if (StringUtil.isNotEmpty(asign)) {
            user.setAsign(asign);
        }
        int result = tbuserMapper.updateByPrimaryKeySelective(user);
        if (result > 0) {
            bean.setCode(Constants.code_0);
            bean.setMsg("保存成功");
        }
        return bean;

    }

    /**
     * 验证当前的用户是否可以执行跟新操作
     *
     * @param userId
     * @param token
     * @return
     */
    public ResultBean getUserOperable(String userId, String token,String idCardNo) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(userId)) {
            bean.setCode(Constants.code_1);
            bean.setMsg("userId不能为空");
            return bean;
        }

        if (StringUtil.isEmpty(token)) {
            bean.setCode(Constants.code_1);
            bean.setMsg("token不能为空");
            return bean;
        }
        //主键查询
        TbUser tbUser = tbuserMapper.selectByPrimaryKey(userId);
        if (tbUser == null) {
            bean.setCode(Constants.code_1);
            bean.setMsg("当前用户不存在");
            return bean;
        }
        if (tbUser.getIdCardNo()!=null && tbUser.getIdCardNo().equals(idCardNo)) {
            bean.setCode(Constants.code_1);
            bean.setMsg("当前用户已经入驻");
            return bean;
        }
        //token 验证
        if (!tbUser.getToken().equals(token)) {
            bean.setCode(Constants.code_2);
            bean.setMsg("当前token已过期,请重新登录");
            return bean;
        }
        bean.setCode(Constants.code_0);
        bean.setResult(tbUser);
        return bean;
    }

    /**
     * 根据userId 查询用户信息(我的-个人)
     * 个人的, 头像、昵称、签名、手机号、账户余额
     *
     * @param user
     * @return
     */
    public ResultBean getMyInfoByUserId(String userId) {
        ResultBean bean = new ResultBean();
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0").andUserIdEqualTo(userId);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(tbUserExample);
        if (tbUsers != null && tbUsers.size() > 0) {
            TbUser tbUser = tbUsers.get(0);
            bean.setResult(new MyUserInfo(tbUser.getNickName(), tbUser.getHeadImg(), tbUser.getHandphone(),
                    tbUser.getAsign(), tbUser.getUserAccountBalance()));
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg("该用户账号异常!");
        }

        return bean;
    }

    /**
     * 根据userId 查询用户信息(我的-个人)
     * 个人的, 头像、昵称
     *
     * @param userId
     * @return
     */
    public ResultBean getHeadAndNameByUserId(String userId) {
        ResultBean bean = new ResultBean();
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0").andUserIdEqualTo(userId);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(tbUserExample);
        if (tbUsers != null && tbUsers.size() > 0) {
            TbUser tbUser = tbUsers.get(0);
            Map<String, String> infoMap = new HashMap<>();
            infoMap.put("headImg", tbUser.getHeadImg());
            infoMap.put("nickName", tbUser.getNickName());
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
            bean.setResult(infoMap);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg("该用户账号异常!");
        }

        return bean;
    }

    /**
     * 根据名师userId获取详附+评论列表（3条数据）
     *
     * @param userId
     * @return
     */
    public ResultBean getTeacherByUserId(String userId) {
        ResultBean bean = new ResultBean();
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0")
                .andIsMasterEqualTo("1").andUserIdEqualTo(userId);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(tbUserExample);
        TeacherDetails details = null;
        if (tbUsers != null && tbUsers.size() == 1) {
            TbUser teacher = tbUsers.get(0);
            //1.服务数量（查询订单）2:测算服务
            Map<String, List<TbComment>> userCommentMap = new HashMap<>();
            ArrayList<String> objects = new ArrayList<>();
            objects.add(teacher.getUserId());
            List<TbOrder> orderList = getOrderListByMasterUserId(objects, "2");
            List<TbComment> commentList = null;
            if (orderList.size() > 0) {
                List<String> orderIds = new ArrayList<>();
                for (TbOrder order : orderList) {
                    orderIds.add(order.getOrderId());
                }
                //2.2 根据所有订单取的评论
                commentList = getCommentList(orderIds, "1");
            } else {
                commentList = new ArrayList<>();
            }

            int serviceNum = orderList.size();
            //2.根据订单获取评论数（获取评论列表:默认取前三条）3:服务评论
            List<String> orderIdList = new ArrayList<>();

            int commentNum = commentList.size();
            //3.取评论表的平均评分（根据userId）评论类型:3:服务评论
            int totalScore = 0;
            if (commentNum > 0) {
                for (TbComment comment : commentList) {
                    totalScore += comment.getCommentScore();
                }
            }
            String commentScore = CalculateUtils.division(totalScore, commentNum);

            //4.擅长专业
            List<String> masterMajors = getMasterMajors(teacher.getMajor());
            //5.根据订单列表->取精选评论列表(3条)
            List<TbComment> pickCommentList = new ArrayList<>();
            int size = commentList.size();
            if (size > 3) {
                size = 3;
            }
            for (int i = 0; i < size; i++) {
                pickCommentList.add(commentList.get(i));
            }

            List<ServiceComment> pickedCommentList = commentService.getShowBackCommentList(pickCommentList, orderList);
            details = new TeacherDetails(teacher.getUserName(), teacher.getHeadImg(), serviceNum, commentNum, commentScore,
                    teacher.getMasterIntroduction(), masterMajors, pickedCommentList);
        }
        bean.setMsg(Constants.msg_success);
        bean.setResult(details);
        bean.setCode(Constants.code_0);
        return bean;
    }

    /**
     * 根据名师userIdList查询取得订单列表
     *
     * @param userIds
     * @param orderType
     * @return List<TbOrder>
     */
    public List<TbOrder> getOrderListByMasterUserId(List<String> userIds, String orderType) {
        TbOrderExample example = new TbOrderExample();
        //订单状态为1：已下单，2：已付款，3：已评价
        example.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0")
                .andOrderTypeEqualTo(orderType).andGoodBelongToIn(userIds).andOrderStatusEqualTo("3");
        example.setOrderByClause("order_comment_time desc");
        List<TbOrder> tbOrders = orderMapper.selectByExample(example);
        if (tbOrders == null) {
            return new ArrayList<>();
        }
        return tbOrders;
    }

    /**
     * 根据名师userId查询取得订单列表
     *
     * @param userId
     * @param orderType
     * @return List<TbOrder>
     */
    public List<TbOrder> getOrderListByMasterUserId(int pageNum, int pageSize, String userId, String orderType) {
        TbOrderExample example = new TbOrderExample();
        //订单状态为1：已下单，2：已付款，3：已评价
        example.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0")
                .andOrderTypeEqualTo(orderType).andGoodBelongToEqualTo(userId).andOrderStatusEqualTo("3");
        example.setOrderByClause("order_comment_time desc");
        PageHelper.startPage(pageNum, pageSize);
        List<TbOrder> tbOrders = orderMapper.selectByExample(example);
        if (tbOrders == null) {
            return new ArrayList<>();
        }
        return tbOrders;
    }

    /**
     * 根据用户userIds查询用户
     *
     * @param userIds
     * @return List<TbUser>
     */
    public List<TbUser> getUsersByUserIds(List<String> userIds) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0").andUserIdIn(userIds);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(tbUserExample);

        if (tbUsers == null) {
            tbUsers = new ArrayList<>();
        }

        return tbUsers;

    }

    /**
     * 取名师服务分类
     * 课程分类的id:24
     *
     * @return
     */
    public ResultBean getServiceType() {
        List<UserServiceType> codeList = new ArrayList<>();
        ResultBean bean = new ResultBean();
        TbCategoryExample example = new TbCategoryExample();
        //课程分类的id:24
        example.createCriteria().andParentIdEqualTo(Long.parseLong("24")).andStatusEqualTo(1);
        List<TbCategory> tbCategories = tbCategoryMapper.selectByExample(example);
        if (tbCategories != null) {
            UserServiceType code = null;
            for (int i = 0; i < tbCategories.size(); i++) {
                code = new UserServiceType();
                code.setId(tbCategories.get(i).getId());
                code.setName(tbCategories.get(i).getName());
                code.setKeyWords(tbCategories.get(i).getKeyWords());
                code.setImgPath(tbCategories.get(i).getImgPath());
                codeList.add(code);
            }
            bean.setResult(codeList);
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
        } else {
            bean.setResult(null);
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
        }
        return bean;
    }

    /**
     * 根据名师的userId取名师的服务列表
     *
     * @return
     */
    public ResultBean getServiceItemByUserId(String userId) {
        ResultBean bean = new ResultBean();
        TbServiceExample example = new TbServiceExample();
        TbServiceExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0");
        List<TbService> tbServices = serviceMapper.selectByExample(example);
        List<ServiceItem> serviceItems = new ArrayList<>();
        if (tbServices != null) {
            ServiceItem item = null;
            for (TbService service : tbServices) {
                item = new ServiceItem();
                item.setServiceId(service.getServiceId());
                item.setServiceName(service.getServiceName());
                item.setServicePrice(service.getServicePrice());
                serviceItems.add(item);
            }
            bean.setResult(serviceItems);
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        } else {
            bean.setResult("该名师暂时无服务条目");
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        }
        return bean;
    }

    public ResultBean recordVideoPercent(String userId, String token, String catalogId, float learningPercent,float currentSecond) {
        ResultBean bean = new ResultBean();
        //1.身份验证
        TbUser user = tbuserMapper.selectByPrimaryKey(userId);
        if (!user.getToken().equals(token)) {
            bean.setCode(Constants.code_2);
            bean.setMsg(Constants.msg_invalid);
            return bean;
        }
        //2.操作记录
        //2.1 查询DB
        TbLearningExample learningExample = new TbLearningExample();
        TbLearningExample.Criteria criteria = learningExample.createCriteria();
        criteria.andCatalogIdEqualTo(catalogId).andUserIdEqualTo(userId);
        List<TbLearning> tbLearnings = tbLearningMapper.selectByExample(learningExample);
        if (tbLearnings != null && tbLearnings.size() > 0) {
            //存在记录，执行更新操作
            TbLearning tbLearning = tbLearnings.get(0);
            tbLearning.setLearningPercent(learningPercent);
            tbLearning.setCurrentSecond(currentSecond);
            tbLearning.setUpdateTime(new Date());
            int update = tbLearningMapper.updateByPrimaryKeySelective(tbLearning);
            if (update > 0) {
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
                return bean;
            }
        } else {
            TbCatalog catalog = tbCatalogMapper.selectByPrimaryKey(Long.parseLong(catalogId));
            //不存在，执行插入操作
            TbLearning tbLearning = new TbLearning();
            tbLearning.setLearningId(IDUtils.genItemId());
            tbLearning.setUserId(userId);
            tbLearning.setCatalogId(catalogId);
            tbLearning.setCourseId(catalog.getCourseId());
            tbLearning.setLearningPercent(learningPercent);
            tbLearning.setCurrentSecond(currentSecond);
            tbLearning.setCreateTime(new Date());
            tbLearning.setUpdateTime(new Date());
            int insertSelective = tbLearningMapper.insertSelective(tbLearning);
            if (insertSelective > 0) {
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
                return bean;
            }
        }

        return null;

    }

    /**
     * 构建userId--user Map
     *
     * @param users
     * @return
     */
    public Map<String, TbUser> getIdUserMap(List<TbUser> users) {
        Map<String, TbUser> userMap = new HashMap<>();
        for (TbUser user : users) {
            if (!userMap.containsKey(user.getUserId())) {
                userMap.put(user.getUserId(), user);
            }
        }
        return userMap;
    }

    public EasyUIDataGridResult getMasterSettledList(int page, int rows) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andApplyStatusIn(Constants.apply_Status_LIST)
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1");
        PageHelper.startPage(page,rows);
        example.setOrderByClause("apply_status asc,apply_time asc");
        List<TbUser> userList = tbuserMapper.selectByExample(example);
        result.setRows(userList);
        PageInfo<TbUser> pageInfo = new PageInfo<>(userList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    public Map<String, String> deleteUserById(String userId) {
        Map<String,String> infoMap = new HashMap<>();
        TbUser user = tbuserMapper.selectByPrimaryKey(userId);
        if(user!=null && user.getUserId()!=null){
            //1.删除课程（逻辑删除）
            user.setDeleteFlag("1");
            int delete = tbuserMapper.updateByPrimaryKeySelective(user);
            //2.删除对应的课程目录
            if(delete>0){
                infoMap.put("code","0");
                infoMap.put("msg","删除成功!");
                return infoMap;
            }

        }
        infoMap.put("code","1");
        infoMap.put("msg","删除失败!");
        return infoMap;
    }

    public Map<String, String> saveServiceByMasterId(String userId,String serviceId, String serviceName, int servicePrice) {
        Map<String, String> infoMap = new HashMap<>();
        //测算服务id->name
        String serviceNameTemp = courseService.getCategoryName(Long.parseLong(serviceName));
        TbService tbService = new TbService();
        tbService.setServiceName(serviceName);
        TbServiceExample example = new TbServiceExample();
        TbServiceExample.Criteria criteria = example.createCriteria();
        criteria.andServiceNameEqualTo(serviceNameTemp);
        List<TbService> tbServices = serviceMapper.selectByExample(example);
        if (tbServices.size()>0){
            infoMap.put("code","1");
            infoMap.put("msg","该项测算服务已存在");
            return infoMap;
        }
        if (StringUtil.isEmpty(serviceId)){
            //不存在，执行保存逻辑
            tbService.setServiceId(IDUtils.genItemId());
            tbService.setUserId(userId);
            tbService.setServiceName(serviceNameTemp);
            tbService.setRemark1(serviceName);
            tbService.setServicePrice(servicePrice);
            int i = serviceMapper.insertSelective(tbService);
            if (i < 0){
                infoMap.put("code","1");
                infoMap.put("msg","保存失败");
                return infoMap;
            }
            infoMap.put("code","0");
            infoMap.put("msg","保存成功");
        } else {
            //1.查询
            TbService service = serviceMapper.selectByPrimaryKey(serviceId);
            if (service.getUserId()!=null){
                //已存在
                //更新
                tbService.setUserId(userId);
                tbService.setServiceId(serviceId);
                //测算服务id->name
                tbService.setServiceName(serviceNameTemp);
                tbService.setRemark1(serviceName);
                tbService.setServicePrice(servicePrice);
                int update = serviceMapper.updateByPrimaryKeySelective(tbService);
                if (update<0){
                    infoMap.put("code","1");
                    infoMap.put("msg","保存失败");
                } else {
                    infoMap.put("code","0");
                    infoMap.put("msg","保存成功");
                }
            } else {
                infoMap.put("code","1");
                infoMap.put("msg","当前服务已失效");
            }
            return infoMap;
        }


        return infoMap;

    }

    public EasyUIDataGridResult getMasterServerList(String userId) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbServiceExample example = new TbServiceExample();
        TbServiceExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1")
                .andUserIdEqualTo(userId);
        List<TbService> tbServices = serviceMapper.selectByExample(example);
        result.setRows(tbServices);
        PageInfo<TbService> pageInfo = new PageInfo<>(tbServices);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    public Map<String, String> checkOutMaster(String idNum, String userName) {
        Map<String, String> infoMap = new HashMap<>();
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName)
                .andIdCardNoEqualTo(idNum)
                .andIsMasterEqualTo("1")
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1");
        List<TbUser> userList = tbuserMapper.selectByExample(example);
        if (userList.size() == 1){
            infoMap.put("code","0");
        } else {
            infoMap.put("code","1");
        }
        return infoMap;
    }

    public Map<String, String> deleteServiceByServiceId(String serviceId) {
        Map<String, String> infoMap = new HashMap<>();
        int i = serviceMapper.deleteByPrimaryKey(serviceId);
        if (i>0){
            infoMap.put("code","0");
        } else {
            infoMap.put("code","1");
        }
        return infoMap;
    }

    public Map<String, String> saveMasterIdNo(String userId, String idCardNo) {
        Map<String, String> infoMap = new HashMap<>();
        //1.查询
        TbUser user = tbuserMapper.selectByPrimaryKey(userId);
        if (user == null){
            infoMap.put("code","1");
            infoMap.put("msg","该名师不存在");
            return infoMap;
        }

        TbUser tbUser = new TbUser();
        tbUser.setIdCardNo(idCardNo);
        tbUser.setUserId(userId);
        int update = tbuserMapper.updateByPrimaryKeySelective(tbUser);
        if (update>0){
            infoMap.put("code","0");
            infoMap.put("msg","保存成功");
        }else {
            infoMap.put("code","0");
            infoMap.put("msg","保存失败");
        }
        return infoMap;
    }

    public Map<String, String> approval(String userId, String flag, String approvalOpinion) {
        Map<String, String> infoMap = new HashMap<>();
        TbUser tbUser = tbuserMapper.selectByPrimaryKey(userId);
        if(!tbUser.getApplyStatus().equals("1")){
            infoMap.put("code","1");
            infoMap.put("msg","名师状态错误，请刷新重试");
            return infoMap;
        }
        //get flag
        if (flag.equals("0")){
            //驳回
            tbUser.setApplyStatus("3");
        } else {
            //通过
            tbUser.setApplyStatus("2");
            tbUser.setIsMaster("1");
        }
        tbUser.setApprovalOpinion(approvalOpinion);
        int update = tbuserMapper.updateByPrimaryKeySelective(tbUser);
        if (update<=0){
            infoMap.put("code","0");
            infoMap.put("msg","保存失败");
            return infoMap;
        }
        infoMap.put("code","0");
        infoMap.put("msg","保存成功");
        return infoMap;
    }

    public Map<String, String> orderApprove(String orderStatus, String orderId) {
        Map<String, String> infoMap = new HashMap<>();
        //查询订单状态
        TbOrder order = tbOrderMapper.selectByPrimaryKey(orderId);
        if (!order.getOrderStatus().equals("4")){
            infoMap.put("code","1");
            infoMap.put("msg","订单状态异常，请刷新重试！");
            return infoMap;
        }
        order.setOrderRefundEtime(new Date());
        order.setOrderStatus(orderStatus);
        int update = tbOrderMapper.updateByPrimaryKeySelective(order);
        if (update<=0){
            infoMap.put("code","1");
            infoMap.put("msg","操作失败，请刷新重试");
            return infoMap;
        }
        infoMap.put("code","0");
        infoMap.put("msg","操作成功");
        return infoMap;
    }

    public ResultBean getMasterApplyInfo(String userId) {
        ResultBean bean = new ResultBean();
        TbUser tbUser = tbuserMapper.selectByPrimaryKey(userId);
        MasterInfo masterInfo = new MasterInfo();
        BeanUtils.copyProperties(tbUser,masterInfo);
        if (tbUser.getApplyStatus().equals("0")){
            masterInfo.setApplyStatus("待申请");
        } else if (tbUser.getApplyStatus().equals("1")){
            masterInfo.setApplyStatus("待审批");
        } else if (tbUser.getApplyStatus().equals("2")){
            masterInfo.setApplyStatus("已通过");
        } else {
            masterInfo.setApplyStatus("已驳回");
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(masterInfo);
        return bean;
    }

    public Map<String, Integer> getApprovalCount() {
        Map<String, Integer> infoMap = new HashMap<>();
        //1.rz
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andApplyStatusEqualTo("1");
        infoMap.put("rz",tbuserMapper.countByExample(userExample));
        //2.tk
        TbOrderExample orderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria1 = orderExample.createCriteria();
        criteria1.andOrderStatusEqualTo("4");
        infoMap.put("tk",tbOrderMapper.countByExample(orderExample));

        //3.pl
        TbCommentExample commentExample = new TbCommentExample();
        TbCommentExample.Criteria criteria2 = commentExample.createCriteria();
        criteria2.andRemark1EqualTo("0");
        infoMap.put("pl",commentMapper.countByExample(commentExample));
        infoMap.put("code",0);
        return infoMap;
    }

    public Map<String, Integer> read(String messageId) {
        Map<String, Integer> info = new HashMap<>();
        TbMessage message = messageMapper.selectByPrimaryKey(messageId);
        if (!message.getIsRead().equals("0")) {
            info.put("code",1);
        }else {
            message.setIsRead("1");
            int update = messageMapper.updateByPrimaryKeySelective(message);
            if (update>0){
                info.put("code",0);
            } else {
                info.put("code",1);
            }
        }
        return info;

    }

    //反馈消息删除
    public Map<String, Integer> deleteFeedBack(String messageId) {
        Map<String, Integer> infoMap = new HashMap<>();
        String[] split = messageId.split(",");
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(split[i]);
        }
        TbMessageExample example = new TbMessageExample();
        TbMessageExample.Criteria criteria = example.createCriteria();
        criteria.andMessageIdIn(idList);
        int i = messageMapper.deleteByExample(example);
        if (i>0){
            infoMap.put("code",0);
        } else {
            infoMap.put("code",1);
        }
        return infoMap;
    }

    public Map<String, String> addAdmin(HttpServletRequest request,String userName, String password) {
        Map<String, String> infoMap = new HashMap<>();
        //判断当前用户
        TbUser userFromSession = baseService.getUserFromSession(request);
        if (userFromSession != null && !"admin".equals(userFromSession.getUserName())){
            infoMap.put("code","1");
            infoMap.put("msg","权限不足");
            return infoMap;
        }
        //1.查询管理员
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName)
                .andIsAdministratorEqualTo("1");
        List<TbUser> userList = tbuserMapper.selectByExample(example);
        if (userList.size()!=0){
            infoMap.put("code","1");
            infoMap.put("msg","用户名已存在，请重新输入");
            return infoMap;
        }
        TbUser user = new TbUser();
        user.setUserId(IDUtils.genItemId());
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setLastLoginTime(new Date());
        user.setRegisterTime(new Date());
        user.setIsAdministrator("1");
        //2.新增
        int i = tbuserMapper.insertSelective(user);
        if (i>0){
            infoMap.put("code","0");
        } else {
            infoMap.put("code","0");
            infoMap.put("msg","操作失败，请稍后重试");
        }
        return infoMap;

    }

    public Map<String, String> editAdmin(HttpServletRequest request,String userName, String password, String password_n) {
        Map<String, String> infoMap = new HashMap<>();
        //判断当前用户
        TbUser userFromSession = baseService.getUserFromSession(request);
        if (userFromSession != null && !"admin".equals(userFromSession.getUserName())){
            infoMap.put("code","1");
            infoMap.put("msg","权限不足");
            return infoMap;
        }
        //1.查询管理员
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName)
                .andIsAdministratorEqualTo("1")
                .andUserPasswordEqualTo(password);
        List<TbUser> userList = tbuserMapper.selectByExample(example);
        if (userList.size() != 1){
            infoMap.put("code","1");
            infoMap.put("msg","原密码错误");
            return infoMap;
        } else {
            TbUser user = userList.get(0);
            user.setUserPassword(password_n);
            infoMap.put("code","0");
        }
        return infoMap;
    }

    public Map<String, String> deleteAdmin(HttpServletRequest request,String userId) {
        Map<String, String> infoMap = new HashMap<>();
        //判断当前用户
        TbUser userFromSession = baseService.getUserFromSession(request);
        if (userFromSession != null && !"admin".equals(userFromSession.getUserName())){
            infoMap.put("code","1");
            infoMap.put("msg","权限不足");
            return infoMap;
        }
        int i = tbuserMapper.deleteByPrimaryKey(userId);
        if (i>0){
            infoMap.put("code","0");
        } else {
            infoMap.put("code","1");
        }
        return infoMap;
    }

    @Transactional
    public Map<String, String> deleteMaster(String userId) {
        Map<String, String> infoMap = new HashMap<>();
        int i = tbuserMapper.deleteByPrimaryKey(userId);
        if (i>0){
            //清空名师的相关信息
            //1.课程相关
            TbCourseWithBLOBs tbCourse = new TbCourseWithBLOBs();
            tbCourse.setCourseBelongto(userId);
            tbCourse.setEffectFlag("0");
            TbCourseExample example = new TbCourseExample();
            TbCourseExample.Criteria criteria = example.createCriteria();
            criteria.andCourseBelongtoEqualTo(userId);
            int i1 = tbCourseMapper.updateByExampleSelective(tbCourse, example);
            //2.服务相关
            TbService tbService = new TbService();
            tbService.setEffectFlag("0");
            TbServiceExample serviceExample = new TbServiceExample();
            TbServiceExample.Criteria criteria1 = serviceExample.createCriteria();
            criteria1.andUserIdEqualTo(userId);
            int i2 = tbServiceMapper.updateByExampleSelective(tbService, serviceExample);
            infoMap.put("code","0");

        } else {
            infoMap.put("code","1");
        }
        return infoMap;
    }
}
