package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.common.exception.AuthException;
import woowacourse.common.exception.JoinException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;


@SpringBootTest
@Sql("classpath:truncate.sql")
class CustomerServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("등록시 이미 존재하는 이메일이라면 에러를 발생시킨다.")
    void registerNotExist() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String username = "name";

        customerService.register(email, password, username);

        //then
        assertThatThrownBy(() -> customerService.register(email, password, username)).isInstanceOf(JoinException.class);
    }

    @Test
    @DisplayName("이메일 값으로 유저를 찾을 수 있다.")
    void showCustomer() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String username = "name";

        customerService.register(email, password, username);

        //when
        CustomerResponse customerResponse = customerService.showCustomer(email);

        //then
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(username)
        );
    }

    @Test
    @DisplayName("비밀번호 변경 시 기존 비밀번호를 잘못 입력한 경우 에러를 발생시킨다.")
    void changePasswordDifferentWithOldPassword() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String username = "name";

        customerService.register(email, password, username);

        //then
        assertThatThrownBy(() -> customerService.changePassword(email, "password1!", "password1!"))
                .isInstanceOf(AuthException.class);
    }

    @Test
    @DisplayName("유저의 기본 정보를 수정할 수 있다.")
    void changeGeneralInfo() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String oldUsername = "name";
        String newUsername = "newName";

        customerService.register(email, password, oldUsername);

        //when
        CustomerResponse customerResponse = customerService.changeGeneralInfo(email, newUsername);

        //then
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(newUsername)
        );
    }

    @Test
    @DisplayName("유저 탈퇴 시 기존 비밀번호와 일치하지 않으면 에러가 발생한다.")
    void deleteFailByPasswordError() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String username = "name";

        customerService.register(email, password, username);

        //then
        assertThatThrownBy(() -> customerService.delete(email, "wrongPwd0!")).isInstanceOf(AuthException.class);
    }
}