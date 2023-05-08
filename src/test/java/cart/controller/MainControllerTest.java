package cart.controller;

import static cart.fixture.SqlFixture.MEMBER_INSERT_SQL_NO_ID;
import static cart.fixture.SqlFixture.PRODUCT_INSERT_SQL_NO_ID;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerTest {

    private static final String ADMIN_PAGE_PRODUCT_LIST_HTML_TAG = "#product-list tr";
    private static final String INDEX_PAGE_PRODUCT_LIST_HTML_TAG = "ul.product-grid li.product";
    private static final String SETTINGS_PAGE_MEMBER_LIST_HTML_TAG = "div.cart-item";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from product");
        jdbcTemplate.update("delete from member");
        jdbcTemplate.update("delete from cart_product");
    }

    @DisplayName("admin 페이지로 접속시 모든 상품의 정보를 출력한다.")
    @Test
    void admin_page_print_all_products() {
        //given
        int countOfProduct = 3;
        createProductsByCount(countOfProduct);
        //when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/admin");
        //then
        Document doc = Jsoup.parse(response.getBody().asString());
        int actual = doc.select(ADMIN_PAGE_PRODUCT_LIST_HTML_TAG).size();

        assertThat(actual).isEqualTo(countOfProduct);
    }

    @DisplayName("index 페이지로 접속시 모든 상품의 정보를 출력한다.")
    @Test
    void index_page_print_all_products() {
        //given
        int countOfProduct = 3;
        createProductsByCount(countOfProduct);
        //when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/");
        //then
        Document doc = Jsoup.parse(response.getBody().asString());
        int actual = doc.select(INDEX_PAGE_PRODUCT_LIST_HTML_TAG).size();

        assertThat(actual).isEqualTo(countOfProduct);
    }

    @DisplayName("settings 페이지로 접속시 모든 회원의 정보를 출력한다.")
    @Test
    void settings_page_print_all_members() {
        //given
        jdbcTemplate.update(MEMBER_INSERT_SQL_NO_ID, "email@naver.com", "password");
        jdbcTemplate.update(MEMBER_INSERT_SQL_NO_ID, "email@kakao.com", "password");
        //when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/settings");
        //then
        Document doc = Jsoup.parse(response.getBody().asString());
        int actual = doc.select(SETTINGS_PAGE_MEMBER_LIST_HTML_TAG).size();
        assertThat(actual).isEqualTo(2);
    }

    private void createProductsByCount(int count) {
        for (int i = 0; i < count; i++) {
            jdbcTemplate.update(PRODUCT_INSERT_SQL_NO_ID, "name", 10000, "url");
        }
    }
}
