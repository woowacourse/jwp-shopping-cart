package cart.controller;

import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
import cart.dto.ResponseMemberDto;
import cart.dto.ResponseProductDto;
import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(ViewController.class)
class ViewControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @Test
    void 상품목록_화면을_반환한다() throws Exception {
        //given
        given(productService.findAll())
                .willReturn(List.of(new ResponseProductDto(new ProductEntity(1L, "치킨", 10_000, "치킨 주소"))));

        //when
        final String html = mockMvc.perform(get("/")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        //then
        assertThat(html).contains("치킨", "10000", "치킨 주소");
    }

    @Test
    void 관리자_화면을_반환한다() throws Exception {
        //given
        given(productService.findAll())
                .willReturn(List.of(new ResponseProductDto(new ProductEntity(1L, "치킨", 10_000, "치킨 주소"))));

        //when
        final String html = mockMvc.perform(get("/admin")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        //then
        assertThat(html).contains("치킨", "10000", "치킨 주소");
    }

    @Test
    void 설정_화면을_반환한다() throws Exception {
        //given
        given(memberService.findAll())
                .willReturn(List.of(new ResponseMemberDto(new MemberEntity(1L, "huchu@woowahan.com", "1234567a!"))));

        //when
        final String html = mockMvc.perform(get("/settings")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        //then
        assertThat(html).contains("huchu@woowahan.com", "1234567a!");
    }

    @Test
    void 장바구니_화면을_반환한다() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(view().name("cart"))
                .andDo(print());
    }
}
