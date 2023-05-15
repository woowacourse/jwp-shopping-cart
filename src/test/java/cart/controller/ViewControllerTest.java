package cart.controller;

import cart.domain.product.Product;
import cart.domain.user.Member;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ViewControllerTest extends ControllerUnitTest {

    @Test
    void 상품을_조회한다() throws Exception {
        given(productDao.findAll())
                .willReturn(List.of(Product.create(1L, "상품테스트", 100, "url")));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void 어드민_상품_목록을_조회한다() throws Exception {
        given(productDao.findAll())
                .willReturn(List.of(Product.create(1L, "상품테스트", 100, "url")));

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    void 카트_상품_목록을_조회한다() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));

    }

    @Test
    void 회원_목록을_조회한다() throws Exception {
        given(memberDao.findAll())
                .willReturn(List.of(new Member(1L, "회원1", "비밀번호")));
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"));
    }
}
