package cn.zora.cloud.repository;

import cn.zora.base.model.JobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud.repository</h4>
 * <p>任务管理mapper</p>
 *
 * @author zora
 * @since 2020.04.26
 */
@Repository
@Mapper
public interface MissionMapper {
    /**
     * 插入一条任务
     *
     * @param entity 任务对象
     */
    void insertEntity(JobEntity entity);

    /**
     * 根据条件拉取任务
     *
     * @param pushed 是否已经下发
     * @return 任务对象列表
     */
    List<JobEntity> selectAll(@Param("pushed") Boolean pushed);

    /**
     * 根据条件获取任务数
     *
     * @param pushed 是否已经下发
     * @return 数量
     */
    int count(@Param("pushed") Boolean pushed);

    /**
     * 拉取一条未下发的任务
     * @param latest 是否拿最新的
     * @return 任务记录
     */
    JobEntity selectNotPushedOne(@Param("latest") Boolean latest);

    /**
     * 更新任务的下发时间
     * @param id id
     * @return rows
     */
    int updatePushTime(@Param("id") Integer id);

    /**
     * 重置一个下发的任务
     * @param id id
     * @return rows
     */
    int updatePushTimeReset(@Param("id") Integer id);

    /**
     * 删除指定任务
     * @param id id
     * @return rows
     */
    int deleteById(@Param("id") Integer id);
}
