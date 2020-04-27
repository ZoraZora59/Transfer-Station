package cn.zora.cloud.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud.model</h4>
 * <p>任务创建</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@Data
public class JobSetterDTO implements Serializable {
    private String title;
    private String description;
    private String downloadUrl;
    private String key;
}
