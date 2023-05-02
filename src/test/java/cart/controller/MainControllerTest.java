package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.member.service.MemberService;
import cart.domain.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("메인 페이지 반환을 테스트한다.")
    public void testMain() throws Exception {
        //given
        when(productService.findAll())
            .thenReturn(null);

        //when
        //then
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("어드민 페이지 반환을 테스트한다.")
    public void testAdmin() throws Exception {
        //given
        when(productService.findAll())
            .thenReturn(null);

        //when
        //then
        mockMvc.perform(get("/admin"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("설정 페이지 반환을 테스트한다.")
    public void testSettings() throws Exception {
        //given
        when(memberService.findAll())
            .thenReturn(null);

        //when
        //then
        mockMvc.perform(get("/settings"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
