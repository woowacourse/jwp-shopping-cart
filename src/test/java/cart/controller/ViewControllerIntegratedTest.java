package cart.controller;

import cart.config.DBTransactionExecutor;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.Matchers.containsString;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewControllerIntegratedTest {
    @LocalServerPort
    private int port;
    
    @RegisterExtension
    private DBTransactionExecutor dbTransactionExecutor;
    
    @Autowired
    public ViewControllerIntegratedTest(final JdbcTemplate jdbcTemplate) {
        dbTransactionExecutor = new DBTransactionExecutor(jdbcTemplate);
    }
    
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_index_페이지로_이동한다() {
        // expect
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>상품목록</title>"))
                .header("Content-Type", "text/html;charset=UTF-8");
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_관리자_페이지로_이동한다() {
        // expect
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"))
                .header("Content-Type", "text/html;charset=UTF-8");
    }
    
    @Test
    void 모든_회원_정보를_가져온_후_설정_페이지로_이동한다() {
        // expect
        RestAssured.given().log().all()
                .when().get("/settings")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>설정</title>"))
                .header("Content-Type", "text/html;charset=UTF-8");
    }
    
    @Test
    void 장바구니_페이지로_이동한다() {
        // expect
        RestAssured.given().log().all()
                .when().get("/cart")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>장바구니</title>"))
                .header("Content-Type", "text/html;charset=UTF-8");
    }
}
