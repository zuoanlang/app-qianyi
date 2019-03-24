package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.pojo.TbCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCourseMapper {
    int countByExample(TbCourseExample example);

    int deleteByExample(TbCourseExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(TbCourse record);

    int insertSelective(TbCourse record);

    List<TbCourse> selectByExample(TbCourseExample example);

    TbCourse selectByPrimaryKey(String courseId);

    int updateByExampleSelective(@Param("record") TbCourse record, @Param("example") TbCourseExample example);

    int updateByExample(@Param("record") TbCourse record, @Param("example") TbCourseExample example);

    int updateByPrimaryKeySelective(TbCourse record);

    int updateByPrimaryKey(TbCourse record);
}