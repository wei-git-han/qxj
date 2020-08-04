package com.css.app.qxjgl.qxjbubao.dao;

import com.css.app.qxjgl.qxjbubao.entity.QxjFlowBubao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 补报表
 *
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2020-07-25 14:38:45
 */
@Mapper
public interface QxjFlowBubaoDao extends BaseDao<QxjFlowBubao> {

    @Select("select top 1 * from (select * from QXJ_FLOW_BUBAO where FILE_ID=#{0} and COMPLETE_FLAG='0' order By CREATED_TIME ASC)")
    QxjFlowBubao queryLastBuBaoUser(String id);

    List<QxjFlowBubao> queryAllBuBaoUser(String id);

    @Delete("delete from QXJ_FLOW_BUBAO where FILE_ID=#{0} and COMPLETE_FLAG='0' and RECEIVE_ID = #{1}")
    void deleteBubao(String id,String userId);

    @Update("update QXJ_FLOW_BUBAO set COMPLETE_FLAG = '1' where FILE_ID = #{0} and RECEIVE_ID = #{1}")
    void updateBubao(String id,String approvalId);

    @Select("select * from QXJ_FLOW_BUBAO where FILE_ID = #{0} and SENDER_ID = #{1}")
    List<QxjFlowBubao> queryIsexist(String id,String userId);

    @Select("select * from QXJ_FLOW_BUBAO where FILE_ID = #{0} and RECEIVE_ID = #{1}")
    List<QxjFlowBubao> queryUserId(String id,String userId);

    void save(QxjFlowBubao qxjFlowBubao);

    @Select("select * from QXJ_FLOW_BUBAO where FILE_ID = #{0}")
    List<QxjFlowBubao> queryInfo(String id);

}
