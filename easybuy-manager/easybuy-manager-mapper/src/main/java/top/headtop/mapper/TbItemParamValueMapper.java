package top.headtop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.headtop.pojo.TbItemParamValue;
import top.headtop.pojo.TbItemParamValueExample;

public interface TbItemParamValueMapper {
    int countByExample(TbItemParamValueExample example);

    int deleteByExample(TbItemParamValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemParamValue record);

    int insertSelective(TbItemParamValue record);

    List<TbItemParamValue> selectByExampleWithBLOBs(TbItemParamValueExample example);

    List<TbItemParamValue> selectByExample(TbItemParamValueExample example);

    TbItemParamValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItemParamValue record, @Param("example") TbItemParamValueExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemParamValue record, @Param("example") TbItemParamValueExample example);

    int updateByExample(@Param("record") TbItemParamValue record, @Param("example") TbItemParamValueExample example);

    int updateByPrimaryKeySelective(TbItemParamValue record);

    int updateByPrimaryKeyWithBLOBs(TbItemParamValue record);

    int updateByPrimaryKey(TbItemParamValue record);
}