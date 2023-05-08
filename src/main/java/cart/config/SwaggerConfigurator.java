package cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigurator {

    private static final String PROJECT_NAME = "cart";
    private static final String CONTROLLER_PACKAGE = "cart.controller";
    private static final String TITLE = "장바구니";
    private static final String DESCRIPTION = "도기 - 장바구니 미션";
    private static final String VERSION = "1.0";
    private static final String TERMS_OF_SERVICE_URL = "http://localhost:8080/";
    private static final String CONTACT_NAME = "도기";
    private static final String LICENSE = "";
    private static final String LICENSE_URL = "";

    @Bean
    public Docket totalApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(PROJECT_NAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(TITLE,
                DESCRIPTION,
                VERSION,
                TERMS_OF_SERVICE_URL,
                CONTACT_NAME,
                LICENSE,
                LICENSE_URL
        );
    }
}
