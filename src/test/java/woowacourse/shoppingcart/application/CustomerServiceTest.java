package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.dto.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password123";
    private static final String PHONE_NUMBER = "01012345678";
    private static final String ADDRESS = "성담빌딩";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("유저를 저장한다")
    void save() {
        CustomerSignUpRequest request = new CustomerSignUpRequest(USERNAME, PASSWORD, PHONE_NUMBER, ADDRESS);
        assertDoesNotThrow(() -> customerService.save(request));
    }

    @Test
    @DisplayName("이미 존재하는 유저 이름이면 예외 발생")
    void save_duplicatedUserName_throwException() {
        CustomerSignUpRequest request = new CustomerSignUpRequest(USERNAME, PASSWORD, PHONE_NUMBER, ADDRESS);
        customerService.save(request);

        assertThatThrownBy(() -> customerService.save(request))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("이미 존재하는 유저 이름입니다.");
    }

    @Test
    @DisplayName("인코딩된 새로운 패스워드로 수정한다")
    void updatePassword() {
        // given
        CustomerSignUpRequest request = new CustomerSignUpRequest(USERNAME, PASSWORD, PHONE_NUMBER, ADDRESS);
        customerService.save(request);

        String changedPassword = "123password";

        // when
        customerService.updatePassword(USERNAME, new CustomerUpdatePasswordRequest(changedPassword));
        String password = customerDao.findByUsername(USERNAME)
                .getPassword();

        // then
        assertThat(passwordEncoder.isMatchPassword(password, changedPassword)).isTrue();
    }
}
