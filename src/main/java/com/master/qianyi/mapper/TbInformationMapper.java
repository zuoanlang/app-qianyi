package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbInformation;
import com.master.qianyi.pojo.TbInformationExample;
import com.master.qianyi.pojo.TbInformationWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInformationMapper {
    int countByExample(TbInformationExample example);

    int deleteByExample(TbInformationExample example);

    int deleteByPrimaryKey(String infoId);

    int insert(TbInformationWithBLOBs record);

    int insertSelective(TbInformationWithBLOBs record);

    List<TbInformationWithBLOBs> selectByExampleWithBLOBs(TbInformationExample example);

    List<TbInformation> selectByExample(TbInformationExample example);

    TbInformationWithBLOBs selectByPrimaryKey(String infoId);

    int updateByExampleSelective(@Param("record") TbInformationWithBLOBs record, @Param("example") TbInformationExample example);

    int updateByExampleWithBLOBs(@Param("record") TbInformationWithBLOBs record, @Param("example") TbInformationExample example);

    int updateByExample(@Param("record") TbInformation record, @Param("example") TbInformationExample example);

    int updateByPrimaryKeySelective(TbInformationWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TbInformationWithBLOBs record);

    int updateByPrimaryKey(TbInformation record);
}