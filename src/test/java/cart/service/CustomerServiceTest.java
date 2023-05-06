package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.controller.dto.CustomerResponse;
import cart.service.dto.CustomerInfoDto;
import cart.service.dto.SignUpDto;
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
        long savedId = customerService.save(new SignUpDto(email, password));

        // then
        CustomerInfoDto customer = customerService.findByEmail(email);
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
        SignUpDto signUpDto = new SignUpDto(email, password);
        customerService.save(signUpDto);

        // when, then
        assertThatThrownBy(() -> customerService.save(signUpDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 이메일 입니다.");
    }

    @DisplayName("전체 사용자 정보를 조회할 수 있다.")
    @Test
    void findAllCustomers() {
        // given
        long savedId1 = customerService.save(new SignUpDto("baron@gmail.com", "password"));
        long savedId2 = customerService.save(new SignUpDto("joureny@gmail.com", "password"));

        // when
        List<CustomerInfoDto> customers = customerService.findAll();

        // then
        List<Long> Ids = customers.stream()
                .map(CustomerInfoDto::getId)
                .collect(Collectors.toList());
        assertThat(Ids).containsExactly(savedId1, savedId2);
    }

    @DisplayName("이메일로 사용자 id를 조회할 수 있다.")
    @Test
    void findIdByEmail() {
        // given
        long savedId = customerService.save(new SignUpDto("baron@gmail.com", "password"));

        // when
        long id = customerService.findIdByEmail("baron@gmail.com");

        // then
        assertThat(id).isEqualTo(savedId);
    }
}
