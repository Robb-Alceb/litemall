package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallSubscribeGoodsPrice;
import org.linlinjava.litemall.db.domain.LitemallSubscribeGoodsPriceExample;

public interface LitemallSubscribeGoodsPriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    long countByExample(LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int insert(LitemallSubscribeGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int insertSelective(LitemallSubscribeGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    LitemallSubscribeGoodsPrice selectOneByExample(LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    LitemallSubscribeGoodsPrice selectOneByExampleSelective(@Param("example") LitemallSubscribeGoodsPriceExample example, @Param("selective") LitemallSubscribeGoodsPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    List<LitemallSubscribeGoodsPrice> selectByExampleSelective(@Param("example") LitemallSubscribeGoodsPriceExample example, @Param("selective") LitemallSubscribeGoodsPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    List<LitemallSubscribeGoodsPrice> selectByExample(LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    LitemallSubscribeGoodsPrice selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallSubscribeGoodsPrice.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    LitemallSubscribeGoodsPrice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    LitemallSubscribeGoodsPrice selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallSubscribeGoodsPrice record, @Param("example") LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallSubscribeGoodsPrice record, @Param("example") LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallSubscribeGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallSubscribeGoodsPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallSubscribeGoodsPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe_goods_price
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}