package com.master.qianyi.mapper;


import com.master.qianyi.pojo.TbInformation;
import com.master.qianyi.pojo.TbInformationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbInformationMapper {
    int countByExample(TbInformationExample example);

    int deleteByExample(TbInformationExample example);

    int deleteByPrimaryKey(String infoId);

    int insert(TbInformation record);

    int insertSelective(TbInformation record);

    List<TbInformation> selectByExampleWithBLOBs(TbInformationExample example);

    List<TbInformation> selectByExample(TbInformationExample example);

    TbInformation selectByPrimaryKey(String infoId);

    int updateByExampleSelective(@Param("record") TbInformation record, @Param("example") TbInformationExample example);

    int updateByExampleWithBLOBs(@Param("record") TbInformation record, @Param("example") TbInformationExample example);

    int updateByExample(@Param("record") TbInformation record, @Param("example") TbInformationExample example);

    int updateByPrimaryKeySelective(TbInformation record);

    int updateByPrimaryKeyWithBLOBs(TbInformation record);

    int updateByPrimaryKey(TbInformation record);
}