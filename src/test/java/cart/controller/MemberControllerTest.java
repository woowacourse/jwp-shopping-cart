package cart.controller;

import cart.dto.member.MemberDto;
import cart.dto.member.MemberRequestDto;
import cart.entity.MemberEntity;
import cart.exception.InvalidMemberException;
import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("회원을 추가한다.")
    void addMemberTest() throws Exception {
        String email = "a@a.com";
        String password = "password1";
        MemberRequestDto requestDto = new MemberRequestDto(email, password);
        MemberDto expectDto = new MemberDto(1L, email, password);

        when(memberService.join(any(MemberRequestDto.class))).thenReturn(expectDto);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(expectDto.getEmail()))
                .andExpect(jsonPath("$.password").value(expectDto.getPassword()));
    }

    @ParameterizedTest
    @MethodSource("makeInvalidDto")
    @DisplayName("회원을 추가한다. - 잘못된 입력을 검증한다.")
    void joinMemberInvalidInput(MemberRequestDto requestDto) throws Exception {
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 정보를 수정한다.")
    void updateProduct() throws Exception {
        String email = "email@email.com";
        String newPassword = "newPassword";

        MemberRequestDto modifiedRequestDto = new MemberRequestDto(email, newPassword);
        MemberDto expectDto = MemberDto.fromEntity(new MemberEntity(1L, email, newPassword));

        when(memberService.updateById(any(MemberRequestDto.class), anyLong())).thenReturn(expectDto);

        mockMvc.perform(put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expectDto.getEmail()))
                .andExpect(jsonPath("$.password").value(expectDto.getPassword()));
    }

    @ParameterizedTest
    @MethodSource("makeInvalidDto")
    @DisplayName("회원 정보를 수정한다. - 잘못된 입력을 검증한다.")
    void updateProductInvalidInput(MemberRequestDto modifiedInvalidRequestDto) throws Exception {
        when(memberService.updateById(any(MemberRequestDto.class), anyLong()))
                .thenThrow(new InvalidMemberException("잘못된 입력입니다."));

        mockMvc.perform(put("/members/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedInvalidRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    void deleteProduct() throws Exception {
        doNothing().when(memberService).deleteById(anyLong());
        mockMvc.perform(delete("/members/{id}", anyLong()))
                .andExpect(status().isOk());
    }

    static Stream<Arguments> makeInvalidDto() {
        return Stream.of(
                Arguments.arguments(new MemberRequestDto("a".repeat(41) + "@email.com", "password")),
                Arguments.arguments(new MemberRequestDto("email@email.com", "a".repeat(51))));
    }
}