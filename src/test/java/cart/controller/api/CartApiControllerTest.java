package cart.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.CartProductResponseDto;
import cart.entity.CartEntity;
import cart.service.CartService;
import cart.service.ProductService;
import io.restassured.RestAssured;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class CartApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("고객의 Basic 인증 정보를 통해 해당 고객이 담은 상품을 조회하여 반환한다.")
    @Test
    void showItems() {
        //given
        final Long savedProductId = productService.register(new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );
        cartService.save(new CartEntity(1L, savedProductId));

        //when
        //then
        final CartProductResponseDto[] cartProductResponseDtos = RestAssured.given()
            .auth().preemptive().basic("split@wooteco.com", "dazzle")
            .when().get("/cart/items")
            .then()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .statusCode(HttpStatus.OK.value()).extract().as(CartProductResponseDto[].class);

        assertAll(
            () -> assertThat(cartProductResponseDtos).hasSize(1),
            () -> assertThat(cartProductResponseDtos[0].getName()).isEqualTo("name"),
            () -> assertThat(cartProductResponseDtos[0].getImageUrl()).isEqualTo("imageUrl"),
            () -> assertThat(cartProductResponseDtos[0].getPrice()).isEqualTo(1000),
            () -> assertThat(cartProductResponseDtos[0].getDescription()).isEqualTo("description")
        );
    }

    @DisplayName("요청의 Basic 인증 정보에 해당하는 고객이 존재하지 않을 때 오류 메시지를 담아 400 상태코드를 담아 응답한다.")
    @Test
    void showItemsInvalidAuthorization() {
        //given
        final Long savedProductId = productService.register(new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );
        cartService.save(new CartEntity(1L, savedProductId));

        //when
        //then
        RestAssured.given()
            .auth().preemptive().basic("split@wooteco.com", "a")
            .when().get("/cart/items")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", Matchers.containsString("해당 인증에 부합하는 고객이 없습니다."));
    }

    @DisplayName("고객의 Basic 인증 정보와 상품의 Id를 통해 장바구니에 상품을 추가한다.")
    @Test
    void addItem() {
        //given
        final Long savedProductId = productService.register(new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );

        //when
        //then
        RestAssured.given()
            .auth().preemptive().basic("split@wooteco.com", "dazzle")
            .when().post("/cart/" + savedProductId)
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("고객의 Basic 인증 정보와 상품의 Id를 통해 장바구니에 상품을 제거한다.")
    @Test
    void deleteItem() {
        //given
        final Long savedProductId = productService.register(new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );
        cartService.save(new CartEntity(1L, savedProductId));

        //when
        //then
        RestAssured.given()
            .auth().preemptive().basic("split@wooteco.com", "dazzle")
            .when().delete("/cart/" + savedProductId)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
