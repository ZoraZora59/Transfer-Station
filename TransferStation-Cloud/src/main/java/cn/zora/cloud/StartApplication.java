package cn.zora.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud</h4>
 * <p>启动入口</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@SpringBootApplication(scanBasePackages = "cn.zora")
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class);
    }
}
