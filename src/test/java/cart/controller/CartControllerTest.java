package cart.controller;

import cart.cart.controller.CartController;
import cart.cart.entity.Cart;
import cart.cart.repository.CartRepository;
import cart.product.entity.Product;
import cart.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    ProductRepository productRepository;

    @Test
    @DisplayName("get 요청을 보냈을 때, 주어진 멤버로 장바구니에 담은 모든 상품을 조회할 수 있다.")
    void findAll() throws Exception {
        // given
        String email = "test@gmail.com";
        List<Cart> carts = List.of(
                new Cart(email, 1L),
                new Cart(email, 2L)
        );
        when(cartRepository.findAllByMemberEmail(email)).thenReturn(carts);
        Product testProduct1 = new Product(1L, "testProduct1", "product1.png", new BigDecimal(4000));
        when(productRepository.findById(1L)).thenReturn(testProduct1);
        Product testProduct2 = new Product(2L, "testProduct2", "product2.png", new BigDecimal(3000));
        when(productRepository.findById(2L)).thenReturn(testProduct2);

        // when
        MvcResult result = mockMvc.perform(get("/carts")
                        .header("authorization", httpBasic(email, "test1234!"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andDo(print())

                // then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<Product> foundProducts = Arrays.asList(objectMapper.readValue(content, Product[].class));
        Assertions.assertThat(foundProducts).containsExactly(testProduct1, testProduct2);
    }

    @Test
    @DisplayName("post 요청을 보냈을 때, 주어진 멤버의 base64 정보와 상품 Id로 장바구니에 상품을 담을 수 있다.")
    void saveCart() throws Exception {
        // given
        Cart cart = new Cart("test@gmail.com", 1L);
        when(cartRepository.save(cart)).thenReturn(cart);

        // when & then
        mockMvc.perform(post("/carts")
                        .header("authorization", httpBasic("test@gmail.com", "test1234!"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of("product_id", 1L)))
                ).andDo(print())
                .andExpect(status().isCreated());

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void deleteByEmailAndId() throws Exception {
        mockMvc.perform(delete("/carts")
                        .header("authorization", httpBasic("test@gmail.com", "test1234!"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of("product_id", 1L)))
                ).andDo(print())
                .andExpect(status().isOk());

        verify(cartRepository, times(1))
                .deleteByMemberEmailAndProductId("test@gmail.com", 1L);
    }

    private String httpBasic(String email, String password) {
        String format = String.format("%s:%s", email, password);
        return String.format("Basic %s", Base64.getEncoder().encodeToString(format.getBytes()));
    }
}