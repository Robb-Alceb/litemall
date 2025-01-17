package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallSubscribe;
import org.linlinjava.litemall.db.domain.LitemallSubscribeExample;

public interface LitemallSubscribeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    long countByExample(LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int insert(LitemallSubscribe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int insertSelective(LitemallSubscribe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    LitemallSubscribe selectOneByExample(LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    LitemallSubscribe selectOneByExampleSelective(@Param("example") LitemallSubscribeExample example, @Param("selective") LitemallSubscribe.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    List<LitemallSubscribe> selectByExampleSelective(@Param("example") LitemallSubscribeExample example, @Param("selective") LitemallSubscribe.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    List<LitemallSubscribe> selectByExample(LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    LitemallSubscribe selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallSubscribe.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    LitemallSubscribe selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    LitemallSubscribe selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallSubscribe record, @Param("example") LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallSubscribe record, @Param("example") LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallSubscribe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallSubscribe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallSubscribeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_subscribe
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}