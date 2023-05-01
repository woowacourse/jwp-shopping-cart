package cart.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class IntegrationTestConfig {
    protected static final String METHOD_NAME = "{method-name}";

    @LocalServerPort
    protected int port;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected RequestSpecification spec;

    @BeforeEach
    protected void setUp(RestDocumentationContextProvider restDocs) {
        RestAssured.port = this.port;
        this.spec = new RequestSpecBuilder()
                .addFilter(document("{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .addFilter(documentationConfiguration(restDocs))
                .build();
    }

    @AfterEach
    protected void clear() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("TRUNCATE TABLE carts RESTART IDENTITY");
        jdbcTemplate.execute("TRUNCATE TABLE members RESTART IDENTITY");
        jdbcTemplate.execute("TRUNCATE TABLE products RESTART IDENTITY");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
