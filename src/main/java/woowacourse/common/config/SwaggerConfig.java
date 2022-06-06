package woowacourse.common.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String API_KEY_ID = "JWT";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(List.of(jwtSecurityContext()))
                .securitySchemes(List.of(bearerAuthSecurityScheme()));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("장바구니 미션 1단계")
                .version("0.0.1")
                .description("정과 조시의 유쾌한 API 명세")
                .build();
    }

    private SecurityContext jwtSecurityContext() {
        return springfox.documentation
                .spi.service.contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext -> true)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference(API_KEY_ID, authorizationScopes));
    }

    private HttpAuthenticationScheme bearerAuthSecurityScheme() {
        return HttpAuthenticationScheme
                .JWT_BEARER_BUILDER
                .name(API_KEY_ID).build();
    }
}
