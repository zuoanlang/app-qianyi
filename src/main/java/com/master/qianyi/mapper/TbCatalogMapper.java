package com.master.qianyi.mapper;

import com.master.qianyi.pojo.TbCatalog;
import com.master.qianyi.pojo.TbCatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCatalogMapper {
    int countByExample(TbCatalogExample example);

    int deleteByExample(TbCatalogExample example);

    int deleteByPrimaryKey(Long catalogId);

    int insert(TbCatalog record);

    int insertSelective(TbCatalog record);

    List<TbCatalog> selectByExampleWithBLOBs(TbCatalogExample example);

    List<TbCatalog> selectByExample(TbCatalogExample example);

    TbCatalog selectByPrimaryKey(Long catalogId);

    int updateByExampleSelective(@Param("record") TbCatalog record, @Param("example") TbCatalogExample example);

    int updateByExampleWithBLOBs(@Param("record") TbCatalog record, @Param("example") TbCatalogExample example);

    int updateByExample(@Param("record") TbCatalog record, @Param("example") TbCatalogExample example);

    int updateByPrimaryKeySelective(TbCatalog record);

    int updateByPrimaryKeyWithBLOBs(TbCatalog record);

    int updateByPrimaryKey(TbCatalog record);
}