package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallOrderCash;
import org.linlinjava.litemall.db.domain.LitemallOrderCashExample;

public interface LitemallOrderCashMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    long countByExample(LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int insert(LitemallOrderCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int insertSelective(LitemallOrderCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    LitemallOrderCash selectOneByExample(LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    LitemallOrderCash selectOneByExampleSelective(@Param("example") LitemallOrderCashExample example, @Param("selective") LitemallOrderCash.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    List<LitemallOrderCash> selectByExampleSelective(@Param("example") LitemallOrderCashExample example, @Param("selective") LitemallOrderCash.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    List<LitemallOrderCash> selectByExample(LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    LitemallOrderCash selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallOrderCash.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    LitemallOrderCash selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    LitemallOrderCash selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallOrderCash record, @Param("example") LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallOrderCash record, @Param("example") LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallOrderCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallOrderCash record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallOrderCashExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_cash
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}