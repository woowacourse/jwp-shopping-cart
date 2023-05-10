package cart.web.controller.cart;

import cart.domain.TestFixture;
import cart.domain.cart.CartService;
import cart.domain.cart.dto.CartDto;
import cart.domain.user.UserService;
import cart.domain.user.dto.UserDto;
import cart.web.auth.AuthService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static cart.web.controller.cart.CartRestControllerTest.Data.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartRestControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        when(userService.login(any()))
                .thenReturn(new UserDto(TestFixture.ZUNY));
    }

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @DisplayName("상품을 장바구니에 추가할 수 있다.")
    @Test
    void addProduct() {
        when(cartService.addProduct(any()))
                .thenReturn(1L);

        when(userService.login(any()))
                .thenReturn(new UserDto(TestFixture.ZUNY));

        doNothing().when(authService).authenticate(any());

        given().log().all()
                .auth().preemptive().basic(email, password)
                .when().post("/carts/{productId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", is("/carts/" + 1L));
    }

    @DisplayName("User의 장바구니의 목록을 가져올 수 있다.")
    @Test
    void getProductsOfUser() {
        when(userService.login(any()))
                .thenReturn(new UserDto(TestFixture.ZUNY));

        when(cartService.getCartOfUser(TestFixture.ZUNY.getId()))
                .thenReturn(List.of(cartDto));

        doNothing().when(authService).authenticate(any());

        given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItem(1))
                .body("name", hasItem("Chicken"))
                .body("price", hasItem(23000));
    }

    @DisplayName("User의 장바구니에서 상품을 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        doNothing().when(cartService).deleteProduct(any());

        doNothing().when(authService).authenticate(any());

        given().log().all()
                .auth().preemptive().basic(email, password)
                .when().delete("/carts/{cartId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    static class Data {
        static CartDto cartDto =
                new CartDto(1L, "Chicken", 23_000, "FOOD", "www.kyochon.com");
        static String email = TestFixture.ZUNY.getEmail();
        static String password = TestFixture.ZUNY.getPassword();

    }
}
