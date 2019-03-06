package com.master.qianyi.course.mapper;

import com.master.qianyi.course.pojo.TbCourse;
import com.master.qianyi.course.pojo.TbCourseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCourseMapper {
    int countByExample(TbCourseExample example);

    int deleteByExample(TbCourseExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(TbCourse record);

    int insertSelective(TbCourse record);

    List<TbCourse> selectByExampleWithBLOBs(TbCourseExample example);

    List<TbCourse> selectByExample(TbCourseExample example);

    TbCourse selectByPrimaryKey(String courseId);

    int updateByExampleSelective(@Param("record") TbCourse record, @Param("example") TbCourseExample example);

    int updateByExampleWithBLOBs(@Param("record") TbCourse record, @Param("example") TbCourseExample example);

    int updateByExample(@Param("record") TbCourse record, @Param("example") TbCourseExample example);

    int updateByPrimaryKeySelective(TbCourse record);

    int updateByPrimaryKeyWithBLOBs(TbCourse record);

    int updateByPrimaryKey(TbCourse record);
}