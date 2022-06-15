package woowacourse.support.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:truncate.sql", "classpath:data.sql"})
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void initPort() {
        RestAssured.port = port;
    }
}
