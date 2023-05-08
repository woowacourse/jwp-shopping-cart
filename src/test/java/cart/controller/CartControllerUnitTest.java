package cart.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.CartItemResponseDto;
import cart.entity.CartItem;
import cart.entity.Product;
import cart.service.CartService;
import cart.service.MemberService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
public class CartControllerUnitTest {

    private static final String EMAIL = "email1@email.com";
    private static final String AUTHORIZATION_HEADER = "Basic ZW1haWwxQGVtYWlsLmNvbTptZW1iZXIx";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        given(memberService.isExistMember(EMAIL))
                .willReturn(true);
    }

    @Test
    @DisplayName("장바구니에 담긴 상품들을 모두 조회한다.")
    void cartItemList() throws Exception {
        given(cartService.findAll(EMAIL))
                .willReturn(List.of(new CartItemResponseDto(new CartItem.Builder()
                                .id(1)
                                .product(buildProduct(1, "치킨", 10000, "이미지"))
                                .build())
                        )
                );

        mockMvc.perform(get("/carts/products")
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("치킨")))
                .andExpect(jsonPath("$[0].price", is(10000)))
                .andExpect(jsonPath("$[0].imageUrl", is("이미지")));

    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem() throws Exception {
        given(cartService.add(1, EMAIL))
                .willReturn(1);

        mockMvc.perform(post("/carts/products/1")
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 삭제한다.")
    void removeCartItem() throws Exception {
        mockMvc.perform(delete("/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER))
                .andExpect(status().isOk());

    }

    private Product buildProduct(int id, String name, int price, String imageUrl) {
        return new Product.Builder()
                .id(id)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }

}
