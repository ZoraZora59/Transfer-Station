package cn.zora.consumer.model;

import cn.zora.base.model.JobEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud.model</h4>
 * <p>展示任务记录</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@Data
public class ConsumerJobListVO implements Serializable {
    private List<JobEntity> jobEntityList;
    private Integer total;
    private Integer pushed;
    private Integer notPushed;
}
