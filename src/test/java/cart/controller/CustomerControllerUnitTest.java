package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.service.CustomerService;
import cart.service.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

}