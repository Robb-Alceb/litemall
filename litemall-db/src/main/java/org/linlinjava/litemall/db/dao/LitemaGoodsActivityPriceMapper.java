package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemaGoodsActivityPrice;
import org.linlinjava.litemall.db.domain.LitemaGoodsActivityPriceExample;

public interface LitemaGoodsActivityPriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    long countByExample(LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int deleteByExample(LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int insert(LitemaGoodsActivityPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int insertSelective(LitemaGoodsActivityPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    LitemaGoodsActivityPrice selectOneByExample(LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    LitemaGoodsActivityPrice selectOneByExampleSelective(@Param("example") LitemaGoodsActivityPriceExample example, @Param("selective") LitemaGoodsActivityPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    List<LitemaGoodsActivityPrice> selectByExampleSelective(@Param("example") LitemaGoodsActivityPriceExample example, @Param("selective") LitemaGoodsActivityPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    List<LitemaGoodsActivityPrice> selectByExample(LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    LitemaGoodsActivityPrice selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemaGoodsActivityPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    LitemaGoodsActivityPrice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    LitemaGoodsActivityPrice selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemaGoodsActivityPrice record, @Param("example") LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemaGoodsActivityPrice record, @Param("example") LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemaGoodsActivityPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemaGoodsActivityPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemaGoodsActivityPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litema_goods_activity_price
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}