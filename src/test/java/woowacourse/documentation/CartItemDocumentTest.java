package woowacourse.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductsRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class CartItemDocumentTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CartService cartService;

    @Test
    void addCartItem() throws Exception {
        CartResponse cartResponse = new CartResponse(1L, 10);
        when(cartService.addCartItem(anyLong(), any(CartRequest.class))).thenReturn(cartResponse);

        CartRequest cartRequest = new CartRequest(1L, 10);
        String token = jwtTokenProvider.createToken(String.valueOf(1L));

        this.mockMvc.perform(post("/customers/carts")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(cartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("cart/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void findCartItems() throws Exception {
        List<CartItemResponse> cartItemResponses = List.of(
                new CartItemResponse(1L,
                        "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001636]_20210225093600536.jpg",
                        "콜드 브루 몰트", 4800, 5),
                new CartItemResponse(2L,
                        "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319040.jpg",
                        "바닐라 크림 콜드 브루", 4500, 20)
        );
        when(cartService.findCartItems(anyLong())).thenReturn(new CartItemsResponse(cartItemResponses));

        String token = jwtTokenProvider.createToken(String.valueOf(anyLong()));
        this.mockMvc.perform(get("/customers/carts")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("cart/find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void updateQuantity() throws Exception {
        doNothing().when(cartService).updateCartItemQuantity(anyLong(), any(CartRequest.class));
        CartRequest cartRequest = new CartRequest(1L, 10);

        String token = jwtTokenProvider.createToken(String.valueOf(1L));
        this.mockMvc.perform(patch("/customers/carts")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(cartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("cart/update/quantity",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void deleteCartItems() throws Exception {
        doNothing().when(cartService).deleteCartItems(anyLong(), any(ProductsRequest.class));
        ProductsRequest productsRequest = new ProductsRequest(List.of(1L, 2L, 3L));

        String token = jwtTokenProvider.createToken(String.valueOf(1L));
        this.mockMvc.perform(delete("/customers/carts")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(productsRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("cart/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}