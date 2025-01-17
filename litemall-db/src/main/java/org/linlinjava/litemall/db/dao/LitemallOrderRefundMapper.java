package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallOrderRefund;
import org.linlinjava.litemall.db.domain.LitemallOrderRefundExample;

public interface LitemallOrderRefundMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    long countByExample(LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int insert(LitemallOrderRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int insertSelective(LitemallOrderRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    LitemallOrderRefund selectOneByExample(LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    LitemallOrderRefund selectOneByExampleSelective(@Param("example") LitemallOrderRefundExample example, @Param("selective") LitemallOrderRefund.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    List<LitemallOrderRefund> selectByExampleSelective(@Param("example") LitemallOrderRefundExample example, @Param("selective") LitemallOrderRefund.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    List<LitemallOrderRefund> selectByExample(LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    LitemallOrderRefund selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallOrderRefund.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    LitemallOrderRefund selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    LitemallOrderRefund selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallOrderRefund record, @Param("example") LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallOrderRefund record, @Param("example") LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallOrderRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallOrderRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallOrderRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_order_refund
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}