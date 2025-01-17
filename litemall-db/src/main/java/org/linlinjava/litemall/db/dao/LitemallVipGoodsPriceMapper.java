package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPrice;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPriceExample;

public interface LitemallVipGoodsPriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    long countByExample(LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int insert(LitemallVipGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int insertSelective(LitemallVipGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    LitemallVipGoodsPrice selectOneByExample(LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    LitemallVipGoodsPrice selectOneByExampleSelective(@Param("example") LitemallVipGoodsPriceExample example, @Param("selective") LitemallVipGoodsPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    List<LitemallVipGoodsPrice> selectByExampleSelective(@Param("example") LitemallVipGoodsPriceExample example, @Param("selective") LitemallVipGoodsPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    List<LitemallVipGoodsPrice> selectByExample(LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    LitemallVipGoodsPrice selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallVipGoodsPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    LitemallVipGoodsPrice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    LitemallVipGoodsPrice selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallVipGoodsPrice record, @Param("example") LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallVipGoodsPrice record, @Param("example") LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallVipGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallVipGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallVipGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}