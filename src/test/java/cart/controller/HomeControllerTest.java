package cart.controller;

import cart.member.domain.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cart.DummyData.INITIAL_MEMBER_ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HomeControllerTest extends ControllerTest {

    @Test
    void 상품_목록_페이지를_조회하면_상태코드_200을_반환하는지_확인한다() throws Exception {
        final String path = "/";
        final List<Member> members = List.of(INITIAL_MEMBER_ONE);

        when(memberService.findAll())
                .thenReturn(members);

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
        final List<Member> members = List.of(INITIAL_MEMBER_ONE);

        when(memberService.findAll())
                .thenReturn(members);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
