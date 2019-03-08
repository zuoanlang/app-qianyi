package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbComment;
import com.master.qianyi.pojo.TbCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCommentMapper {
    int countByExample(TbCommentExample example);

    int deleteByExample(TbCommentExample example);

    int deleteByPrimaryKey(String commentId);

    int insert(TbComment record);

    int insertSelective(TbComment record);

    List<TbComment> selectByExample(TbCommentExample example);

    TbComment selectByPrimaryKey(String commentId);

    int updateByExampleSelective(@Param("record") TbComment record, @Param("example") TbCommentExample example);

    int updateByExample(@Param("record") TbComment record, @Param("example") TbCommentExample example);

    int updateByPrimaryKeySelective(TbComment record);

    int updateByPrimaryKey(TbComment record);
}