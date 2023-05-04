package cart.controller;

import cart.auth.AuthenticationService;
import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import cart.mapper.MemberResponseMapper;
import cart.mapper.ProductResponseMapper;
import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.DummyData.INITIAL_MEMBER_ONE;
import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(AuthenticationService.class)
@WebMvcTest(HomeController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    MemberService memberService;

    @Test
    void 상품_목록_페이지를_조회하면_상태코드_200을_반환하는지_확인한다() throws Exception {
        final String path = "/";
        final ProductResponse response = ProductResponseMapper.from(INITIAL_PRODUCT_ONE);
        final List<ProductResponse> responses = List.of(response);

        when(productService.findAll())
                .thenReturn(responses);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }

    @Test
    void 장바구니_목록_페이지를_조회하면_상태코드_200을_반환하는지_확인한다() throws Exception {
        final String path = "/cart";

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }

    @Test
    void 사용자_목록_페이지를_조회하면_상태코드_200을_반환하는지_확인한다() throws Exception {
        final String path = "/settings";
        final MemberResponse response = MemberResponseMapper.from(INITIAL_MEMBER_ONE);
        final List<MemberResponse> responses = List.of(response);

        when(memberService.findAll())
                .thenReturn(responses);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
