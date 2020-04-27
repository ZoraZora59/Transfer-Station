package cn.zora.consumer.model;

import cn.zora.base.model.JobEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.consumer.model</h4>
 * <p>消费者Response</p>
 *
 * @author zora
 * @since 2020.04.27
 */
@Data
public class ConsumerResponse implements Serializable{
        @ApiModelProperty(value = "code")
        private Integer code;
        @ApiModelProperty(value = "消息描述")
        private String message;
        @ApiModelProperty(value = "返回对象")
        private JobEntity data;
}
