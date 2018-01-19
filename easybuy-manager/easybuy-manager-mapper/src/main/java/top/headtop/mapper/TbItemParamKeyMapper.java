package top.headtop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.headtop.pojo.TbItemParamKey;
import top.headtop.pojo.TbItemParamKeyExample;

public interface TbItemParamKeyMapper {
    int countByExample(TbItemParamKeyExample example);

    int deleteByExample(TbItemParamKeyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemParamKey record);

    int insertSelective(TbItemParamKey record);

    List<TbItemParamKey> selectByExampleWithBLOBs(TbItemParamKeyExample example);

    List<TbItemParamKey> selectByExample(TbItemParamKeyExample example);

    TbItemParamKey selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItemParamKey record, @Param("example") TbItemParamKeyExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemParamKey record, @Param("example") TbItemParamKeyExample example);

    int updateByExample(@Param("record") TbItemParamKey record, @Param("example") TbItemParamKeyExample example);

    int updateByPrimaryKeySelective(TbItemParamKey record);

    int updateByPrimaryKeyWithBLOBs(TbItemParamKey record);

    int updateByPrimaryKey(TbItemParamKey record);
}