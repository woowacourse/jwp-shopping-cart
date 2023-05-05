package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.fixture.MemberFixture.FIRST_MEMBER;
import static cart.fixture.ProductFixture.FIRST_PRODUCT;
import static cart.fixture.ProductFixture.SECOND_PRODUCT;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(PageController.class)
public class PageControllerTest {
    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 메인_페이지를_가져온다() throws Exception {
        given(productService.findAll())
                .willReturn(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE));

        mockMvc.perform(get("/"))
                .andExpectAll(
                        status().isOk(),
                        view().name("index"),
                        model().attribute("products", equalTo(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE)))
                )
                .andDo(print());
    }

    @Test
    void 관리자_페이지를_가져온다() throws Exception {
        given(productService.findAll()).willReturn(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE));

        mockMvc.perform(get("/admin"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin"),
                        model().attribute("products", equalTo(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE)))
                )
                .andDo(print());
    }

    @Test
    void 세팅_페이지를_가져온다() throws Exception {
        given(memberService.findAll()).willReturn(List.of(FIRST_MEMBER.RESPONSE));

        mockMvc.perform(get("/settings"))
                .andExpectAll(
                        status().isOk(),
                        view().name("settings"),
                        model().attribute("members", equalTo(List.of(FIRST_MEMBER.RESPONSE)))
                )
                .andDo(print());
    }

    @Test
    void 장바구니_페이지를_가져온다() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpectAll(
                        status().isOk(),
                        view().name("cart")
                )
                .andDo(print());
    }
}
