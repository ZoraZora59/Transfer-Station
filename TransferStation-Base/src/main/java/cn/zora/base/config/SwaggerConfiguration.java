package cn.zora.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.base.config</h4>
 * <p>Swagger配置</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@EnableSwagger2
//    @ConditionalOnProperty(name = "swagger.enable",havingValue = "true")
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ZoraTeam")
                .apiInfo(new ApiInfoBuilder().title("API接口文档").description("Restful接口").build())
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.zora"))
                .paths(PathSelectors.any())
                .build();
    }

}
