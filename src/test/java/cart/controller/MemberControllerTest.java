package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.member.dto.CreatedMemberDto;
import cart.domain.member.service.MemberService;
import cart.dto.request.MemberCreateRequest;
import cart.dto.response.MemberCreateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원 생성 api")
    public void testCreate() throws Exception {
        //given
        final MemberCreateRequest request = new MemberCreateRequest("test@test.com", "password");
        final String jsonRequest = objectMapper.writeValueAsString(request);
        final CreatedMemberDto createdMemberDto = new CreatedMemberDto(1L,
            request.getEmail(), LocalDateTime.now(), LocalDateTime.now());
        final MemberCreateResponse expectedResponse = MemberCreateResponse.of(createdMemberDto);
        when(memberService.create(any()))
            .thenReturn(createdMemberDto);

        //when
        //then
        final MvcResult mvcResult = mockMvc.perform(post("/api/member")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
        final MemberCreateResponse result = jsonToObject(mvcResult,
            MemberCreateResponse.class);

        assertThat(result)
            .extracting("id", "email", "createdAt", "updatedAt")
            .containsExactly(
                expectedResponse.getId(),
                expectedResponse.getEmail(),
                expectedResponse.getCreatedAt(),
                expectedResponse.getUpdatedAt()
            );
    }

    private <T> T jsonToObject(final MvcResult result, final Class<T> valueType)
        throws UnsupportedEncodingException, JsonProcessingException {
        final String responseString = result.getResponse()
            .getContentAsString();
        return objectMapper.readValue(responseString, valueType);
    }
}
