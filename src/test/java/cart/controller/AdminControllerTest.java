package cart.controller;

import cart.dao.ItemDao;
import cart.entity.CreateItem;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE item RESTART IDENTITY;");

        itemDao.save(new CreateItem("치킨", "a", 10000));
        itemDao.save(new CreateItem("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void addItemTest() {
        //given
        CreateItem createItem = new CreateItem("국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createItem)
                .when().post("/admin/items/add")
                .then().log().all()
                .statusCode(HttpStatus.MOVED_TEMPORARILY.value());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() {
        //given
        CreateItem createItem = new CreateItem("국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createItem)
                .when().post("/admin/items/edit/1")
                .then().log().all()
                .statusCode(HttpStatus.MOVED_TEMPORARILY.value());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest() {
        //then
        RestAssured.given().log().all()
                .when().post("/admin/items/delete/1")
                .then().log().all()
                .statusCode(HttpStatus.MOVED_TEMPORARILY.value());
    }
}
