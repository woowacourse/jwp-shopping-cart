package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixture.다른_비밀번호;
import static woowacourse.Fixture.다른_이름;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerAddRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.exception.shoppingcart.InvalidCustomerException;

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
            CustomerAddRequest customerAddRequest = new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when
            CustomerResponse customerResponse = customerService.save(customerAddRequest);

            // then
            Assertions.assertAll(
                    () -> assertThat(customerResponse.getLoginId()).isEqualTo(페퍼_아이디),
                    () -> assertThat(customerResponse.getName()).isEqualTo(페퍼_이름)
            );
        }

        @Test
        @DisplayName("로그인 아이디가 이미 존재하면, 예외를 던진다.")
        void fail_duplicateLoginId() {
            // given
            CustomerAddRequest customerAddRequest = new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when
            customerService.save(customerAddRequest);

            // then
            assertThatThrownBy(
                    () -> customerService.save(customerAddRequest)
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
            customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(다른_이름, 페퍼_비밀번호);

            // when
            CustomerResponse response = customerService.update(new LoginCustomer(페퍼_아이디), customerUpdateRequest);

            // then
            assertThat(response.getName()).isEqualTo(다른_이름);
        }

        @Test
        @DisplayName("회원 정보의 비밀번호를 수정하려고 하면, 예외를 던진다.")
        void fail_changePassword() {
            // given
            customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(페퍼_이름, 다른_비밀번호);

            // when & then
            assertThatThrownBy(
                    () -> customerService.update(new LoginCustomer(페퍼_아이디), customerUpdateRequest)
            ).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호가 일치하지 않습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 회원 정보를 수정하려고 하면, 예외를 던진다.")
        void fail_notExistCustomer() {
            // given
            CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(페퍼_이름, 다른_비밀번호);

            // when & then
            assertThatThrownBy(
                    () -> customerService.update(new LoginCustomer(페퍼_아이디), customerUpdateRequest)
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
            customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
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
            customerService.save(new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest(다른_비밀번호);

            // when & then
            assertThatThrownBy(() -> customerService.delete(new LoginCustomer(페퍼_아이디), customerDeleteRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호가 일치하지 않습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 회원을 삭제하려고 하면, 예외를 던진다.")
        void fail_notExistCustomer() {
            // given
            CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest(페퍼_비밀번호);

            // when & then
            assertThatThrownBy(() -> customerService.delete(new LoginCustomer(페퍼_아이디), customerDeleteRequest))
                    .isInstanceOf(InvalidCustomerException.class);
        }
    }
}
