package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallGoodsAccessory;
import org.linlinjava.litemall.db.domain.LitemallGoodsAccessoryExample;

public interface LitemallGoodsAccessoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    long countByExample(LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int insert(LitemallGoodsAccessory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int insertSelective(LitemallGoodsAccessory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    LitemallGoodsAccessory selectOneByExample(LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    LitemallGoodsAccessory selectOneByExampleSelective(@Param("example") LitemallGoodsAccessoryExample example, @Param("selective") LitemallGoodsAccessory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    List<LitemallGoodsAccessory> selectByExampleSelective(@Param("example") LitemallGoodsAccessoryExample example, @Param("selective") LitemallGoodsAccessory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    List<LitemallGoodsAccessory> selectByExample(LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    LitemallGoodsAccessory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGoodsAccessory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    LitemallGoodsAccessory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    LitemallGoodsAccessory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallGoodsAccessory record, @Param("example") LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallGoodsAccessory record, @Param("example") LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallGoodsAccessory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallGoodsAccessory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallGoodsAccessoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_accessory
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}