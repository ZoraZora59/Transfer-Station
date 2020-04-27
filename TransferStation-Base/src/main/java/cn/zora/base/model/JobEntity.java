package cn.zora.base.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud.model</h4>
 * <p>任务实体映射</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@Data
@Accessors(chain = true)
public class JobEntity implements Serializable {
    /**
     * 数据库id
     */
    private Integer id;
    /**
     * 任务名
     */
    private String title;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 下载地址
     */
    private String downloadUrl;
    /**
     * 任务创建时间
     */
    private Date createTime;
    /**
     * 任务下发时间
     */
    private Date pushTime;
}
