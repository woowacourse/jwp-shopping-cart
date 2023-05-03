package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.auth.AuthService;
import cart.service.CustomerService;
import cart.service.dto.CustomerResponse;
import cart.service.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerUnitTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("회원가입 API 호출 시 회원이 저장된다.")
    @Test
    void signUp() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("baron@gmail.com", "password");
        String requestString = objectMapper.writeValueAsString(signUpRequest);
        given(customerService.save(any(SignUpRequest.class))).willReturn(1L);

        // when then
        mockMvc.perform(post("/settings/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/settings/users/1"));
    }

    @DisplayName("전체 회원 조회 API 호출 시, 전체 회원 정보가 조회된다.")
    @Test
    void viewAllCustomers() throws Exception {
        // given
        CustomerResponse baron = new CustomerResponse(1L, "baron@gmail.com", "password");
        CustomerResponse journey = new CustomerResponse(2L, "journey@gmail.com", "password");
        given(customerService.findAll()).willReturn(List.of(baron, journey));

        // when, then
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"));
    }

}