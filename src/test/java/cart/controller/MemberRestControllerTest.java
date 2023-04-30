package cart.controller;

import cart.controller.dto.MemberDto;
import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberRestController.class)
public class MemberRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("사용자 정보를 추가한다")
    void addMember_success() throws Exception {
        // given
        final MemberDto journey = new MemberDto(1L, "journey@gmail.com", "password", "져니", "010-1234-5678");

        when(memberService.save(any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(journey))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/member/1")
                );
    }

    @Test
    @DisplayName("사용자 정보 추가 시 잘못된 형식으로 들어오면 예외가 발생한다")
    void addMember_fail() throws Exception {
        // given
        final MemberDto journey = new MemberDto(1L, "aa", "", "", "010");

        // when, then
        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(journey))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errorMessage",
                                containsInAnyOrder(
                                        "이메일 형식에 맞게 입력해 주세요.",
                                        "사용자 비밀번호는 비어있을 수 없습니다.",
                                        "사용자 닉네임은 비어있을 수 없습니다.",
                                        "올바른 전화번호 형식을 입력해 주세요."
                                )
                        ));
    }
}
