package cart.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.service.MemberService;
import cart.service.ProductService;
import cart.service.dto.member.MemberSearchResponse;
import cart.service.dto.product.ProductSearchResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ViewController.class)
class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("searchAllMembers() : 모든 사용자를 가져올 수 있다.")
    void test_searchAllMembers() throws Exception {
        // when
        List<MemberSearchResponse> memberSearchResponses = List.of();
        given(memberService.searchAllMembers())
                .willReturn(memberSearchResponses);

        // expected
        mockMvc.perform(get("/settings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andExpect(model().attributeExists("members"));
    }

    @Test
    @DisplayName("searchProduct() : 상품을 가져올 수 있다.")
    void test_searchProduct() throws Exception {
        // when
        List<ProductSearchResponse> productSearchResponses = List.of();
        given(productService.searchAllProducts())
                .willReturn(productSearchResponses);

        // expected
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    @DisplayName("showWelcomePage() : index 페이지를 보여준다.")
    void test_showWelcomePage() throws Exception {
        // when
        List<ProductSearchResponse> productSearchResponses = List.of();
        given(productService.searchAllProducts())
                .willReturn(productSearchResponses);

        // expected
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"));
    }
}
