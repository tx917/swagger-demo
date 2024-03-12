package com.jingai.swagger.plugin;

import com.jingai.swagger.annotation.ApiRespJson;
import com.jingai.swagger.util.JsonAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

/**
 * 解析ApiRespJson中的json字符串，添加到@ApiResponse中的message中。可以不设置@ApiResponse
 */
@Component
@Slf4j
public class ApiResponseJsonBuilderPlugin implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        final Optional<ApiRespJson> apiRespJson = context.findAnnotation(ApiRespJson.class);
        if(apiRespJson.isPresent()) {
            final ApiRespJson respJson = apiRespJson.get();
            final OperationBuilder operationBuilder = context.operationBuilder();
            operationBuilder.responses(plugResponseMessage(operationBuilder, respJson));
        }
    }

    /**
     * responseMessage增强
     * @param operationBuilder
     * @param respJson
     */
    private Set<Response> plugResponseMessage(OperationBuilder operationBuilder, ApiRespJson respJson) {
        final SortedSet<Response> responses = operationBuilder.build().getResponses();
        Set<Response> set = new HashSet<Response>(1);
        boolean addJson = false;
        for(Response res : responses) {
            if("200".equals(res.getCode())) {
                final Response resp = new ResponseBuilder().code(res.getCode())
                        .description(convertToHtml(res.getDescription(), respJson.jsonStr(), respJson.desc())).build();
                set.add(resp);
                addJson = true;
            }
        }
        if(!addJson) {
            final Response resp = new ResponseBuilder().code("200")
                    .description(convertToHtml("成功", respJson.jsonStr(), respJson.desc())).build();
            set.add(resp);
        }
        return set;
    }

    /**
     * 转换成html字符串
     * @return
     */
    private static String convertToHtml(String message, String json, String desc) {

        json = addSuccessInfo(json);

        StringBuilder sb = new StringBuilder();
        sb.append("<p><strong>").append(message).append("</strong></p>");
        sb.append("<pre>");
        if(StringUtils.hasText(desc)) {
            sb.append("<p><strong>说明：</strong>").append(desc).append("</p>");
        }
        sb.append("<code>");
        sb.append(JsonAnalysis.convert2Html(json));

        sb.append("</code></pre>");

        return sb.toString();
    }

    /**
     * 添加200成功信息
     * @param json
     * @return
     */
    private static String addSuccessInfo(String json) {
        json = json.replace(" ", "");
        final StringBuilder temp = new StringBuilder();
        temp.append("{code:200,msg:成功,data:");
        if(json.startsWith("{data:")) {
            json = json.replace("{data:", temp.toString());
        } else {
            json = temp.toString() + json + "}";
        }
        return json;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
