package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.dao.entity.CustomerEntity;
import cart.service.dto.SignUpRequest;
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
        System.out.println("\n\nsavedId = " + savedId);

        // then
        CustomerEntity customer = customerService.findByEmail(email);
        assertThat(customer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new CustomerEntity.Builder()
                        .email(email)
                        .password(password));
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

}