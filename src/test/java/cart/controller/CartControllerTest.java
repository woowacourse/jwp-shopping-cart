package cart.controller;

import cart.controller.dto.AddCartRequest;
import cart.domain.cart.Cart;
import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.domain.user.Member;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest extends ControllerUnitTest {

    private static final String AUTH = "Authorization";

    @Test
    void 카트의_상품을_조회한다() throws Exception {
        final Cart cart = new Cart(List.of(
                new CartItem(1L, Product.createWithoutId("상품1", 1000, "url")),
                new CartItem(2L, Product.createWithoutId("상품2", 2000, "url2"))
        ));
        given(cartDao.findByUserId(anyLong()))
                .willReturn(cart);

        given(memberDao.findByEmail(anyString()))
                .willReturn(new Member(1L, "email", "123456"));

        mockMvc.perform(get("/cart/products").header(AUTH, CODE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(2)))
                .andExpect(jsonPath("$.[1].productName", is("상품2")));
    }

    @Test
    void 카트에_상품을_추가한다() throws Exception {
        given(cartDao.addProduct(anyLong(), anyLong()))
                .willReturn(1L);

        given(memberDao.findByEmail(anyString()))
                .willReturn(new Member(1L, "email", "123456"));

        final AddCartRequest request = new AddCartRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/cart/products")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .header(AUTH, CODE)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

}
