package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.AuthException;
import woowacourse.exception.JoinException;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.application.dto.ChangePasswordDto;
import woowacourse.shoppingcart.customer.application.dto.RegisterDto;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.ui.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.customer.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.customer.ui.dto.CustomerResponse;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    private static final String EMAIL = "east@gmail.com";
    private static final String PASSWORD = "password1!";
    private static final String USER_NAME = "이스트";
    private static final CustomerRequest CUSTOMER_REQUEST = new CustomerRequest(EMAIL, PASSWORD, USER_NAME);

    @DisplayName("유저 저장 기능의 정상 동작 확인")
    @Test
    void register() {
        //when
        customerService.register(RegisterDto.from(CUSTOMER_REQUEST));
        //then
        final Customer savedCustomer = customerDao.findByEmail(EMAIL);
        assertThat(savedCustomer.getUsername()).isEqualTo(USER_NAME);
    }

    @DisplayName("이미 존재하는 이메일로 회원가입 시 예외 발생")
    @Test
    void registerWithExistentEmail() {
        //given
        customerService.register(RegisterDto.from(CUSTOMER_REQUEST));
        final CustomerRequest customerRequest = new CustomerRequest(EMAIL, "password0!", "웨스트");
        //then
        assertThatThrownBy(() -> customerService.register(RegisterDto.from(customerRequest)))
                .isInstanceOf(JoinException.class);
    }

    @DisplayName("비밀번호 변경 정상 동작 확인")
    @Test
    void changePassword() {
        //given
        final String newPassword = "password2!";
        customerService.register(RegisterDto.from(CUSTOMER_REQUEST));
        //when
        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(PASSWORD, newPassword);
        customerService.changePassword(ChangePasswordDto.from(changePasswordRequest, EMAIL));
        //then
        final Customer savedCustomer = customerDao.findByEmail(EMAIL);
        assertThat(savedCustomer.isDifferentPassword(newPassword)).isFalse();
    }

    @DisplayName("기존 비밀번호가 맞지 않을 경우 예외 발생")
    @Test
    void changePasswordWithIncorrectOriginPassword() {
        //given
        final String newPassword = "password2!";
        customerService.register(RegisterDto.from(CUSTOMER_REQUEST));
        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("password4!", newPassword);
        //then
        assertThatThrownBy(() -> customerService.changePassword(ChangePasswordDto.from(changePasswordRequest, EMAIL)))
                .isInstanceOf(AuthException.class);
    }

    @DisplayName("일반 정보 수정 정상 동작 확인")
    @Test
    void changeGeneralInfo() {
        //given
        final String newUserName = "싸우쓰";
        customerService.register(RegisterDto.from(CUSTOMER_REQUEST));
        //when
        final CustomerResponse customerResponse = customerService.changeGeneralInfo(EMAIL, newUserName);
        //then
        assertThat(customerResponse.getUsername()).isEqualTo(newUserName);
    }
}
