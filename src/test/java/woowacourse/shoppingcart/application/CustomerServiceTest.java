package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixture.바꿀_비밀번호;
import static woowacourse.Fixture.바꿀_아이디;
import static woowacourse.Fixture.바꿀_이름;
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
import woowacourse.shoppingcart.domain.LoginCustomer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

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
        void saveCustomer() {
            // given
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when
            CustomerResponse customerResponse = customerService.save(customerRequest);

            // then
            Assertions.assertAll(
                    () -> assertThat(customerResponse.getLoginId()).isEqualTo(페퍼_아이디),
                    () -> assertThat(customerResponse.getName()).isEqualTo(페퍼_이름)
            );
        }

        @Test
        @DisplayName("로그인 아이디가 이미 존재하면, 예외를 던진다.")
        void saveCustomer_duplicateLoginId() {
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
        @DisplayName("존재하는 회원의 정보를 할 수 있다.")
        void updateCustomer() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 바꿀_이름, 페퍼_비밀번호);

            // when
            CustomerResponse response = customerService.update(new LoginCustomer(페퍼_아이디, 페퍼_이름), customerRequest);

            // then
            assertThat(response.getName()).isEqualTo(바꿀_이름);
        }

        @Test
        @DisplayName("회원 정보의 로그인 아이디를 수정하려고 하면, 예외를 던진다.")
        void updateCustomer_changeLoginId() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerRequest customerRequest = new CustomerRequest(바꿀_아이디, 페퍼_이름, 페퍼_비밀번호);

            // when & then
            assertThatThrownBy(() -> customerService.update(new LoginCustomer(페퍼_아이디, 페퍼_이름), customerRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("아이디는 변경할 수 없습니다.");
        }

        @Test
        @DisplayName("회원 정보의 비밀번호를 수정하려고 하면, 예외를 던진다.")
        void updateCustomer_changePassword() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            CustomerRequest customerRequest = new CustomerRequest(페퍼_아이디, 페퍼_이름, 바꿀_비밀번호);

            // when & then
            assertThatThrownBy(
                    () -> customerService.update(new LoginCustomer(페퍼_아이디, 페퍼_이름), customerRequest)
            ).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비밀번호는 변경할 수 없습니다.");
        }
    }
}
