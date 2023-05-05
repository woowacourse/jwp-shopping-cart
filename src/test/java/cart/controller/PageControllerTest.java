package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
        final ProductResponse firstProductResponse = new ProductResponse(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 1_000_000_000);
        final ProductResponse secondProductResponse = new ProductResponse(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 1_000_000_000);

        given(productService.findAll()).willReturn(List.of(firstProductResponse, secondProductResponse));

        mockMvc.perform(get("/"))
                .andExpectAll(
                        status().isOk(),
                        view().name("index"),
                        model().attribute("products", equalTo(List.of(firstProductResponse, secondProductResponse)))
                )
                .andDo(print());
    }

    @Test
    void 관리자_페이지를_가져온다() throws Exception {
        final ProductResponse firstProductResponse = new ProductResponse(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 1_000_000_000);
        final ProductResponse secondProductResponse = new ProductResponse(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 1_000_000_000);

        given(productService.findAll()).willReturn(List.of(firstProductResponse, secondProductResponse));

        mockMvc.perform(get("/admin"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin"),
                        model().attribute("products", equalTo(List.of(firstProductResponse, secondProductResponse)))
                )
                .andDo(print());
    }

    @Test
    void 세팅_페이지를_가져온다() throws Exception {
        final MemberResponse firstMemberResponse = new MemberResponse(1L, "turtle@test.com", "pw1");
        final MemberResponse secondMemberResponse = new MemberResponse(2L, "hongo@test.com", "pw2");

        given(memberService.findAll()).willReturn(List.of(firstMemberResponse, secondMemberResponse));

        mockMvc.perform(get("/settings"))
                .andExpectAll(
                        status().isOk(),
                        view().name("settings"),
                        model().attribute("members", equalTo(List.of(firstMemberResponse, secondMemberResponse)))
                )
                .andDo(print());
    }
}
