package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.service.dto.CustomerResponse;
import cart.service.dto.SignUpRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("새로운 이메일을 저장할 수 있다.")
    @Test
    void save() {
        // given
        String email = "baron@gmail.com";
        String password = "password";

        // when
        long savedId = customerService.save(new SignUpRequest(email, password));

        // then
        CustomerResponse customer = customerService.findByEmail(email);
        assertThat(customer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new CustomerResponse(1L, email, password));
    }

    @DisplayName("존재하는 이메일은 저장할 수 없다.")
    @Test
    void exceptionWhenDuplicateEmailSave() {
        // given
        String email = "baron@gmail.com";
        String password = "password";
        SignUpRequest signUpRequest = new SignUpRequest(email, password);
        customerService.save(signUpRequest);

        // when, then
        assertThatThrownBy(() -> customerService.save(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 이메일 입니다.");
    }

    @DisplayName("전체 사용자 정보를 조회할 수 있다.")
    @Test
    void findAllCustomers() {
        // given
        long savedId1 = customerService.save(new SignUpRequest("baron@gmail.com", "password"));
        long savedId2 = customerService.save(new SignUpRequest("joureny@gmail.com", "password"));

        // when
        List<CustomerResponse> customers = customerService.findAll();

        // then
        List<Long> Ids = customers.stream()
                .map(CustomerResponse::getId)
                .collect(Collectors.toList());
        assertThat(Ids).containsExactly(savedId1, savedId2);
    }

}