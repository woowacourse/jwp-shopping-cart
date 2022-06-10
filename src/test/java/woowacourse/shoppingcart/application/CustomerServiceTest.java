package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CustomerNameResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("이메일 중복 조회를 할 때, 중복이 발생하면 예외를 반환한다.")
    @Test
    void checkDuplicationEmail() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);

        assertThatThrownBy(() -> customerService.checkDuplicationEmail("email"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 email 입니다.");
    }

    @DisplayName("회원을 등록한다.")
    @Test
    void saveCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        CustomerResponse response = customerService.save(customer);

        assertThat(response).extracting("email", "name", "phone", "address")
                .containsExactly("email", "name", "010-2222-3333", "address");
    }

    @DisplayName("중복되는 email이면 예외가 발생한다.")
    @Test
    void existEmailException() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);
        assertThatThrownBy(() -> customerService.save(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 email 입니다.");
    }

    @DisplayName("customer id을 이용하여 회원 정보를 조회한다.")
    @Test
    void findCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        CustomerResponse savedResponse = customerService.save(customer);

        CustomerResponse customerResponse = customerService.findById(1L);

        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly(savedResponse.getEmail(), savedResponse.getName(),
                        savedResponse.getPhone(), savedResponse.getAddress());
    }

    @DisplayName("존재하지 않는 id를 이용하여 회원 정보를 조회하면 예외가 발생한다.")
    @Test
    void checkExistByIdExceptionWhenFind() {
        assertThatThrownBy(() -> customerService.findById(1L))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @DisplayName("customer id을 이용하여 회원 이름을 조회한다.")
    @Test
    void findCustomerNameById() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);

        CustomerNameResponse name = customerService.findNameById(1L);

        assertThat(name.getName()).isEqualTo("name");
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void update() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);

        CustomerRequest customerRequest =
                new CustomerRequest("email", "Pw123456~~", "eve", "010-1111-2222", "address2");
        customerService.update(1L, customerRequest);

        CustomerResponse customerResponse = customerService.findById(1L);
        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly("email", "eve", "010-1111-2222", "address2");
    }

    @DisplayName("존재하지 않는 id를 이용하여 회원 정보를 수정하면 예외가 발생한다.")
    @Test
    void checkExistByIdExceptionWhenUpdate() {
        CustomerRequest customerRequest =
                new CustomerRequest("email", "Pw123456~~", "eve", "010-1111-2222", "address2");
        assertThatThrownBy(() -> customerService.update(1L, customerRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @DisplayName("id를 이용하여 customer를 삭제한다.")
    @Test
    void deleteById() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);

        customerService.delete(1L);

        assertThatThrownBy(() -> customerService.findById(1L))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @DisplayName("존재하지 않는 id를 이용하여 회원을 삭제하면 예외가 발생한다.")
    @Test
    void checkExistByIdExceptionWhenDelete() {
        assertThatThrownBy(() -> customerService.delete(1L))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }
}
