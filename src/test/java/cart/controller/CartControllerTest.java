package cart.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import cart.auth.BasicAuthorizationExtractor;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.Product;

@Import(BasicAuthorizationExtractor.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    private ProductDao productDao;

    @MockBean
    private CartDao cartDao;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasicAuthorizationExtractor basicAuthorizationExtractor;

    @DisplayName("'/'로 GET 요청을 했을 때 index template을 반환한다.")
    @Test
    void findAllProducts() throws Exception {
        // given
        Product product = new Product(1, "치킨", "image.url", 10000);
        given(productDao.findAll()).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("치킨")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is("image.url")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(10000)))))
                .andExpect(view().name("index"));
    }

//    @DisplayName("ff")
//    @Test
//    void postTest() throws Exception {
//        // given
//        given(basicAuthorizationExtractor.extract(any(HttpServletRequest.class)))
//                .willReturn(new AuthInfo("jeomxon@gmail.com", "password1"));
//
//        // when
//
//        // then
//        mockMvc.perform(post("/cart/products/{productId}", 1)
//                        .header("Authorization", "Basic amVvbXhvbkBnbWFpbC5jb206cGFzc3dvcmQx=="))
//                .andExpect(status().isCreated());
//    }
}
