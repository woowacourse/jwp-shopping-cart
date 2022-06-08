package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.exception.bodyexception.DuplicateEmailException;

@SpringBootTest
@Transactional
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    private Customer customer;
    private Long id;

    @BeforeEach
    void setUp() {
        customer = new Customer("kun", "kun@email.com", "qwerasdf123");
        id = customerDao.save(customer);
    }

    @Test
    @DisplayName("이미 가입 이메일로 회원가입을 하면 예외를 던진다.")
    void create_alreadyExistEmail_exceptionThrown() {
        // given
        CustomerCreationRequest request = new CustomerCreationRequest(customer.getEmail(), "1q2w3e4r", "kun");

        // when, then
        Assertions.assertThatThrownBy(() -> customerService.create(request))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void create() {
        // given
        String email = "kun@naver.com";
        String nickname = "kun2";
        String password = "qwerasdf123";
        CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        // when
        Long actual = customerService.create(request);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 예외를 발생시킨다.")
    void getByEmail_notExistEmail_exceptionThrown() {
        // given
        String email = "asdf@email.com";

        // when, then
        assertThatThrownBy(() -> customerService.getByEmail(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이메일이 존재하는 경우에, Customer를 반환한다.")
    void getByEmail_existEmail_customerReturned() {
        // when
        Customer actual = customerService.getByEmail(customer.getEmail());

        // then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    @DisplayName("Customer를 수정한다.")
    void update_customer_void() {
        // given
        Customer loginCustomer = new Customer(id, customer.getNickname(), customer.getEmail(), customer.getPassword());

        CustomerUpdationRequest request = new CustomerUpdationRequest("rick", "qwerasdf123");

        // when, then
        assertThatCode(() -> customerService.update(loginCustomer, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Customer를 삭제한다.")
    void delete_customer_void() {
        // given
        Customer loginCustomer = new Customer(id, customer.getNickname(), customer.getEmail(), customer.getPassword());

        // when, then
        assertAll(
                () -> assertThatCode(() -> customerService.delete(loginCustomer))
                        .doesNotThrowAnyException(),
                () -> assertThatThrownBy(() -> customerService.getByEmail(loginCustomer.getEmail()))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
