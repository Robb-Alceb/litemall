package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserLog;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserLogExample;

public interface LitemallGiftCardUserLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    long countByExample(LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int insert(LitemallGiftCardUserLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int insertSelective(LitemallGiftCardUserLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    LitemallGiftCardUserLog selectOneByExample(LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    LitemallGiftCardUserLog selectOneByExampleSelective(@Param("example") LitemallGiftCardUserLogExample example, @Param("selective") LitemallGiftCardUserLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    List<LitemallGiftCardUserLog> selectByExampleSelective(@Param("example") LitemallGiftCardUserLogExample example, @Param("selective") LitemallGiftCardUserLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    List<LitemallGiftCardUserLog> selectByExample(LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    LitemallGiftCardUserLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGiftCardUserLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    LitemallGiftCardUserLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    LitemallGiftCardUserLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallGiftCardUserLog record, @Param("example") LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallGiftCardUserLog record, @Param("example") LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallGiftCardUserLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallGiftCardUserLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallGiftCardUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_gift_card_user_log
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}