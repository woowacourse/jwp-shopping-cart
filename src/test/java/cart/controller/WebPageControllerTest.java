package cart.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cart.dao.UserDao;
import cart.dao.dto.UserDto;
import cart.domain.Product;
import cart.repository.ProductRepository;

@WebMvcTest(WebPageController.class)
class WebPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDao userDao;
    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        given(productRepository.getAll())
                .willReturn(List.of(
                        new Product(1, "밀키스", "image1.png", 2000L),
                        new Product(2, "버터링", "image2.png", 2300L))
                );
        given(userDao.selectAll())
                .willReturn(List.of(
                        new UserDto(1, "beaver@wooteco.com", "weakpassword"),
                        new UserDto(2, "0chil@wooteco.com", "securepassword")
                ));
    }

    @DisplayName("시작 페이지를 상품을 담아 응답한다")
    @Test
    void responseStartPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("밀키스")))
                .andExpect(content().string(containsString("버터링")));
    }

    @DisplayName("관리자 페이지를 상품을 담아 응답한다")
    @Test
    void responseAdminPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("밀키스")))
                .andExpect(content().string(containsString("버터링")));
    }

    @DisplayName("설정 페이지를 유저를 담아 응답한다")
    @Test
    void responseSettingsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/settings"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("beaver@wooteco.com")))
                .andExpect(content().string(containsString("weakpassword")))
                .andExpect(content().string(containsString("0chil@wooteco.com")))
                .andExpect(content().string(containsString("securepassword")));
    }
}
