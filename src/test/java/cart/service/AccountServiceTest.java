package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dto.application.AccountDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountServiceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 사용자명과_비밀번호가_일치하는_계정이_있을_경우_true를_반환한다() {
        final AccountDto accountDto = new AccountDto("user1@email.com", "password1");
        final boolean isMember = accountService.isMember(accountDto);

        assertThat(isMember).isTrue();
    }

    @Test
    void 사용자명과_비밀번호가_일치하는_계정이_없을_경우_false를_반환한다() {
        final AccountDto accountDto = new AccountDto("user3@email.com", "password1");
        final boolean isMember = accountService.isMember(accountDto);

        assertThat(isMember).isFalse();
    }
}
