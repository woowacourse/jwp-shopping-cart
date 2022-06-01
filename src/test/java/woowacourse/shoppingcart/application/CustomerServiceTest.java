package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixture.다른_비밀번호;
import static woowacourse.Fixture.다른_아이디;
import static woowacourse.Fixture.다른_이름;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.LoginCustomer;
import woowacourse.shoppingcart.dto.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
@Nested
@DisplayName("CustomerService 클래스의")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Nested
    @DisplayName("save 메서드는")
    class save {
        @Test
        @DisplayName("고객 정보를 입력하면, 저장한다.")
        void success() {
            // given
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when
            CustomerResponse customerResponse = customerService.save(customerRequest);

            // then
            assertAll(
                    () -> assertThat(customerResponse.getLoginId()).isEqualTo(페퍼_아이디),
                    () -> assertThat(customerResponse.getName()).isEqualTo(페퍼_이름)
            );
        }

        @Test
        @DisplayName("로그인 아이디가 이미 존재하면, 예외를 던진다.")
        void fail_duplicateLoginId() {
            // given
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when
            customerService.save(customerRequest);

            // then
            assertThatThrownBy(
                    () -> customerService.save(customerRequest)
            ).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 존재하는 아이디입니다.");
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class update {
        @Test
        @DisplayName("존재하는 회원의 정보를 수정할 수 있다.")
        void success() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 다른_이름, 페퍼_비밀번호);

            // when
            CustomerResponse response = customerService.update(new LoginCustomer(페퍼_아이디), customerRequest);

            // then
            assertThat(response.getName()).isEqualTo(다른_이름);
        }

        @Test
        @DisplayName("회원 정보의 로그인 아이디를 수정하려고 하면, 예외를 던진다.")
        void fail_changeLoginId() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerRequest customerRequest = new CustomerRequest(다른_아이디, 페퍼_이름, 페퍼_비밀번호);
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

            // when & then
            assertThatThrownBy(() -> customerService.update(loginCustomer, customerRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("아이디는 변경할 수 없습니다.");
        }

        @Test
        @DisplayName("회원 정보의 비밀번호를 수정하려고 하면, 예외를 던진다.")
        void fail_changePassword() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 페퍼_이름, 다른_비밀번호);
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

            // when & then
            assertThatThrownBy(
                    () -> customerService.update(loginCustomer, customerRequest)
            ).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호는 변경할 수 없습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 회원 정보를 수정하려고 하면, 예외를 던진다.")
        void fail_notExistCustomer() {
            // given
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 페퍼_이름, 다른_비밀번호);
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

            // when & then
            assertThatThrownBy(
                    () -> customerService.update(loginCustomer, customerRequest)
            ).isInstanceOf(InvalidCustomerException.class)
                    .hasMessage("유효하지 않은 고객입니다");
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete {
        @Test
        @DisplayName("존재하는 회원의 정보를 삭제할 수 있다.")
        void success() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest(페퍼_비밀번호);

            // when & then
            assertThatNoException().isThrownBy(
                    () -> customerService.delete(new LoginCustomer(페퍼_아이디), customerDeleteRequest)
            );
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않으면, 예외를 던진다.")
        void fail_changeLoginId() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest(다른_비밀번호);
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

            // when & then
            assertThatThrownBy(() -> customerService.delete(loginCustomer, customerDeleteRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호가 일치하지 않습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 회원을 삭제하려고 하면, 예외를 던진다.")
        void fail_notExistCustomer() {
            // given
            CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest(페퍼_비밀번호);
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

            // when & then
            assertThatThrownBy(() -> customerService.delete(loginCustomer, customerDeleteRequest))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }
}
