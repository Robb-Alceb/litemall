package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallGiftCard;
import org.linlinjava.litemall.db.domain.LitemallGiftCardExample;

public interface LitemallGiftCardMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    long countByExample(LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int insert(LitemallGiftCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int insertSelective(LitemallGiftCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    LitemallGiftCard selectOneByExample(LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    LitemallGiftCard selectOneByExampleSelective(@Param("example") LitemallGiftCardExample example, @Param("selective") LitemallGiftCard.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    List<LitemallGiftCard> selectByExampleSelective(@Param("example") LitemallGiftCardExample example, @Param("selective") LitemallGiftCard.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    List<LitemallGiftCard> selectByExample(LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    LitemallGiftCard selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGiftCard.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    LitemallGiftCard selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    LitemallGiftCard selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallGiftCard record, @Param("example") LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallGiftCard record, @Param("example") LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallGiftCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallGiftCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallGiftCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}