package woowacourse.shoppingcart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("이메일, 패스워드, 유저 이름을 받아서 CREATED와 Location 헤더에 리소스 접근 URI를 반환한다.")
    void signUp() throws Exception {

        final SignUpDto signUpDto = new SignUpDto("test@test.com", "testtest", "test");
        final long createdId = 1L;
        when(customerService.signUp(any(SignUpDto.class))).thenReturn(createdId);

        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(signUpDto)))
                        .andDo(print())
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).isEqualTo("/api/customers/"+createdId);
    }

    @Test
    @DisplayName("유저 이름을 받아서 기존 유저의 이름을 수정한 뒤 수정된 유저를 반환한다.")
    void updateCustomer() throws Exception {
        Long createdId = 1L;
        when(customerService.updateCustomer(any(Long.class),any(UpdateCustomerDto.class))).thenReturn(new CustomerDto(createdId, "test@test.com","테스트2"));

        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto("테스트2");

        final MockHttpServletResponse response = mockMvc.perform(put("/api/customers/" + createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(updateCustomerDto)))
                        .andDo(print())
                        .andReturn()
                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("URI path에 id를 받아 일치하는 회원을 삭제한다.")
    void deleteCustomer() throws Exception {
        final long customerId = 1L;
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/customers/" + customerId))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}