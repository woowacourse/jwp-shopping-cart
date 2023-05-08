package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.member.dto.MemberDto;
import cart.domain.member.service.MemberService;
import cart.domain.product.dto.ProductDto;
import cart.domain.product.service.ProductService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ViewControllerTest {

    @MockBean
    private ProductService productService;
    @MockBean
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

    private final List<MemberDto> memberDtos = List.of(
        new MemberDto("test1@test.com", "password1"),
        new MemberDto("test2@test.com", "password2")
    );
    private final List<ProductDto> productDtos = List.of(
        new ProductDto(1L, "name1", 1000, "imageUrl1", LocalDateTime.now(), LocalDateTime.now()),
        new ProductDto(2L, "name2", 2000, "imageUrl2", LocalDateTime.now(), LocalDateTime.now())
    );

    @Test
    @DisplayName("메인 페이지 반환을 테스트한다.")
    public void testMain() throws Exception {
        //given
        when(productService.findAll())
            .thenReturn(productDtos);

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
            .thenReturn(productDtos);

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
            .thenReturn(memberDtos);

        //when
        //then
        mockMvc.perform(get("/settings"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
