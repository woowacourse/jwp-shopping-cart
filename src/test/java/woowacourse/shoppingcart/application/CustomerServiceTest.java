package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("고객을 저장한다.")
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
    @DisplayName("로그인 아이디가 이미 존재하는 고객을 저장할 수 없다.")
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
