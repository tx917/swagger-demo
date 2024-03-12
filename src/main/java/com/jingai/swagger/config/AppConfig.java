package com.jingai.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).useDefaultResponseMessages(false)
                .apiInfo(apiInfo()).globalRequestParameters(globalRequestParameters())
                .select()
                // 此处的apis填写的包名必现是接口所在的控制类的包名
                .apis(RequestHandlerSelectors.basePackage("com.jingai.swagger.controller"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * http://localhost:8080/swagger-ui/index.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("XXX服务接口").description("微服务接口文档")
                .version("1.0").build();
    }

    /**
     * 添加公用请求参数。如时间戳、签名等
     * @return
     */
    private List<RequestParameter> globalRequestParameters() {
        List<RequestParameter> list = new ArrayList<>();
        /*RequestParameterBuilder builder = new RequestParameterBuilder();
        list.add(builder.name("timestamp").description("时间戳").required(true).build());*/
        return list;
    }
}
