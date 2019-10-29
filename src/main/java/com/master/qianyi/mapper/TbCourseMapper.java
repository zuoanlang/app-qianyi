package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.pojo.TbCourseExample;
import com.master.qianyi.pojo.TbCourseWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCourseMapper {
    int countByExample(TbCourseExample example);

    int deleteByExample(TbCourseExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(TbCourseWithBLOBs record);

    int insertSelective(TbCourseWithBLOBs record);

    List<TbCourseWithBLOBs> selectByExampleWithBLOBs(TbCourseExample example);

    List<TbCourse> selectByExample(TbCourseExample example);

    TbCourseWithBLOBs selectByPrimaryKey(String courseId);

    int updateByExampleSelective(@Param("record") TbCourseWithBLOBs record, @Param("example") TbCourseExample example);

    int updateByExampleWithBLOBs(@Param("record") TbCourseWithBLOBs record, @Param("example") TbCourseExample example);

    int updateByExample(@Param("record") TbCourse record, @Param("example") TbCourseExample example);

    int updateByPrimaryKeySelective(TbCourseWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TbCourseWithBLOBs record);

    int updateByPrimaryKey(TbCourse record);
}