package cart.controller;

import cart.auth.BasicAuthorizationExtractor;
import cart.auth.interceptor.LoginInterceptor;
import cart.auth.resolver.BasicAuthenticationPrincipalArgumentResolver;
import cart.controller.dto.request.UserRequest;
import cart.controller.dto.response.UserResponse;
import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import cart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(UserControllerTest.class)
class UserControllerTest {

    @MockBean
    UserService mockUserService;

    @MockBean
    UserController mockUserController;

    @MockBean
    LoginInterceptor loginInterceptor;

    @MockBean
    BasicAuthorizationExtractor extractor;

    @MockBean
    BasicAuthenticationPrincipalArgumentResolver basicAuthenticationPrincipalArgumentResolver;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("POST /users 요청 시 addUser 메서드가 호출된다")
    @Test
    void addUserMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new UserRequest("test@email.com", "testPassword"));
        when(mockUserService.saveUser(any())).thenReturn(1L);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(value));
        //then
        verify(mockUserController, times(1)).addUser(any());
    }

    @DisplayName("GET /users 요청 시 loadAllUser 메서드가 호출된다")
    @Test
    void loadAllUserMappingURL() throws Exception {
        //given
        when(mockUserService.loadAllUser()).thenReturn(Collections.emptyList());
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users"));
        //then
        verify(mockUserController, times(1)).loadAllUser();
    }

    @DisplayName("GET /users/{userId} 요청 시 메서드가 호출된다")
    @Test
    void loadUserMappingURL() throws Exception {
        //given
        when(mockUserService.loadUser(1L)).thenReturn(UserResponse.from(new User.Builder().id(1L)
                                                                                          .email(new Email("test@email.com"))
                                                                                          .password(new Password("testPassword"))
                                                                                          .build())
        );
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"));
        //then
        verify(mockUserController, times(1)).loadUser(anyLong());
    }

    @DisplayName("PUT /users/{userId} 요청 시 deleteUser 메서드가 호출된다")
    @Test
    void updateUserMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new UserRequest("test@email.com", "testPassword"));
        doNothing().when(mockUserService)
                   .updateUser(anyLong(), any());
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(value));
        //then
        verify(mockUserController, times(1)).updateUser(anyLong(), any());
    }

    @DisplayName("DELETE /users/{userId} 요청 시 deleteUser 메서드가 호출된다")
    @Test
    void deleteItemMappingURL() throws Exception {
        //given
        doNothing().when(mockUserService)
                   .deleteUser(anyLong());
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"));
        //then
        verify(mockUserController, times(1)).deleteUser(anyLong());
    }

    @DisplayName("UserRequest를 입력받아 사용자 추가 시 ResponseEntity를 응답한다")
    @Test
    void addItem() {
        //given
        UserRequest value = new UserRequest("test@email.com", "testPassword");
        when(userService.saveUser(any())).thenReturn(1L);
        //then
        ResponseEntity<Void> responseEntity = userController.addUser(value);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/settings"))
        );
    }

    @DisplayName("사용자 조회 시 ResponseEntity<List<UserResponse>>를 응답한다")
    @Test
    void loadAllItem() {
        //given
        List<UserResponse> userResponses = List.of(
                UserResponse.from(new User.Builder().id(1L)
                                                    .email(new Email("test@email.com"))
                                                    .password(new Password("testPassword"))
                                                    .build()
                ));
        when(userService.loadAllUser()).thenReturn(userResponses);
        //then
        ResponseEntity<List<UserResponse>> listResponseEntity = userController.loadAllUser();
        Assertions.assertAll(
                () -> assertThat(listResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(listResponseEntity.getBody()).contains(userResponses.get(0))
        );
    }

    @DisplayName("사용자 조회 시 ResponseEntity<UserResponse>를 응답한다")
    @Test
    void loadItem() {
        //given
        UserResponse userResponse = UserResponse.from(
                new User.Builder().id(1L)
                                  .email(new Email("test@email.com"))
                                  .password(new Password("testPassword"))
                                  .build()
        );
        when(userService.loadUser(1L)).thenReturn(userResponse);
        //then
        ResponseEntity<UserResponse> responseEntity = userController.loadUser(1L);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).isEqualTo(userResponse)
        );
    }

    @DisplayName("UserResponse를 입력받아 사용자 수정 시 ResponseEntity를 응답한다")
    @Test
    void updateItem() {
        //given
        UserRequest value = new UserRequest("edit@email.com", "editPassword");
        Long itemId = 1L;
        doNothing().when(userService)
                   .updateUser(anyLong(), any());
        //then
        ResponseEntity<Void> responseEntity = userController.updateUser(itemId, value);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/settings"))
        );
    }

    @DisplayName("userId를 입력받아 사용자 삭제 시 ResponseEntity를 응답한다")
    @Test
    void deleteItem() {
        //given
        Long userId = 1L;
        doNothing().when(userService)
                   .deleteUser(anyLong());
        //then
        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/settings"))
        );
    }
}
