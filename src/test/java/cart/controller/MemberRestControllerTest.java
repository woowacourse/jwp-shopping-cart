package cart.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.helper.RestDocsHelper;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.service.MemberService;
import cart.service.dto.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.snippet.Attributes;


@WebMvcTest(MemberRestController.class)
public class MemberRestControllerTest extends RestDocsHelper {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("사용자 정보를 추가한다")
    void addMember_success() throws Exception {
        // given
        final MemberRequest journey = new MemberRequest(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");

        when(memberService.save(any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(journey))
                .characterEncoding(StandardCharsets.UTF_8))
            .andExpectAll(
                status().isCreated(),
                header().string("Location", "/member/1"))
            .andDo(
                documentationResultHandler.document(
                    requestFields(
                        fieldWithPath("id").description("사용자 아이디").ignored(),
                        fieldWithPath("role").description("사용자 권한(USER, ADMIN)"),
                        fieldWithPath("email").description("사용자 이메일")
                            .attributes(new Attributes.Attribute(RANGE, "5-50")),
                        fieldWithPath("password").description("사용자 비밀번호")
                            .attributes(new Attributes.Attribute(RANGE, "8-50")),
                        fieldWithPath("nickname").description("사용자 닉네임")
                            .attributes(new Attributes.Attribute(RANGE, "1-30")),
                        fieldWithPath("telephone").description("사용자 전화번호 (010-xxxx-xxxx)")
                            .attributes(new Attributes.Attribute(RANGE, "13"))
                    ),
                    responseHeaders(
                        headerWithName("Location").description("사용자 세부 정보 URI")
                    )
                )
            );
    }

    @Test
    @DisplayName("사용자 정보 추가 시 잘못된 형식으로 들어오면 예외가 발생한다")
    void addMember_invalid_fail() throws Exception {
        // given
        final MemberRequest journey = new MemberRequest(1L, null, "aa", "",
            "", "010");

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
                        "사용자 권한은 비어있을 수 없습니다.",
                        "사용자 비밀번호는 비어있을 수 없습니다.",
                        "사용자 닉네임은 비어있을 수 없습니다.",
                        "올바른 전화번호 형식을 입력해 주세요."
                    )))
            .andDo(
                documentationResultHandler.document(
                    requestFields(
                        fieldWithPath("id").description("사용자 아이디").ignored(),
                        fieldWithPath("role").description("잘못된 권한 형식"),
                        fieldWithPath("email").description("잘못된 이메일 형식"),
                        fieldWithPath("password").description("잘못된 비밀번호 형식"),
                        fieldWithPath("nickname").description("잘못된 닉네임 형식"),
                        fieldWithPath("telephone").description("잘못된 전화번호 형식")
                    )
                )
            );
    }

    @Test
    @DisplayName("사용자 정보 추가 시 이미 존재하는 이메일이 들어오면 예외가 발생한다")
    void addMember_duplicate_fail() throws Exception {
        // given
        final MemberRequest journey = new MemberRequest(1L, "USER", "journey@gmail.com",
            "password", "져니", "010-1234-5678");
        doThrow(new GlobalException(ErrorCode.MEMBER_DUPLICATE_EMAIL))
            .when(memberService).save(any());

        // when, then
        mockMvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(journey))
                .characterEncoding(StandardCharsets.UTF_8))
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$.errorMessage",
                    contains(
                        "이미 등록된 사용자 이메일입니다."
                    )))
            .andDo(
                documentationResultHandler.document(
                    requestFields(
                        fieldWithPath("id").description("사용자 아이디").ignored(),
                        fieldWithPath("role").description("사용자 권한"),
                        fieldWithPath("email").description("중복된 사용자 이메일"),
                        fieldWithPath("password").description("사용자 비밀번호"),
                        fieldWithPath("nickname").description("사용자 닉네임"),
                        fieldWithPath("telephone").description("사용자 전화번호")
                    )
                )
            );
    }
}
