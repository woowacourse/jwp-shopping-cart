package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dao.CartProductJdbcDao;
import cart.dao.ProductJdbcDao;
import cart.domain.cart.CartProduct;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.CartProductSaveRequest;
import cart.fixture.MemberFixture;
import cart.fixture.ProductFixture.BLACKCAT;
import cart.fixture.ProductFixture.HERB;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(ReplaceUnderscores.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductJdbcDao productJdbcDao;

    @Autowired
    private CartProductJdbcDao cartProductDao;

    @Test
    void 장바구니에_상품을_추가한다() throws Exception {
        // given
        final Long productId = productJdbcDao.saveAndGetId(HERB.PRODUCT).get();
        final Member member = MemberFixture.BLACKCAT.MEMBER;
        final CartProductSaveRequest cartProductSaveRequest = new CartProductSaveRequest(productId);
        final String request = objectMapper.writeValueAsString(cartProductSaveRequest);

        // when
        mockMvc.perform(post("/carts")
                        .header("Authorization", makeAuthorizationHeader(member))
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        final List<Product> result = cartProductDao.findAllProductByMember(member);
        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getImage().imageUrl()).isEqualTo(HERB.PRODUCT.getImage().imageUrl()),
                () -> assertThat(result.get(0).getName().name()).isEqualTo(HERB.PRODUCT.getName().name()),
                () -> assertThat(result.get(0).getPrice().price()).isEqualTo(HERB.PRODUCT.getPrice().price())
        );
    }


    private String makeAuthorizationHeader(final Member member) {
        final String email = member.getEmail().email();
        final String password = member.getPassword().password();

        return "Basic " + new String(Base64.getUrlEncoder().encode((email + ":" + password).getBytes()));
    }

    @Test
    void 특정_유저의_장바구니에_담겨있는_모든_상품을_조회한다() throws Exception {
        // given
        final Long productId1 = productJdbcDao.saveAndGetId(HERB.PRODUCT).get();
        final Long productId2 = productJdbcDao.saveAndGetId(BLACKCAT.PRODUCT).get();
        final Member member = MemberFixture.BLACKCAT.MEMBER;

        cartProductDao.saveAndGetId(new CartProduct(member.getId(), productId1));
        cartProductDao.saveAndGetId(new CartProduct(member.getId(), productId2));

        // expect
        mockMvc.perform(get("/carts")
                        .header("Authorization", makeAuthorizationHeader(member))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name", is(HERB.PRODUCT.getName().name())))
                .andExpect(jsonPath("$.products[0].image", is(HERB.PRODUCT.getImage().imageUrl())))
                .andExpect(jsonPath("$.products[0].price", is((int) HERB.PRODUCT.getPrice().price())))
                .andExpect(jsonPath("$.products[1].name", is(BLACKCAT.PRODUCT.getName().name())))
                .andExpect(jsonPath("$.products[1].image", is(BLACKCAT.PRODUCT.getImage().imageUrl())))
                .andExpect(jsonPath("$.products[1].price", is((int) BLACKCAT.PRODUCT.getPrice().price())));
    }

    @Test
    void 특정_유저의_장바구니에_담겨있는_상품을_삭제한다() throws Exception {
        // given
        final Long productId = productJdbcDao.saveAndGetId(HERB.PRODUCT).get();
        final Member member = MemberFixture.BLACKCAT.MEMBER;

        cartProductDao.saveAndGetId(new CartProduct(member.getId(), productId));

        // expect
        mockMvc.perform(delete("/carts/" + productId)
                        .header("Authorization", makeAuthorizationHeader(member))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(cartProductDao.findAllProductByMember(member)).isEmpty();
    }
}
