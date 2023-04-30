package cart.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.SignInRequest;
import cart.domain.user.User;
import cart.exception.GlobalControllerAdvice;
import cart.exception.user.SignInFailureException;
import cart.service.UserService;
import cart.service.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    UserController userController;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalControllerAdvice())
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("존재하는 사용자라면 로그인에 성공한다.")
    void signInSuccess() throws Exception {
        User user = new User("a@a.com", "a");
        given(userService.signIn(anyString(), anyString())).willReturn(new UserDto(user));
        SignInRequest signInRequest = new SignInRequest("a@a.com", "a");

        mockMvc.perform(post("/users/sign-in")
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basic").isNotEmpty());
    }

    @Test
    @DisplayName("존재하지 않는 사용자거나 비밀번호가 일치하지 않으면 로그인에 실패한다.")
    void signInFailWithNotUserOrNotMatchesPassword() throws Exception {
        given(userService.signIn(anyString(), anyString()))
                .willThrow(new SignInFailureException("로그인에 실패했습니다."));
        SignInRequest signInRequest = new SignInRequest("c@c.com", "c");

        mockMvc.perform(post("/users/sign-in")
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("로그인에 실패했습니다."));
    }

    @Test
    @DisplayName("이메일이 비어 있으면 BAD REQUEST가 반환된다.")
    void signInFailWithBlankEmail() throws Exception {
        SignInRequest signInRequest = new SignInRequest(" ", "a");

        mockMvc.perform(post("/users/sign-in")
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("이메일을 입력해주세요.")));
    }

    @Test
    @DisplayName("이메일이 이메일 형식이 아니면 있으면 BAD REQUEST가 반환된다.")
    void signInFailWithNotEmailFormat() throws Exception {
        SignInRequest signInRequest = new SignInRequest("aaa", "a");

        mockMvc.perform(post("/users/sign-in")
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("이메일 형식으로 입력해주세요."));
    }

    @Test
    @DisplayName("비밀번호가 비어 있으면 BAD REQUEST가 반환된다.")
    void signInFailWithBlankPassword() throws Exception {
        SignInRequest signInRequest = new SignInRequest("a@a.com", " ");

        mockMvc.perform(post("/users/sign-in")
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("비밀번호를 입력해주세요."));
    }
}
