package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbReceiption;
import com.master.qianyi.pojo.TbReceiptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbReceiptionMapper {
    int countByExample(TbReceiptionExample example);

    int deleteByExample(TbReceiptionExample example);

    int deleteByPrimaryKey(String receiptId);

    int insert(TbReceiption record);

    int insertSelective(TbReceiption record);

    List<TbReceiption> selectByExample(TbReceiptionExample example);

    TbReceiption selectByPrimaryKey(String receiptId);

    int updateByExampleSelective(@Param("record") TbReceiption record, @Param("example") TbReceiptionExample example);

    int updateByExample(@Param("record") TbReceiption record, @Param("example") TbReceiptionExample example);

    int updateByPrimaryKeySelective(TbReceiption record);

    int updateByPrimaryKey(TbReceiption record);
}