package cart.intergration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
import cart.repository.MemberDao;
import cart.repository.ProductDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class CartIntegrationTest {
    private static final String CART_PATH = "/api/cart";
    private static final String MEMBER_1 = "Basic Z2xlbkBuYXZlci5jb206MTIzNDU2"; // glen@naver.com:123456
    private static final String MEMBER_2 = "Basic ZmlkZGljaEBuYXZlci5jb206MTIzNDU2"; // fiddich@naver.com:123456
    private static final String UNSIGNED_MEMBER = "Basic Z2xlbmZpZGRpY2hAbmF2ZXIuY29tOjEyMzQ1Ng==";

    private Long productId;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberDao memberDao;

    @Autowired
    ProductDao productDao;

    @BeforeEach
    void setUp() {
        memberDao.save(new Member(new Email("glen@naver.com"), new Password("123456")));
        memberDao.save(new Member(new Email("fiddich@naver.com"), new Password("123456")));
        productId = productDao.save(
                        new Product(new ProductName("글렌피딕"), new Price(1000), ImageUrl.from("https://image.com/image.png")))
                .getId();
    }

    @Test
    @DisplayName("장바구니에 상품을 추가하고, 장바구니의 조회가 정상적으로 돼야 한다.")
    void saveCartAndFindCarts() throws Exception {
        // given
        mockMvc.perform(post(CART_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("장바구니에 상품이 담겼습니다."));

        // expect
        mockMvc.perform(get(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("1개의 상품이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].product.name").value("글렌피딕"))
                .andExpect(jsonPath("$.result[0].product.price").value(1000))
                .andExpect(jsonPath("$.result[0].product.imageUrl").value("https://image.com/image.png"));
    }

    @Test
    @DisplayName("장바구니에 상품을 추가하고, 장바구니에 담긴 상품을 삭제할 수 있어야 한다.")
    void saveCartAndDeleteCart() throws Exception {
        // given
        mockMvc.perform(post(CART_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk())
                .andReturn();
        int cartId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result[0].cartId");

        // when
        mockMvc.perform(delete(CART_PATH + "/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk());

        // then
        mockMvc.perform(get(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("0개의 상품이 조회되었습니다."));
    }

    @Test
    @DisplayName("사용자가 장바구니에 상품을 추가하면 다른 사용자의 장바구니에는 보이지 않아야 한다.")
    void saveCartAndShouldNotShownToOtherMember() throws Exception {
        // given
        mockMvc.perform(post(CART_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk());

        // expect
        mockMvc.perform(get(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("1개의 상품이 조회되었습니다."));

        mockMvc.perform(get(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("0개의 상품이 조회되었습니다."));
    }

    @Test
    @DisplayName("다른 사용자의 장바구니에 담긴 상품을 지울 수 없어야 한다.")
    void deleteCartShouldNotOtherMember() throws Exception {
        // given
        mockMvc.perform(post(CART_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_1))
                .andExpect(status().isOk())
                .andReturn();
        int cartId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result[0].cartId");

        // expect
        mockMvc.perform(delete(CART_PATH + "/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, MEMBER_2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("존재하지 않는 장바구니의 ID 입니다."));
    }
    
    @Test
    @DisplayName("회원가입된 사용자가 아니면 HTTP 401 상태 코드가 반환되어야 한다.")
    void saveCart_notSignupMember() throws Exception {
        // expect
        mockMvc.perform(post(CART_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, UNSIGNED_MEMBER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("비밀번호가 틀리면 HTTP 401 상태 코드가 반환되어야 한다.")
    void saveCart_wrongPassword() throws Exception {
        // expect
        String basicAuth = Base64Utils.encodeToString("glen@naver.com:wrongpassword".getBytes());

        mockMvc.perform(post(CART_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, "Basic " + basicAuth))
                .andExpect(status().isUnauthorized());
    }
}
