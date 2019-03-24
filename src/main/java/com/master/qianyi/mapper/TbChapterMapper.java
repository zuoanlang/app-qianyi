package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbChapter;
import com.master.qianyi.pojo.TbChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbChapterMapper {
    int countByExample(TbChapterExample example);

    int deleteByExample(TbChapterExample example);

    int deleteByPrimaryKey(String chapterId);

    int insert(TbChapter record);

    int insertSelective(TbChapter record);

    List<TbChapter> selectByExample(TbChapterExample example);

    TbChapter selectByPrimaryKey(String chapterId);

    int updateByExampleSelective(@Param("record") TbChapter record, @Param("example") TbChapterExample example);

    int updateByExample(@Param("record") TbChapter record, @Param("example") TbChapterExample example);

    int updateByPrimaryKeySelective(TbChapter record);

    int updateByPrimaryKey(TbChapter record);
}