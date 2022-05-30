package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.SignUpRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;


    @Test
    void 회원가입() {
        var signUpRequest = new SignUpRequest("alpha", "bcc0830@naver.com", "123");

        var signUpResponse = customerService.signUp(signUpRequest);

        assertAll(
                () -> assertThat(signUpResponse.getUsername()).isEqualTo("alpha"),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo("bcc0830@naver.com")
        );
    }

    @Test
    void 중복된_이름으로_회원가입을_하는_경우() {
        var signUpRequest = new SignUpRequest("puterism", "crew10@naver.com", "123");

        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 존재하는 사용자 이름입니다.");
    }

    @Test
    void 중복된_이메일로_회원가입을_하는_경우() {
        var signUpRequest = new SignUpRequest("chicChoc", "crew01@naver.com", "123");

        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 존재하는 이메일입니다.");
    }
}
