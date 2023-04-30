package cart.controller;

import cart.dao.ItemDao;

import cart.dto.item.ItemSaveRequest;
import cart.dto.item.ItemUpdateRequest;
import cart.entity.ItemEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;

@Sql("classpath:test_init.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemDao.save(new ItemEntity("치킨", "a", 10000));
        itemDao.save(new ItemEntity("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void addItemTest() {
        //given
        ItemSaveRequest itemSaveRequest = new ItemSaveRequest("국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemSaveRequest)
                .when().post("http://localhost:" + port + "/admin/item")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("data.id", equalTo(3));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() {
        //given
        ItemUpdateRequest itemRequest = new ItemUpdateRequest(1L, "국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().put("http://localhost:" + port + "/admin/item")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest() {
        //then
        RestAssured.given().log().all()
                .when().delete("http://localhost:" + port + "/admin/admin/1")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
