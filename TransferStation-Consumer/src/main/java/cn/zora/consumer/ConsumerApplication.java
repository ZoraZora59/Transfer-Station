package cn.zora.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.consumer</h4>
 * <p>任务消费者</p>
 *
 * @author zora
 * @since 2020.04.27
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = "cn.zora",exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }
}
