package woowacourse.shoppingcart.integration.customer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.customer.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.customer.exception.badrequest.DuplicateEmailException;
import woowacourse.shoppingcart.customer.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.integration.IntegrationTest;

class CustomerServiceIntegrationTest extends IntegrationTest {

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
        final CustomerCreationRequest request = new CustomerCreationRequest(customer.getEmail(), "1q2w3e4r", "kun");

        // when, then
        Assertions.assertThatThrownBy(() -> customerService.create(request))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void create() {
        // given
        final String email = "kun@naver.com";
        final String nickname = "kun2";
        final String password = "qwerasdf123";
        final CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        // when
        final Long actual = customerService.create(request);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 예외를 발생시킨다.")
    void getByEmail_notExistEmail_exceptionThrown() {
        // given
        final String email = "asdf@email.com";

        // when, then
        assertThatThrownBy(() -> customerService.getByEmail(email))
                .isInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    @DisplayName("이메일이 존재하는 경우에, Customer를 반환한다.")
    void getByEmail_existEmail_customerReturned() {
        // when
        final Customer actual = customerService.getByEmail(customer.getEmail());

        // then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    @DisplayName("Customer를 수정한다.")
    void update_customer_void() {
        // given
        final Customer loginCustomer = new Customer(id, customer.getNickname(), customer.getEmail(),
                customer.getPassword());

        final CustomerUpdationRequest request = new CustomerUpdationRequest("rick", "qwerasdf123");

        // when, then
        assertThatCode(() -> customerService.update(loginCustomer, request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Customer를 삭제한다.")
    void delete_customer_void() {
        // given
        final Customer loginCustomer = new Customer(id, customer.getNickname(), customer.getEmail(),
                customer.getPassword());

        // when, then
        assertThatCode(() -> customerService.delete(loginCustomer))
                .doesNotThrowAnyException();
    }
}
