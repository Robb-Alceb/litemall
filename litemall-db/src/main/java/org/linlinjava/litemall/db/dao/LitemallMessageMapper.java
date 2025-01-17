package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallMessage;
import org.linlinjava.litemall.db.domain.LitemallMessageExample;

public interface LitemallMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    long countByExample(LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int insert(LitemallMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int insertSelective(LitemallMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    LitemallMessage selectOneByExample(LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    LitemallMessage selectOneByExampleSelective(@Param("example") LitemallMessageExample example, @Param("selective") LitemallMessage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    LitemallMessage selectOneByExampleWithBLOBs(LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    List<LitemallMessage> selectByExampleSelective(@Param("example") LitemallMessageExample example, @Param("selective") LitemallMessage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    List<LitemallMessage> selectByExampleWithBLOBs(LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    List<LitemallMessage> selectByExample(LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    LitemallMessage selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallMessage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    LitemallMessage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    LitemallMessage selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallMessage record, @Param("example") LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") LitemallMessage record, @Param("example") LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallMessage record, @Param("example") LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(LitemallMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_message
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}