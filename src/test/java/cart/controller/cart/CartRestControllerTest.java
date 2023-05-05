package cart.controller.cart;

import cart.service.cart.CartService;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(CartRestController.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartService cartService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품추가() throws Exception {
        BDDMockito.given(cartService.createCartItem(any(), any()))
                .willReturn(1L);
        String header = "Basic " + new String(Base64.getEncoder().encode("cyh6099@gmail.com:qwer1234".getBytes()));
        mockMvc.perform(MockMvcRequestBuilders.post("/carts/1")
                        .header("Authorization", header))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void 잘못된_인증_정보로_장바구니_추가_시_예외() throws Exception {
        String header = "Bearer " + new String(Base64.getEncoder().encode("cyh6099@gmail.com:qwer1234".getBytes()));
        String exceptionMessage = mockMvc.perform(MockMvcRequestBuilders.post("/carts/1")
                        .header("Authorization", header))
                .andExpect(status().isUnauthorized())
                .andReturn().getResolvedException().getMessage();

        Assertions.assertThat(exceptionMessage).isEqualTo("올바르지 않은 인증 방식입니다.");
    }

    @Test
    void 장바구니_상품을_삭제한다() {
        doNothing().when(cartService).deleteCartItem(any(), any());
        given().log().all()
                .auth().preemptive().basic("cyh6099@gmail.com", "qwer1234")
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void 잘못된_인증_정보로_장바구니_삭제_시_예외() {
        given().log().all()
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
