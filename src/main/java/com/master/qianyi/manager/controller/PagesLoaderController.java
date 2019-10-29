package com.master.qianyi.manager.controller;

import com.master.qianyi.comment.form.InfoComment;
import com.master.qianyi.information.form.DetailsInformation;
import com.master.qianyi.information.service.InformationService;
import com.master.qianyi.manager.pojo.CourseInfo;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.pojo.TbCourseWithBLOBs;
import com.master.qianyi.pojo.TbOrderExample;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * project:itcast-microservice-user
 * function:
 * author:kangkang
 * date: 2019/2/26
 */
@Controller
@RequestMapping("/pages")
public class PagesLoaderController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @RequestMapping("/item-add")
    public String itemAdd(){
        return "/pages/item-add";
    }

    @RequestMapping("/content")
    public String content(){
        return "/pages/content";
    }

    @RequestMapping("/content-category")
    public String contentCategory(){
        return "/pages/content-category";
    }

    @RequestMapping("/content-add")
    public String contentAdd(){
        return "/pages/content-add";
    }

    @RequestMapping("/content-edit")
    public String contentEdit(){
        return "/pages/content-edit";
    }

    @RequestMapping("/file-upload")
    public String fileUpload(){
        return "/pages/file-upload";
    }

    //******************用户管理******************
    @RequestMapping("/user-feedback")
    public String userList(){
        return "/pages/user-feedback";
    }

    @RequestMapping("/user-list")
    public String userParamList(){
        return "/pages/user-list";
    }

    @RequestMapping("/user-teacherList")
    public String userTeacherList(){
        return "/pages/user-teacherList";
    }

    @RequestMapping("/user-service")
    public String userService(){
        return "/pages/user-service";
    }

    @RequestMapping("/user-serviceE")
    public String userServiceE(){
        return "/pages/user-serviceE";
    }

    @RequestMapping("/user-edit")
    public String userEdit(){
        return "/pages/user-edit";
    }

    @RequestMapping("/user-admin")
    public String userAdminList(){
        return "/pages/user-admin";
    }

    @RequestMapping("/user-master")
    public String userMasterList(){
        return "/pages/user-master";
    }

    @RequestMapping("/user-admin-add")
    public String userAdminAdd(){
        return "/pages/user-admin-add";
    }

    @RequestMapping("/user-admin-edit")
    public String userAdminEdit(){
        return "/pages/user-admin-edit";
    }

    //******************订单管理******************
    @RequestMapping("/order-list")
    public String orderList(){
        return "/pages/order-list";
    }

    //******************审批管理******************
    @RequestMapping("/approve-refund")
    public String approveRefund(){
        return "/pages/approve-refund";
    }

    @RequestMapping("/approve-details")
    public String approveDetails(){
        return "/pages/approve-details";
    }

    @RequestMapping("/approve-opinion")
    public String approveOpinion(){
        return "/pages/approve-opinion";
    }


    @RequestMapping("/approve-settled")
    public String approveSettled(){
        return "/pages/approve-settled";
    }

    @RequestMapping("/approve-comment")
    public String approveComment(){
        return "/pages/approve-comment";
    }

    //******************课程管理******************
    @RequestMapping("/course-add")
    public String courseAdd(){
        return "/pages/course-add";
    }

    @RequestMapping("/course-edit")
    public String courseEdit(){
        return "/pages/course-edit";
    }

    @RequestMapping("/course-carousel")
    public String courseCarousel(){
        return "/pages/course-carousel";
    }

    @RequestMapping("/course-list")
    public String courseList(){
        return "/pages/course-list";
    }

    //******************资讯模块******************
    @RequestMapping("/info-add")
    public String infoAdd(){
        return "/pages/info-add";
    }

    @RequestMapping("/info-list")
    public String infoList(){
        return "/pages/info-list";
    }

    @RequestMapping("/info-edit")
    public String infoEdit(){
        return "/pages/info-edit";
    }

    @RequestMapping("/news")
    public String showNews(ModelMap modelMap,@RequestParam(value="infoId") String infoId){
        //get infoDetails
        ResultBean resultBean = informationService.getInformationById(infoId);
        Map<String,Object> result = (HashMap)resultBean.getResult();

        if(result == null){
            return "/pages/error";
        }
        ArrayList<InfoComment> infoComments = (ArrayList<InfoComment>) result.get("infoComments");
        DetailsInformation infoDetails = (DetailsInformation) result.get("infoDetails");

        modelMap.put("infoDetails",infoDetails);
        modelMap.put("infoComments",infoComments);

        return "/pages/news";
    }

    @RequestMapping("/courseDetails")
    public String courseDetails(ModelMap modelMap,@RequestParam(value="courseId") String courseId){
        // 1.get course
        TbCourseWithBLOBs course = tbCourseMapper.selectByPrimaryKey(courseId);
        // 2.make orderInfo entity
        TbOrderExample orderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andGoodIdEqualTo(courseId).andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1")
                .andOrderStatusIn(Constants.ordered_Status_LIST);
        int num = tbOrderMapper.countByExample(orderExample);
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseImg(course.getCourseImg());
        courseInfo.setCourseName(course.getCourseName());
        courseInfo.setCourseDate(course.getStartdate()+" 至 "+course.getEnddate());
        courseInfo.setOrderNum(num);
        courseInfo.setDetails(course.getCourseDetails());
        courseInfo.setCoursePrice(course.getCoursePrice()/100);
        courseInfo.setAddress(course.getCourseAddress());
        modelMap.put("courseInfo",courseInfo);

        return "/pages/courseInfo";
    }


}
