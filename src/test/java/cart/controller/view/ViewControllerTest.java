package cart.controller.view;

import cart.controller.ViewController;
import cart.dto.member.MembersResponseDto;
import cart.dto.product.ProductsResponseDto;
import cart.repository.member.MemberRepository;
import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.factory.member.MemberFactory.createMember;
import static cart.factory.product.ProductFactory.createProduct;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewController.class)
class ViewControllerTest {

    @MockBean
    ProductService productService;

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberRepository memberRepository;

    @Test
    @DisplayName("Home을 반환한다.")
    void returns_home_view() throws Exception {
        // given
        ProductsResponseDto expected = ProductsResponseDto.from(List.of(createProduct()));
        given(productService.findAll()).willReturn(expected);

        // when & then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @DisplayName("Admin을 반환한다.")
    void returns_admin_view() throws Exception {
        // given
        ProductsResponseDto expected = ProductsResponseDto.from(List.of(createProduct()));
        given(productService.findAll()).willReturn(expected);

        // when & then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @DisplayName("Settings를 반환한다.")
    void returns_settings_view() throws Exception {
        // given
        MembersResponseDto expected = MembersResponseDto.from(List.of(createMember()));
        given(memberService.findAll()).willReturn(expected);

        // when & then
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @DisplayName("Cart를 반환한다.")
    void returns_cart_view() throws Exception {
        // when & then
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }
}
